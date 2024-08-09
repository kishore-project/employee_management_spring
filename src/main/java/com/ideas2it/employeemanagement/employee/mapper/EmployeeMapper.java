package com.ideas2it.employeemanagement.employee.mapper;

import com.ideas2it.employeemanagement.employee.dto.EmployeeDto;
import com.ideas2it.employeemanagement.model.Address;
import com.ideas2it.employeemanagement.model.Department;
import com.ideas2it.employeemanagement.model.Employee;

public class EmployeeMapper {
    // Convert Employee entity to EmployeeDto
    public static EmployeeDto mapToEmployeeDto(Employee employee) {


        Address address = employee.getAddress();

        return new EmployeeDto(employee.getId(),
                employee.getName(),
                employee.getDob(),
                employee.getDepartment().getId(),
                //employee.getDepartment().getName(),
                employee.getEmailId(),
                address.getStreet(),
                address.getCity(),
                address.getState(),
                address.getZip());
    }

    // Convert EmployeeDto to Employee entity
    public static Employee mapToEmployee(EmployeeDto employeeDto, Department department, Address address) {
        Employee employee = new Employee();
        employee.setId(employeeDto.getId());
        employee.setName(employeeDto.getName());
        employee.setDob(employeeDto.getDob());
        employee.setEmailId(employeeDto.getEmailId());
        employee.setDepartment(department);
        employee.setAddress(address);
        return employee;
    }
}
