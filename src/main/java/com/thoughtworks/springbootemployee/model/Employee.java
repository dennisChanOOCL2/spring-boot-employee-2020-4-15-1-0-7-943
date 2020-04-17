package com.thoughtworks.springbootemployee.model;

import com.thoughtworks.springbootemployee.CommonTools.CommonUtils;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Integer age;
    private String gender;
    private Integer salary;


    public void updateEmployee(Employee updateData){
        if(name != null){
            this.setName(updateData.getName());
        }
        if(updateData.getAge() != null){
            this.setAge(updateData.getAge());
        }
        if(updateData.getGender() != null){
            if(updateData.getGender().toUpperCase().equals(CommonUtils.MALE.toUpperCase())
                    || updateData.getGender().toUpperCase().equals(CommonUtils.FEMALE.toUpperCase())){
                this.setGender(updateData.getGender());
            }
        }
        if(updateData.getSalary() != null){
            this.setSalary(updateData.getSalary());
        }
    }


}