package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private List<Employee> employeeList = new ArrayList<>();

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getAllEmployee(){
        employeeList.add(new Employee(0,"Xiaoming", 20, "Male"));
        employeeList.add(new Employee(1,"Xiaohong", 19, "Female"));
        employeeList.add(new Employee(2,"Xiaozhi", 15, "Male"));
        employeeList.add(new Employee(3,"Xiaogang", 16, "Male"));
        employeeList.add(new Employee(4,"Xiaoxia", 15, "Female"));

        return employeeList;
    }

}
