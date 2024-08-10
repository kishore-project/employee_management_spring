package com.ideas2it.employeemanagement.employee.dto;

import com.ideas2it.employeemanagement.model.Department;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeDto {
    private int id;
    private String name;
    private LocalDate dob;
    private String emailId;
    private int departmentID;
    private String departmentName;
    private String street;
    private String city;
    private String state;
    private String zip;
}
