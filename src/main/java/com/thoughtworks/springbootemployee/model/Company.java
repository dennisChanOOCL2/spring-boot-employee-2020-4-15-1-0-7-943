package com.thoughtworks.springbootemployee.model;

import java.util.List;

public class Company {
    private String companyName;
    private int employeesNumber;
    private int companyId;
    private List<Employee> employeeList;

    public Company(){

    }

    public Company(String companyName,int companyId, List employeeList){
        this.companyName = companyName;
        this.companyId = companyId;
        this.employeesNumber = employeeList.size();
        this.employeeList = employeeList;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getEmployeesNumber() {
        return employeesNumber;
    }

    public void setEmployeesNumber(int employeesNumber) {
        this.employeesNumber = employeesNumber;
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }
}