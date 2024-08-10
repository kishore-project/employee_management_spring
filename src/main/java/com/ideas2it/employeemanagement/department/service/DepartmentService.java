package com.ideas2it.employeemanagement.department.service;

import com.ideas2it.employeemanagement.department.dto.DepartmentDto;
import com.ideas2it.employeemanagement.model.Department;
import com.ideas2it.employeemanagement.model.Employee;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
public interface DepartmentService {
    DepartmentDto addDepartment(DepartmentDto departmentDTO);
    void deleteDepartment(int id);
    List<DepartmentDto> getAllDepartments() ;
    DepartmentDto getDepartmentById(int id);
    DepartmentDto updateDepartment(int id, DepartmentDto departmentDTO);
    List<Employee> getEmployeesByDepartmentId(int departmentId);



}
