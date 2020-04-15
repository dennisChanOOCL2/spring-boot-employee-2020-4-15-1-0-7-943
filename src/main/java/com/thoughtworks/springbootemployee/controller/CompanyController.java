package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
public class CompanyController {
    public static final String MALE = "male";
    public static final String FEMALE = "female";
    private List<Employee> employeeList = new ArrayList<>();



    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Employee> getSpecificEmployee(@PathVariable int id){
        return new ResponseEntity<>(employeeList.stream()
                .filter(employee -> employee.getId() == id)
                .findFirst()
                .orElse(null), HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Employee>> getAllEmployee(@RequestParam(required = false) Integer page,
                                                         @RequestParam(required = false) Integer pageSize,
                                                         @RequestParam(required = false) String gender
                                         ){
        List<Employee> returnList = new ArrayList<>(employeeList);

        if(gender != null){
            returnList = returnList.stream().filter(employee -> employee.getGender().equals(gender)).collect(Collectors.toList());
            if(returnList.size() == 0){
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
        }

        if(page != null && pageSize != null){
            try{
                return new ResponseEntity<>(returnList.subList(page * pageSize, pageSize * pageSize + pageSize), HttpStatus.OK);
            }catch(IndexOutOfBoundsException expcetion){
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(returnList, HttpStatus.OK);
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
        employeeList.add(new Employee(0,"Xiaoming", 20, MALE));
        employeeList.add(new Employee(1,"Xiaohong", 19, FEMALE));
        employeeList.add(new Employee(2,"Xiaozhi", 15, MALE));
        employeeList.add(new Employee(3,"Xiaogang", 16, MALE));
        employeeList.add(new Employee(4,"Xiaoxia", 15, FEMALE));
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

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Employee> updateEmployee(@PathVariable int id,
                                                   @RequestParam(required = false) String name,
                                                   @RequestParam(required = false) Integer age,
                                                   @RequestParam(required = false) String gender,
                                                   @RequestParam(required = false) Integer salary){

        Employee selectedEmployee = employeeList.stream()
                .filter(employee -> employee.getId() == id)
                .findFirst()
                .orElse(null);

        if(selectedEmployee == null){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
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
        return new ResponseEntity<>(selectedEmployee, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Employee> removeEmployee(@PathVariable int id){
        Employee selectedEmployee = employeeList.stream()
                .filter(employee -> employee.getId() == id)
                .findFirst()
                .orElse(null);

        if(selectedEmployee != null){
            employeeList.remove(selectedEmployee);
            return new ResponseEntity<>(selectedEmployee, HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

    }

}
