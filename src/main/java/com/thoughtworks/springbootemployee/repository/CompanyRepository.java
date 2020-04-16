package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.CommonTools.CommonUtils;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CompanyRepository {
    private List<Company> companyList = new ArrayList<>();
    private CommonUtils commonUtils = new CommonUtils();

    public CompanyRepository(){
        List<Employee> companyOneEmployeeList = new ArrayList<>();
        List<Employee> companyTwoEmployeeList = new ArrayList<>();

        companyOneEmployeeList.add(new Employee(4,"alibaba1", 20, commonUtils.MALE, 6000));
        companyOneEmployeeList.add(new Employee(11,"tengxun2", 19, commonUtils.FEMALE, 7000));
        companyTwoEmployeeList.add(new Employee(6,"alibaba3", 19, commonUtils.MALE, 8000));

        companyList.add(new Company("alibaba", 0, 200, companyOneEmployeeList));
        companyList.add(new Company("tengxun", 1,200, companyTwoEmployeeList));
    }

    public Company findCompanyByCompanyId(int companyId){
        return companyList.stream()
                .filter(company -> company.getCompanyId() == companyId)
                .findFirst()
                .orElse(null);
    }

    public List<Employee> findEmployeeListForCompany(Company company){
        return company.getEmployeeList();
    }

    public List<Company> findAll(Integer page, Integer pageSize){

        List<Company> returnList = new ArrayList<>(companyList);

        returnList = commonUtils.pagingForList(returnList, page, pageSize);
        if(returnList == null){
            return null;
        }

        return returnList;
    }

    public Company addCompany(Company companyToBeCreated) {
        if(findCompanyByCompanyId(companyToBeCreated.getCompanyId()) != null){
            return null;
        }else{
            companyList.add(companyToBeCreated);
            return companyToBeCreated;
        }
    }

    public Company updateCompany(Company selectedCompany, String companyName, List<Employee> employeeList) {

        if(selectedCompany == null){
            return null;
        }

        if(employeeList != null){
            selectedCompany.setEmployeesNumber(employeeList.size());
            selectedCompany.setEmployeeList(employeeList);
        }

        if(companyName != null){
            selectedCompany.setCompanyName(companyName);
        }

        return selectedCompany;
    }

    public boolean removeCompany(Company selectedCompany) {
        return companyList.remove(selectedCompany);
    }
}
