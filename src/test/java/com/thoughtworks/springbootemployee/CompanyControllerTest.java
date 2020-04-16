package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.controller.CompanyController;
import com.thoughtworks.springbootemployee.controller.EmployeeController;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import io.restassured.http.ContentType;
import io.restassured.mapper.TypeRef;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
public class CompanyControllerTest {

    @Before
    public void setUp(){
        CompanyController companyController = new CompanyController();
        RestAssuredMockMvc.standaloneSetup(companyController);
    }

    @Test
    public void shouldFindAllCompany(){
        MockMvcResponse response = RestAssuredMockMvc.given().contentType(ContentType.JSON)
                .when()
                .get("/companies");

        Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        List<Company> companies = response.getBody().as(new TypeRef<List<Company>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });
        Assert.assertEquals(2, companies.size());
        Assert.assertEquals("alibaba", companies.get(0).getCompanyName());
    }

    @Test
    public void shouldFindCompanyById(){
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .when()
                .get("/companies/1");

        Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        Company company = response.getBody().as(Company.class);
        Assert.assertEquals(1, company.getCompanyId());
        Assert.assertEquals("tengxun", company.getCompanyName());
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
