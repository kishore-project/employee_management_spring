package com.ideas2it.employeemanagement.department.service;

import com.ideas2it.employeemanagement.department.dto.DepartmentDto;
import com.ideas2it.employeemanagement.model.Department;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DepartmentService {
    DepartmentDto convertToDTO(Department department);
    Department convertToEntity(DepartmentDto departmentDto);
    DepartmentDto addDepartment(DepartmentDto departmentDTO);
    void deleteDepartment(int id);
    List<DepartmentDto> getAllDepartments() ;
    DepartmentDto getDepartmentById(int id);
    DepartmentDto updateDepartment(int id, DepartmentDto departmentDTO);



}
