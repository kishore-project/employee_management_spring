package com.ideas2it.employeemanagement.department.mapper;

import com.ideas2it.employeemanagement.department.dto.DepartmentDto;
import com.ideas2it.employeemanagement.model.Department;

public class DepartmentMapper {
    // Convert entity to DTO
    public static DepartmentDto mapToDepartmentDto(Department department) {
        return new DepartmentDto(department.getId(), department.getName());
    }

    // Convert DTO to entity
    public static Department mapToDepartment(DepartmentDto departmentDto) {
        return Department.builder()
                .id(departmentDto.getId())
                .name(departmentDto.getName())
                .build();
    }
}
