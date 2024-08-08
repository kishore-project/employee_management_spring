package com.ideas2it.employeemanagement.department.service;

import com.ideas2it.employeemanagement.department.dto.DepartmentDto;
import com.ideas2it.employeemanagement.department.dao.DepartmentRepository;
import com.ideas2it.employeemanagement.model.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    // Convert entity to DTO
    public DepartmentDto convertToDTO(Department department) {
        return new DepartmentDto(department.getId(), department.getName());
    }

    // Convert DTO to entity
    public Department convertToEntity(DepartmentDto departmentDto) {
        Department department = new Department();
        department.setId(departmentDto.getId());
        department.setName(departmentDto.getName());
        return department;
    }

    public DepartmentDto addDepartment(DepartmentDto departmentDTO) {
        Department department = convertToEntity(departmentDTO);
        Department savedDepartment = departmentRepository.save(department);
        return convertToDTO(savedDepartment);
    }

    public List<DepartmentDto> getAllDepartments() {
        List<DepartmentDto> result = new ArrayList<>();
        Iterable<Department> departments = departmentRepository.findAll();
        for (Department department : departments) {
            result.add(convertToDTO(department));
        }
        return result;
    }

    public DepartmentDto getDepartmentById(int id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Department not found with ID: " + id));
        return convertToDTO(department);
    }

    public DepartmentDto updateDepartment(int id, DepartmentDto departmentDTO) {
        Department existingDepartment = departmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Department not found with ID: " + id));
        existingDepartment.setName(departmentDTO.getName());
        Department updatedDepartment = departmentRepository.save(existingDepartment);
        return convertToDTO(updatedDepartment);
    }

    public void deleteDepartment(int id) {
        departmentRepository.deleteById(id);
    }
}
