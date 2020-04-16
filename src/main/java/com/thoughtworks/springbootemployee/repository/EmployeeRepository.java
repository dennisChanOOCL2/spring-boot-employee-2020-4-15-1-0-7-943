package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.CommonTools.CommonUtils;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EmployeeRepository {

    private List<Employee> employeeList = new ArrayList<>();

    private CommonUtils commonUtils = new CommonUtils();

    public EmployeeRepository(){
        employeeList.add(new Employee(0,"Xiaoming", 20, CommonUtils.MALE));
        employeeList.add(new Employee(1,"Xiaohong", 19, CommonUtils.FEMALE));
        employeeList.add(new Employee(2,"Xiaozhi", 15, CommonUtils.MALE));
        employeeList.add(new Employee(3,"Xiaogang", 16, CommonUtils.MALE));
        employeeList.add(new Employee(4,"Xiaoxia", 15, CommonUtils.FEMALE));
    }

    public List<Employee> findAll(Integer page, Integer pageSize, String gender){

        List<Employee> returnList = new ArrayList<>(employeeList);
        if(gender != null){
            returnList = returnList.stream()
                    .filter(employee -> employee.getGender().toUpperCase().equals(gender.toUpperCase()))
                    .collect(Collectors.toList());
            if(returnList.size() == 0){
                return null;
            }
        }

        returnList = commonUtils.pagingForList(returnList, page, pageSize);
        if(returnList == null){
            return null;
        }

        return returnList;
    }

    public Employee findEmployeeById(int id){
        return employeeList.stream()
                .filter(existEmployee -> existEmployee.getId() == id)
                .findAny().orElse(null);
    }

    public Employee createEmployee(Employee employeeToBeCreate){
        if(findEmployeeById(employeeToBeCreate.getId()) != null){
            return null;
        }else{
            employeeList.add(employeeToBeCreate);
            return employeeToBeCreate;
        }
    }

    public Employee updateEmployee(Employee selectedEmployee, String name, Integer age, String gender, Integer salary) {
        if(name != null){
            selectedEmployee.setName(name);
        }
        if(age != null){
            selectedEmployee.setAge(age);
        }
        if(gender != null){
            if(gender.toUpperCase().equals(CommonUtils.MALE.toUpperCase())
                    || gender.toUpperCase().equals(CommonUtils.FEMALE.toUpperCase())){
                selectedEmployee.setGender(gender);
            }
        }
        if(salary != null){
            selectedEmployee.setSalary(salary);
        }
        return selectedEmployee;
    }

    public boolean removeEmployee(Employee selectedEmployee) {
        return employeeList.remove(selectedEmployee);
    }
}
