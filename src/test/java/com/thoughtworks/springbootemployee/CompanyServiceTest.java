package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.CommonTools.CommonUtils;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doReturn;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CompanyServiceTest {

    public List<Employee> employeeList = new ArrayList<>();
    public Employee employee = new Employee();

    @Mock
    private EmployeeRepository employeeRepository;

    private EmployeeService employeeService;

    @Before
    public void setUp(){
        employeeService = new EmployeeService(employeeRepository);

        employeeList.add(new Employee(null, 0,"Xiaoming", 20, CommonUtils.MALE, 8000, null));
        employeeList.add(new Employee(null, 1,"Xiaohong", 19, CommonUtils.FEMALE, 8000, null));
        employeeList.add(new Employee(null, 2,"Xiaozhi", 15, CommonUtils.MALE, 8000, null));
        employeeList.add(new Employee(null, 3,"Xiaogang", 16, CommonUtils.MALE, 8000, null));
        employeeList.add(new Employee(null, 4,"Xiaoxia", 15, CommonUtils.FEMALE, 8000, null));

        employee.setId(1);
        employee.setName("Xiaohong");

    }

    @Test
    public void shouldFindAllEmployee(){

        doReturn(employeeList).when(employeeRepository).findAll();

        List<Employee> employeesList = employeeService.getAll(null, null, null);

        Assert.assertEquals(5, employeesList.size());
        Assert.assertEquals("Xiaoming", employeesList.get(0).getName());

    }

//
//    @Test
//    public void shouldFindEmployeeById(){
//
//        doReturn(employee).when(employeeService).getSpecificEmployee(1);
//        MockMvcResponse response = given().contentType(ContentType.JSON)
//                .when()
//                .get("/employees/1");
//
//        Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode());
//
//        Employee employee = response.getBody().as(Employee.class);
//        Assert.assertEquals(1, employee.getId());
//        Assert.assertEquals("Xiaohong", employee.getName());
//    }
//
//    @Test
//    public void shouldFindEmployeeByGender() {
//        employeeList = employeeList.stream()
//                .filter(employeeElement -> employeeElement.getGender().equals(CommonUtils.MALE))
//                .collect(Collectors.toList());
//        doReturn(employeeList).when(employeeService).getAll(null,null,"male");
//        MockMvcResponse response = given().contentType(ContentType.JSON)
//                .params("gender", "male")
//                .when()
//                .get("/employees");
//
//        Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode());
//
//        List<Employee> employees = response.getBody().as(new TypeRef<List<Employee>>() {
//            @Override
//            public Type getType() {
//                return super.getType();
//            }
//        });
//        Assert.assertEquals(3, employees.size());
//        Assert.assertEquals("Xiaoming", employees.get(0).getName());
//    }
//
//    @Test
//    public void shouldFindEmployeeByPaging() {
//
//        employeeList = employeeList.subList(0,1);
//
//        doReturn(employeeList).when(employeeService).getAll(1,1,null);
//
//        HashMap<String, Integer> paramsMap = new HashMap();
//        paramsMap.put("page", 1);
//        paramsMap.put("pageSize",1);
//
//        MockMvcResponse response = given().contentType(ContentType.JSON)
//                .params(paramsMap)
//                .when()
//                .get("/employees");
//
//        Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode());
//
//        List<Employee> employees = response.getBody().as(new TypeRef<List<Employee>>() {
//            @Override
//            public Type getType() {
//                return super.getType();
//            }
//        });
//
//        Assert.assertEquals(1, employees.size());
//        Assert.assertEquals("Xiaoming", employees.get(0).getName());
//    }
//
//    @Test
//    public void shouldAddEmployee(){
//
//        doReturn(employee).when(employeeService).createEmployee(any());
//        MockMvcResponse response = RestAssuredMockMvc.given().contentType(ContentType.JSON)
//                .body(employee)
//                .when()
//                .post("/employees");
//
//        Assert.assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
//
//        Employee employee = response.getBody().as(Employee.class);
//
//        Assert.assertEquals("Xiaohong",employee.getName());
//        Assert.assertEquals(1, employee.getId());
//    }
//
//    @Test
//    public void shouldUpdateEmployee(){
//
//        doReturn(employee).when(employeeService).updateEmployee(1,"Xiaohong",null,null,null);
//        MockMvcResponse response = RestAssuredMockMvc.given().contentType(ContentType.JSON)
//                .params("name","Xiaohong")
//                .when()
//                .put("/employees/1");
//
//        Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode());
//
//        Employee employee = response.getBody().as(Employee.class);
//
//        Assert.assertEquals("Xiaohong",employee.getName());
//    }
//
//    @Test
//    public void shouldDeleteEmployee(){
//
//        doReturn(employee).when(employeeService).removeEmployee(1);
//        MockMvcResponse response = RestAssuredMockMvc.given().contentType(ContentType.JSON)
//                .when()
//                .delete("/employees/1");
//
//        Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode());
//
//        Employee employee = response.getBody().as(Employee.class);
////
//        Assert.assertEquals("Xiaohong",employee.getName());
//
//    }
}
