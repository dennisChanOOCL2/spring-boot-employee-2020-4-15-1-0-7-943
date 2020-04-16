package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository repository;

    @Autowired
    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    public List<Employee> getAll(Integer page, Integer pageSize, String gender){
        return repository.findAll(page, pageSize, gender);
    }

    public Employee getSpecificEmployee(int id){
        return repository.findEmployeeById(id);
    }

    public Employee createEmployee(Employee employee){
        return repository.createEmployee(employee);
    }


    public Employee updateEmployee(int id, String name, Integer age, String gender, Integer salary) {
        Employee selectedEmployee = repository.findEmployeeById(id);
        if(selectedEmployee == null){
            return null;
        }

        return repository.updateEmployee(selectedEmployee, name, age, gender, salary);


    }

    public Employee removeEmployee(int id) {
        Employee selectedEmployee = repository.findEmployeeById(id);
        if(selectedEmployee == null){
            return null;
        }
        if (!repository.removeEmployee(selectedEmployee)){
            return null;
        }
        return selectedEmployee;

    }
}
