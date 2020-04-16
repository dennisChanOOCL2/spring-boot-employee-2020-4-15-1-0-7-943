package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.CommonTools.CommonUtils;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    private List<Company> companyList = new ArrayList<>();
    private CommonUtils commonUtils = new CommonUtils();

    @Autowired
    private CompanyService companyService;

    public CompanyController(){

    }

    @GetMapping("/{companyId}/employees")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Employee>> getEmployeesForSpecificCompany(@PathVariable int companyId){
        List<Employee> employeeList = companyService.getEmployeesForSpecificCompanyByCompanyId(companyId);

        if(employeeList == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<>(employeeList, HttpStatus.OK);
        }
    }

    @GetMapping("/{companyId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Company> getSpecificCompany(@PathVariable int companyId){
        Company selectedCompany =  companyService.getSpecificCompanyByCompanyId(companyId);

        if(selectedCompany == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<>(selectedCompany, HttpStatus.OK);
        }
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Company>> getAllCompany(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer pageSize){
        List<Company> companyList = companyService.getAll(page, pageSize);

        if(companyList == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(companyList, HttpStatus.OK);

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Company> createCompany(@RequestBody Company company){

        Company companyToBeCreated = companyService.createCompany(company);

        if(companyToBeCreated == null){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(company, HttpStatus.CREATED);

    }


    private Company selectCompanyById(int companyId){
        return companyList.stream()
                .filter(company -> company.getCompanyId() == companyId)
                .findFirst()
                .orElse(null);
    }


    @PutMapping("/{companyId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Company> updateCompany(@PathVariable int companyId,
                                                   @RequestParam(required = false) String companyName,
                                                   @RequestParam(required = false) Integer employeesNumber,
                                                   @RequestParam(required = false) List<Employee> employeeList){

        Company selectedCompany =  selectCompanyById(companyId);

        if(selectedCompany == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        int originalEmployeesNumber = selectedCompany.getEmployeesNumber();

        if(employeesNumber != null){
            if(selectedCompany.getEmployeeList().size() > employeesNumber){
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
            selectedCompany.setEmployeesNumber(employeesNumber);
        }

        if(employeeList != null){
            if(employeeList.size() > selectedCompany.getEmployeesNumber()){
                selectedCompany.setEmployeesNumber(originalEmployeesNumber);
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
            selectedCompany.setEmployeeList(employeeList);
        }

        if(companyName != null){
            selectedCompany.setCompanyName(companyName);
        }

        return new ResponseEntity<>(selectedCompany, HttpStatus.OK);
    }

    @DeleteMapping("/{companyId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Company> removeCompany(@PathVariable int companyId){

        Company selectedCompany =  selectCompanyById(companyId);

        if(selectedCompany != null){
            companyList.remove(selectedCompany);
            return new ResponseEntity<>(selectedCompany, HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

    }

}
