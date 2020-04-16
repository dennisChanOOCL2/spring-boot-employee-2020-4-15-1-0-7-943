package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.CommonTools.CommonUtils;
import com.thoughtworks.springbootemployee.controller.CompanyController;
import com.thoughtworks.springbootemployee.controller.EmployeeController;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.CompanyService;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Type;
import java.util.*;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.mockito.Mockito.doReturn;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CompanyControllerTest {

    private List<Company> companyList = new ArrayList<>();
    private CommonUtils commonUtils = new CommonUtils();

    @Mock
    private CompanyService companyService;

    @Before
    public void setUp(){
        CompanyController companyController = new CompanyController(companyService);
        RestAssuredMockMvc.standaloneSetup(companyController);

        List<Employee> companyOneEmployeeList = new ArrayList<>();
        List<Employee> companyTwoEmployeeList = new ArrayList<>();

        companyOneEmployeeList.add(new Employee(4,"alibaba1", 20, commonUtils.MALE, 6000));
        companyOneEmployeeList.add(new Employee(11,"tengxun2", 19, commonUtils.FEMALE, 7000));
        companyTwoEmployeeList.add(new Employee(6,"alibaba3", 19, commonUtils.MALE, 8000));

        companyList.add(new Company("alibaba", 0, 200, companyOneEmployeeList));
        companyList.add(new Company("tengxun", 1,200, companyTwoEmployeeList));

    }

    @Test
    public void shouldFindAllCompany(){
        doReturn(companyList).when(companyService).getAll(any(),any());
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
    public void shouldFindAllCompanyWithPaging(){
        doReturn(companyList.subList(0,1)).when(companyService).getAll(any(),any());
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
        Assert.assertEquals(1, companies.size());
        Assert.assertEquals("alibaba", companies.get(0).getCompanyName());
    }

    @Test
    public void shouldFindCompanyById(){
        doReturn(companyList.get(0)).when(companyService).getAll(any(),any());
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .when()
                .get("/companies/1");

        Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        Company company = response.getBody().as(Company.class);
        Assert.assertEquals(1, company.getCompanyId());
        Assert.assertEquals("alibaba", company.getCompanyName());
    }

    @Test
    public void getFindEmployeesForSpecificCompanyById() {

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
