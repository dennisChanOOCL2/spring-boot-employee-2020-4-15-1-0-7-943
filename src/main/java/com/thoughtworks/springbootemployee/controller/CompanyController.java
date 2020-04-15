package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.CommonTools.CommonUtils;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    public static final String MALE = "male";
    public static final String FEMALE = "female";
    private List<Company> companyList = new ArrayList<>();
    private List<Employee> employeeList = new ArrayList<>();
    private CommonUtils commonUtils = new CommonUtils();

    @PostMapping("/init")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<List<Company>> createEmployeeForTesting(){

        List<Employee> companyOneEmployeeList = new ArrayList<>();
        List<Employee> companyTwoEmployeeList = new ArrayList<>();

        companyOneEmployeeList.add(new Employee(4,"alibaba1", 20, MALE, 6000));
        companyOneEmployeeList.add(new Employee(11,"tengxun2", 19, FEMALE, 7000));

        companyTwoEmployeeList.add(new Employee(6,"alibaba3", 19, MALE, 8000));

        companyList.add(new Company("alibaba", 0, 200, companyOneEmployeeList));
        companyList.add(new Company("tengxun", 1,200, companyTwoEmployeeList));

        return new ResponseEntity<>(companyList, HttpStatus.CREATED);
    }

    @GetMapping("/{id}/employees")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Employee>> getEmployeesForSpecificCompany(@PathVariable int id){
        Company selectedCompany =  companyList.stream()
                .filter(company -> company.getCompanyId() == id)
                .findFirst()
                .orElse(null);

        if(selectedCompany == null){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }else{
            return new ResponseEntity<>(selectedCompany.getEmployeeList(), HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Company> getSpecificCompany(@PathVariable int id){
        Company selectedCompany =  companyList.stream()
                .filter(company -> company.getCompanyId() == id)
                .findFirst()
                .orElse(null);

        if(selectedCompany == null){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }else{
            return new ResponseEntity<>(selectedCompany, HttpStatus.OK);
        }
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Company>> getAllCompany(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer pageSize
                                         ){
        List<Company> returnList = new ArrayList<>(companyList);
        returnList = commonUtils.pagingForList(returnList, page, pageSize);
        if(returnList == null){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(returnList, HttpStatus.OK);

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Company> createEmployee(@RequestBody Company company){
        if(selectCompanyById(company.getCompanyId()) != null){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }else{
            companyList.add(company);
            return new ResponseEntity<>(company, HttpStatus.CREATED);
        }
    }


    private Company selectCompanyById(int id){
        Company selectedCompany =  companyList.stream()
                .filter(company -> company.getCompanyId() == id)
                .findFirst()
                .orElse(null);
        return selectedCompany;
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Company> updateCompany(@PathVariable int id,
                                                   @RequestParam(required = false) String companyName,
                                                   @RequestParam(required = false) Integer employeesNumber,
                                                   @RequestParam(required = false) List<Employee> employeeList){

        Company selectedCompany =  selectCompanyById(id);

        if(selectedCompany == null){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        if(companyName != null){
            selectedCompany.setCompanyName(companyName);
        }
        if(employeesNumber != null){
            selectedCompany.setEmployeesNumber(employeesNumber);
        }
        if(employeeList != null){
            selectedCompany.setEmployeeList(employeeList);
        }

        return new ResponseEntity<>(selectedCompany, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Company> removeCompany(@PathVariable int id){

        Company selectedCompany =  selectCompanyById(id);

        if(selectedCompany != null){
            companyList.remove(selectedCompany);
            return new ResponseEntity<>(selectedCompany, HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

    }

}
