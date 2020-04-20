package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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
        return repository.findById(companyId).orElse(null);
    }

    public List<Employee> getEmployeesForSpecificCompanyByCompanyId(int companyId) {
        Company selectedCompany = repository.findById(companyId).orElse(null);
        return selectedCompany.getEmployeesList();
    }

    public List<Company> getAll(Integer page, Integer pageSize) {

        List<Company> returnCompanyList = repository.findAll();

        if(page != null && pageSize != null){
            returnCompanyList = repository.findAll(PageRequest.of(page,pageSize)).getContent();
        }

        return returnCompanyList;
    }

    public Company createCompany(Company company) {
        return repository.save(company);
    }

    public Company updateCompany(int companyId, Company updateDataCompany) {
        Company selectedCompany = repository.findById(companyId).orElse(null);
        if(selectedCompany == null){
            return null;
        }

        selectedCompany.updateCompany(updateDataCompany);

        return repository.save(selectedCompany);

    }

    public Company removeCompany(int companyId) {
        Company selectedCompany = repository.findById(companyId).orElse(null);

        if(selectedCompany == null){
            return null;
        }

        repository.delete(selectedCompany);
        return selectedCompany;
    }

}
