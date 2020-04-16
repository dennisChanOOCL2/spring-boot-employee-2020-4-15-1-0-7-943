package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return repository.findAll(page, pageSize);
    }

    public Company createCompany(Company company) {
        return repository.addCompany(company);
    }

    public Company updateCompany(int companyId, String companyName, List<Employee> employeeList) {
        Company selectedCompany = repository.findCompanyByCompanyId(companyId);
        if(selectedCompany == null){
            return null;
        }

        return repository.updateCompany(selectedCompany, companyName, employeeList);

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
