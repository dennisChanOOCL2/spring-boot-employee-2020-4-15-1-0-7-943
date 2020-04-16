package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.CommonTools.CommonUtils;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CompanyRepository {
    private static final String MALE = "male";
    private static final String FEMALE = "female";
    private List<Company> companyList = new ArrayList<>();
    private CommonUtils commonUtils = new CommonUtils();

    public CompanyRepository(){
        List<Employee> companyOneEmployeeList = new ArrayList<>();
        List<Employee> companyTwoEmployeeList = new ArrayList<>();

        companyOneEmployeeList.add(new Employee(4,"alibaba1", 20, MALE, 6000));
        companyOneEmployeeList.add(new Employee(11,"tengxun2", 19, FEMALE, 7000));

        companyTwoEmployeeList.add(new Employee(6,"alibaba3", 19, MALE, 8000));

        companyList.add(new Company("alibaba", 0, 200, companyOneEmployeeList));
        companyList.add(new Company("tengxun", 1,200, companyTwoEmployeeList));
    }

    public Company getCompanyById(int companyId){
        return companyList.stream()
                .filter(company -> company.getCompanyId() == companyId)
                .findFirst()
                .orElse(null);
    }



}
