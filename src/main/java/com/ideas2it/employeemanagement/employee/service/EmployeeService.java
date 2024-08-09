package com.ideas2it.employeemanagement.employee.service;

import com.ideas2it.employeemanagement.department.dto.DepartmentDto;
import com.ideas2it.employeemanagement.employee.dao.EmployeeRepository;
import com.ideas2it.employeemanagement.employee.dto.EmployeeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
public interface EmployeeService {

    EmployeeDto addEmployee(EmployeeDto employeeDto);
    void deleteEmployee(int id);
    List<EmployeeDto> getAllEmployees() ;
    EmployeeDto getEmployeeById(int id);
    EmployeeDto updateEmployee(int id, EmployeeDto employeeDto);
}
