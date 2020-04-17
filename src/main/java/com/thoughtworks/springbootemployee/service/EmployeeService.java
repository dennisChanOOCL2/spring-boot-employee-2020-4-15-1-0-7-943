package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository repository;

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    public List<Employee> getAll(Integer page, Integer pageSize, String gender){

        List<Employee> returnList = repository.findAllEmployees();

        if(page != null && pageSize != null){
            returnList = repository.findAllEmployeesWithPaging(page, pageSize);
        }

        if(gender != null){
            returnList = returnList.stream()
                    .filter(employee ->
                            employee.getGender().toUpperCase().equals(gender.toUpperCase()))
                    .collect(Collectors.toList());
            if(returnList.size() == 0){
                return null;
            }
        }

        return returnList;
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

        Employee updateData = new Employee();
        updateData.setName(name);
        updateData.setSalary(salary);
        updateData.setAge(age);
        updateData.setGender(gender);

        return repository.updateEmployee(selectedEmployee, updateData);


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
