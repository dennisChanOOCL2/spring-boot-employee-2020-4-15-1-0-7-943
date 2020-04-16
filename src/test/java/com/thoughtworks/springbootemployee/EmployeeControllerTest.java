package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.CommonTools.CommonUtils;
import com.thoughtworks.springbootemployee.controller.EmployeeController;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import io.restassured.http.ContentType;
import io.restassured.mapper.TypeRef;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.mockito.Mockito.doReturn;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeControllerTest {

    public List<Employee> employeeList = new ArrayList<>();
    public Employee employee = new Employee();

    @Mock
    private EmployeeService employeeService;

    @Before
    public void setUp(){
        EmployeeController employeeController = new EmployeeController(employeeService);
        RestAssuredMockMvc.standaloneSetup(employeeController);

        employeeList.add(new Employee(0,"Xiaoming", 20, CommonUtils.MALE));
        employeeList.add(new Employee(1,"Xiaohong", 19, CommonUtils.FEMALE));
        employeeList.add(new Employee(2,"Xiaozhi", 15, CommonUtils.MALE));
        employeeList.add(new Employee(3,"Xiaogang", 16, CommonUtils.MALE));
        employeeList.add(new Employee(4,"Xiaoxia", 15, CommonUtils.FEMALE));

        employee.setId(1);
        employee.setName("Xiaohong");

    }

    @Test
    public void shouldFindAllEmployee(){

        doReturn(employeeList).when(employeeService).getAll(null,null,null);
        MockMvcResponse response = RestAssuredMockMvc.given().contentType(ContentType.JSON)
                .when()
                .get("/employees");

        Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        List<Employee> employees = response.getBody().as(new TypeRef<List<Employee>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });

        Assert.assertEquals(5, employees.size());
        Assert.assertEquals("Xiaoming", employees.get(0).getName());
    }

    @Test
    public void shouldFindEmployeeById(){

        doReturn(employee).when(employeeService).getSpecificEmployee(1);
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .when()
                .get("/employees/1");

        Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        Employee employee = response.getBody().as(Employee.class);
        Assert.assertEquals(1, employee.getId());
        Assert.assertEquals("Xiaohong", employee.getName());
    }

    @Test
    public void shouldFindEmployeeByGender() {

        MockMvcResponse response = given().contentType(ContentType.JSON)
                .params("gender", "male")
                .when()
                .get("/employees");

        Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        List<Employee> employees = response.getBody().as(new TypeRef<List<Employee>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });
        Assert.assertEquals(3, employees.size());
        Assert.assertEquals("Xiaoming", employees.get(0).getName());
    }

    @Test
    public void shouldFindEmployeeByPaging() {

        Map<String, Integer> paramsMap = new HashMap();
        paramsMap.put("page", 1);
        paramsMap.put("pageSize",1);
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .params(paramsMap)
                .when()
                .get("/employees");

        Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        List<Employee> employees = response.getBody().as(new TypeRef<List<Employee>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });
        Assert.assertEquals(1, employees.size());
        Assert.assertEquals("Xiaoming", employees.get(0).getName());
    }

    @Test
    public void shouldAddEmployee(){

        Employee newAddEmployee = new Employee();
        newAddEmployee.setId(10);
        newAddEmployee.setName("Dennis");

        MockMvcResponse response = RestAssuredMockMvc.given().contentType(ContentType.JSON)
                .body(newAddEmployee)
                .when()
                .post("/employees");

        Assert.assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());

        Employee employee = response.getBody().as(Employee.class);

        Assert.assertEquals(newAddEmployee.getName(),employee.getName());
        Assert.assertEquals(newAddEmployee.getId(), employee.getId());
    }

    @Test
    public void shouldUpdateEmployee(){

        MockMvcResponse response = RestAssuredMockMvc.given().contentType(ContentType.JSON)
                .param("name","DennisTesting")
                .when()
                .put("/employees/1");

        Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        Employee employee = response.getBody().as(Employee.class);

        Assert.assertEquals("DennisTesting",employee.getName());
    }

    @Test
    public void shouldDeleteEmployee(){

        MockMvcResponse response = RestAssuredMockMvc.given().contentType(ContentType.JSON)
                .when()
                .delete("/employees/1");

        Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        Employee employee = response.getBody().as(Employee.class);

        Assert.assertEquals("Xiaohong",employee.getName());

    }
}
