package com.ideas2it.employeemanagement.employee.dto;

import com.ideas2it.employeemanagement.model.Department;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {
    private int id;
    private String name;
    private LocalDate dob;
    private Department department ; // For input
    //private int departmentName; //for output
    private String emailId;
    private String street;
    private String city;
    private String state;
    private String zip;
}
