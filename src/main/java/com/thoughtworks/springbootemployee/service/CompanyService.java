package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CompanyService {

    @Autowired
    CompanyRepository repository;

    public CompanyService(CompanyRepository repository) {
        this.repository = repository;
    }

    public Company getSpecificCompanyByCompanyId(int companyId) {
        return repository.findCompanyByCompanyId(companyId);
    }

    public List<Employee> getEmployeesForSpecificCompanyByCompanyId(int companyId) {
        Company selectedCompany = repository.findCompanyByCompanyId(companyId);
        return selectedCompany.getEmployeeList();
    }

    public List<Company> getAll(Integer page, Integer pageSize) {

        List<Company> returnCompanyList = repository.findAll();

        if(page != null && pageSize != null){
            returnCompanyList = repository.findAllWithPaging(returnCompanyList, page, pageSize);
        }

        return returnCompanyList;
    }

    public Company createCompany(Company company) {
        return repository.addCompany(company);
    }

    public Company updateCompany(int companyId, String companyName, List<Employee> employeeList) {
        Company selectedCompany = repository.findCompanyByCompanyId(companyId);
        if(selectedCompany == null){
            return null;
        }

        Company updatedData = new Company();
        updatedData.setCompanyName(companyName);
        updatedData.setEmployeeList(employeeList);

        return repository.updateCompany(selectedCompany, updatedData);

    }

    public Company removeCompany(int companyId) {
        Company selectedCompany = repository.findCompanyByCompanyId(companyId);
        if(selectedCompany == null){
            return null;
        }
        if (!repository.removeCompany(selectedCompany)){
            return null;
        }
        return selectedCompany;
    }
}
