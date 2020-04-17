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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CompanyControllerTest {

    private List<Company> companyList = new ArrayList<>();
    private Company company = new Company();
    private CommonUtils commonUtils = new CommonUtils();

    @Mock
    private CompanyService companyService;

    @Before
    public void setUp(){
        CompanyController companyController = new CompanyController(companyService);
        RestAssuredMockMvc.standaloneSetup(companyController);

        List<Employee> companyOneEmployeeList = new ArrayList<>();
        List<Employee> companyTwoEmployeeList = new ArrayList<>();

        companyOneEmployeeList.add(new Employee(null,4,"alibaba1", 20, commonUtils.MALE, 6000, null));
        companyOneEmployeeList.add(new Employee(null,11,"tengxun2", 19, commonUtils.FEMALE, 7000, null));
        companyTwoEmployeeList.add(new Employee(null,6,"alibaba3", 19, commonUtils.MALE, 8000, null));

        companyList.add(new Company("alibaba", 0,companyOneEmployeeList.size(),companyOneEmployeeList));
        companyList.add(new Company("tengxun", 1,companyOneEmployeeList.size(),companyTwoEmployeeList));

        company = companyList.get(1);
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
        doReturn(companyList.subList(0,1))
                .when(companyService)
                .getAll(any(),any());
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
        doReturn(company).when(companyService).getSpecificCompanyByCompanyId(1);
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .when()
                .get("/companies/1");

        Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        Company company = response.getBody().as(Company.class);
        Assert.assertEquals(2, company.getCompanyId());
        Assert.assertEquals("tengxun", company.getCompanyName());
    }

    @Test
    public void getFindEmployeesForSpecificCompanyById() {
        doReturn(company.getEmployeesList()).when(companyService).getEmployeesForSpecificCompanyByCompanyId(1);
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .when()
                .get("/companies/1/employees");

        Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        List<Employee> employeeList = response.getBody().as(new TypeRef<List<Employee>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });

        Assert.assertEquals(1, employeeList.size());
        Assert.assertEquals("alibaba3", employeeList.get(0).getName());
    }

    @Test
    public void shouldUpdateCompany(){

        doReturn(company)
                .when(companyService)
                .updateCompany(
                        eq(1)
                        ,any()
                );

        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("companyName","DennisTesting");

        MockMvcResponse response = RestAssuredMockMvc.given().contentType(ContentType.JSON)
                .body(company)
                .when()
                .put("/companies/1");

        Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        Company companyUpdated = response.getBody().as(Company.class);

        Assert.assertEquals(companyUpdated.getCompanyName(),company.getCompanyName());

    }

    @Test
    public void shouldCreateCompany(){

        doReturn(company)
                .when(companyService)
                .createCompany(any());
        MockMvcResponse response = RestAssuredMockMvc.given().contentType(ContentType.JSON)
                .body(company)
                .when()
                .post("/companies");

        Assert.assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());

        Company company = response.getBody().as(Company.class);

        Assert.assertEquals("tengxun",company.getCompanyName());
    }

    @Test
    public void shouldDeleteCompany(){

        doReturn(company).when(companyService).removeCompany(1);
        MockMvcResponse response = RestAssuredMockMvc.given().contentType(ContentType.JSON)
                .body(company)
                .when()
                .delete("/companies/1");

        Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        Company company = response.getBody().as(Company.class);

        Assert.assertEquals("tengxun",company.getCompanyName());

    }

}
