package com.thoughtworks.springbootemployee.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int companyId;

    @OneToMany(cascade=CascadeType.ALL, targetEntity = Employee.class, mappedBy = "companyId", fetch = FetchType.EAGER)
    private List<Employee> employeesList;

    public void updateCompany(Company updatedData){
        if(updatedData.getEmployeesList() != null){
            this.setEmployeesNumber(updatedData.getEmployeesList().size());
            this.setEmployeesList(updatedData.getEmployeesList());
        }

        if(updatedData.getCompanyName() != null){
            this.setCompanyName(companyName);
        }
    }

}