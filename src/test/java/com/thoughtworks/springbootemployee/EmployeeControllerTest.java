package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.controller.EmployeeController;
import com.thoughtworks.springbootemployee.model.Employee;
import io.restassured.http.ContentType;
import io.restassured.mapper.TypeRef;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeControllerTest {

    @Before
    public void setUp(){
        EmployeeController employeeController = new EmployeeController();
        RestAssuredMockMvc.standaloneSetup(employeeController);
    }

    @Test
    public void shouldFindAllEmployee(){
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
        Map paramsMap = new HashMap();
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
