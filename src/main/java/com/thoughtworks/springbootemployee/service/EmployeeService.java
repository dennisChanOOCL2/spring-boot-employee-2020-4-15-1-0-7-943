package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository repository;

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    public List<Employee> getAll(Integer page, Integer pageSize, String gender){

        Pageable pageable = null;

        if(page != null && pageSize != null){
            pageable = PageRequest.of(page,pageSize);
        }

        if(gender != null){
            return repository.findAllByGender(gender, pageable);
        }

        return repository.findAll(pageable).getContent();
    }

    public Employee getSpecificEmployee(int id){
        return repository.findById(id).orElse(null);
    }

    public Employee createEmployee(Employee employee){
        if(repository.findById(employee.getId()).orElse(null) == null){
            return repository.save(employee);
        }
        return null;
    }

    public Employee updateEmployee(Integer id, String name, Integer age, String gender, Integer salary) {
        Employee selectedEmployee = repository.findById(id).orElse(null);
        if(selectedEmployee == null){
            return null;
        }

        Employee updateData = new Employee();
        updateData.setName(name);
        updateData.setSalary(salary);
        updateData.setAge(age);
        updateData.setGender(gender);

        selectedEmployee.updateEmployee(updateData);
        return repository.save(selectedEmployee);

    }

    public Employee removeEmployee(int id) {
        Employee selectedEmployee = repository.findById(id).orElse(null);
        if(selectedEmployee == null){
            return null;
        }
        repository.delete(selectedEmployee);
        return selectedEmployee;
    }
}
