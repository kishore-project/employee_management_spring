package com.ideas2it.employeemanagement.employee.mapper;

import com.ideas2it.employeemanagement.department.dto.DepartmentDto;
import com.ideas2it.employeemanagement.employee.dto.EmployeeDto;
import com.ideas2it.employeemanagement.model.Address;
import com.ideas2it.employeemanagement.model.Department;
import com.ideas2it.employeemanagement.model.Employee;

public class EmployeeMapper {
    // Convert Employee entity to EmployeeDto
    public static EmployeeDto mapToEmployeeDto(Employee employee) {
        EmployeeDto dto = new EmployeeDto();
        dto.setId(employee.getId());
        dto.setName(employee.getName());
        dto.setDob(employee.getDob());
        dto.setEmailId(employee.getEmailId());
        dto.setDepartmentID(employee.getDepartment().getId());
        dto.setDepartmentName(employee.getDepartment().getName());
        dto.setStreet(employee.getAddress().getStreet());
        dto.setCity(employee.getAddress().getCity());
        dto.setState(employee.getAddress().getState());
        dto.setZip(employee.getAddress().getZip());
        return dto;
    }

    // Convert EmployeeDto to Employee entity
    public static Employee mapToEmployee(EmployeeDto employeeDto) {
        Employee employee = new Employee();
        employee.setName(employeeDto.getName());
        employee.setDob(employeeDto.getDob());
        employee.setEmailId(employeeDto.getEmailId());
        employee.setActive(true); // Default to active when adding
        return employee;
    }

    // Convert DepartmentDto to Department entity
    public static Department mapToDepartment(DepartmentDto departmentDto) {
        Department department = new Department();
        department.setId(departmentDto.getId());
        department.setName(departmentDto.getName());
        return department;
    }
}
