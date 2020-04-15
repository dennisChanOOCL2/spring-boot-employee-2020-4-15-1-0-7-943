package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private List<Employee> employeeList = new ArrayList<>();
    private int page;
    private int pageSize;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Employee getSpecificEmployee(@PathVariable int id){
        return employeeList.stream()
                .filter(employee -> employee.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Employee>> getAllEmployee(@RequestParam(required = false) Integer page,
                                         @RequestParam(required = false) Integer pageSize
                                         ){
        if(page != null && pageSize != null){
            try{
                return new ResponseEntity<>(employeeList.subList(page * pageSize, pageSize * pageSize + pageSize), HttpStatus.OK);
            }catch(IndexOutOfBoundsException expcetion){
                return new ResponseEntity<>(null, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(employeeList, HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee){
        if(canCreateEmployee(employee)){
            employeeList.add(employee);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }else{
            return new ResponseEntity<>(employee, HttpStatus.CREATED);
        }
    }

    @PostMapping("/init")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<List<Employee>> createEmployeeForTesting(){
        employeeList.add(new Employee(0,"Xiaoming", 20, "Male"));
        employeeList.add(new Employee(1,"Xiaohong", 19, "Female"));
        employeeList.add(new Employee(2,"Xiaozhi", 15, "Male"));
        employeeList.add(new Employee(3,"Xiaogang", 16, "Male"));
        employeeList.add(new Employee(4,"Xiaoxia", 15, "Female"));
        return new ResponseEntity<>(employeeList, HttpStatus.CREATED);
    }

    private boolean canCreateEmployee(Employee employeeForChecking){
        Employee employee = employeeList.stream()
                .filter(existEmployee -> existEmployee.getId() == employeeForChecking.getId())
                .findAny().orElse(null);

        if(employee != null){
            return false;
        }
        return true;
    }


}
