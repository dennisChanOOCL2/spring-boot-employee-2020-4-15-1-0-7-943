package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Employee> getSpecificEmployee(@PathVariable int id){

        Employee selectedEmployee = employeeService.getSpecificEmployee(id);
        if(selectedEmployee == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<>(selectedEmployee, HttpStatus.OK);
        }
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Employee>> getAllEmployee(@RequestParam(required = false) Integer page,
                                                         @RequestParam(required = false) Integer pageSize,
                                                         @RequestParam(required = false) String gender){

        List<Employee> returnList = employeeService.getAll(page, pageSize, gender);
        if(returnList == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(returnList, HttpStatus.OK);

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee){

        Employee employeeToBeCreated = employeeService.createEmployee(employee);
        if(employeeToBeCreated == null){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }else{
            return new ResponseEntity<>(employeeToBeCreated, HttpStatus.CREATED);
        }

    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Employee> updateEmployee(@PathVariable int id,
                                                   @RequestParam(required = false) String name,
                                                   @RequestParam(required = false) Integer age,
                                                   @RequestParam(required = false) String gender,
                                                   @RequestParam(required = false) Integer salary){
        Employee employeeToBeUpdated = employeeService.updateEmployee(id, name, age, gender, salary);
        if(employeeToBeUpdated == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(employeeToBeUpdated, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Employee> removeEmployee(@PathVariable int id){

        Employee employeeToBeRemoved = employeeService.removeEmployee(id);
        if(employeeToBeRemoved == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(employeeToBeRemoved, HttpStatus.OK);

    }

}
