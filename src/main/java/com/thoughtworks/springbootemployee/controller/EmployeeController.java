package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.CommonTools.CommonUtils;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private static final String MALE = "male";
    private static final String FEMALE = "female";
    private List<Employee> employeeList = new ArrayList<>();
    private CommonUtils commonUtils = new CommonUtils();

    public EmployeeController(){
        employeeList.add(new Employee(0,"Xiaoming", 20, MALE));
        employeeList.add(new Employee(1,"Xiaohong", 19, FEMALE));
        employeeList.add(new Employee(2,"Xiaozhi", 15, MALE));
        employeeList.add(new Employee(3,"Xiaogang", 16, MALE));
        employeeList.add(new Employee(4,"Xiaoxia", 15, FEMALE));
    }


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Employee> getSpecificEmployee(@PathVariable int id){
        Employee selectedEmployee = selectEmployeeById(id);

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
        List<Employee> returnList = new ArrayList<>(employeeList);

        if(gender != null){
            returnList = returnList.stream()
                    .filter(employee -> employee.getGender().toUpperCase().equals(gender.toUpperCase()))
                    .collect(Collectors.toList());
            if(returnList.size() == 0){
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        }

        if(commonUtils.pagingForList(returnList, page, pageSize) == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(commonUtils.pagingForList(returnList, page, pageSize), HttpStatus.OK);

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee){
        if(selectEmployeeById(employee.getId()) != null){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }else{
            employeeList.add(employee);
            return new ResponseEntity<>(employee, HttpStatus.CREATED);
        }
    }

    private Employee selectEmployeeById(int id){
        return employeeList.stream()
                .filter(existEmployee -> existEmployee.getId() == id)
                .findAny().orElse(null);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Employee> updateEmployee(@PathVariable int id,
                                                   @RequestParam(required = false) String name,
                                                   @RequestParam(required = false) Integer age,
                                                   @RequestParam(required = false) String gender,
                                                   @RequestParam(required = false) Integer salary){

        Employee selectedEmployee = selectEmployeeById(id);

        if(selectedEmployee == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        if(name != null){
            selectedEmployee.setName(name);
        }
        if(age != null){
            selectedEmployee.setAge(age);
        }
        if(gender != null){
            if(gender.toUpperCase().equals(MALE.toUpperCase())
                || gender.toUpperCase().equals(FEMALE.toUpperCase())){
                selectedEmployee.setGender(gender);
            }
        }
        if(salary != null){
            selectedEmployee.setSalary(salary);
        }
        return new ResponseEntity<>(selectedEmployee, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Employee> removeEmployee(@PathVariable int id){
        Employee selectedEmployee = selectEmployeeById(id);

        if(selectedEmployee == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        employeeList.remove(selectedEmployee);
        return new ResponseEntity<>(selectedEmployee, HttpStatus.OK);
    }

}
