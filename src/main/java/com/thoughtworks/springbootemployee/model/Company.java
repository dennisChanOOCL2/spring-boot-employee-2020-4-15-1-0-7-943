package com.thoughtworks.springbootemployee.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Company {
    private String companyName;
    private int employeesNumber;
    @Id
    private int companyId;
    private List<Employee> employeeList;


    public Company(String companyName,int companyId, List employeeList){
        this.companyName = companyName;
        this.companyId = companyId;
        this.employeesNumber = employeeList.size();
        this.employeeList = employeeList;
    }

    @OneToMany(targetEntity = Employee.class, mappedBy = "companyId", fetch = FetchType.EAGER)
    public void updateCompany(Company updatedData){
        if(updatedData.getEmployeeList() != null){
            this.setEmployeesNumber(employeeList.size());
            this.setEmployeeList(employeeList);
        }

        if(updatedData.getCompanyName() != null){
            this.setCompanyName(companyName);
        }
    }

}