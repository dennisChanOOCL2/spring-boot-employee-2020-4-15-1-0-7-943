package com.thoughtworks.springbootemployee.model;


import com.thoughtworks.springbootemployee.CommonTools.CommonUtils;

public class Employee {
    private int id;
    private String name;
    private Integer age;
    private String gender;
    private Integer salary;

    public Employee() {
    }

    public Employee(int id, String name, int age, String gender){
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public Employee(int id, String name, int age, String gender, int salary){
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public void updateEmployee(Employee updateData){
        if(updateData.getName() != null){
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