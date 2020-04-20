package com.thoughtworks.springbootemployee.model;

import com.thoughtworks.springbootemployee.CommonTools.CommonUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer employeeId;

    private Integer companyId;
    private String name;
    private Integer age;
    private String gender;
    private Integer salary;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "employeeId", referencedColumnName = "employeeId")
    private ParkingBoy parkingBoy;

    public void updateEmployee(Employee updateData){
        if(updateData.getName() != null){
            setName(updateData.getName());
        }
        if(updateData.getAge() != null){
            setAge(updateData.getAge());
        }
        if(updateData.getGender() != null){
            if(updateData.getGender().toUpperCase().equals(CommonUtils.MALE.toUpperCase())
                    || updateData.getGender().toUpperCase().equals(CommonUtils.FEMALE.toUpperCase())){
                setGender(updateData.getGender());
            }
        }
        if(updateData.getCompanyId() != null){
            setCompanyId(updateData.getCompanyId());
        }
        if(updateData.getSalary() != null){
            setSalary(updateData.getSalary());
        }
    }
}