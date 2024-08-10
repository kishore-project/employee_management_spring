package com.ideas2it.employeemanagement.department.service;

import com.ideas2it.employeemanagement.department.dto.DepartmentDto;
import com.ideas2it.employeemanagement.department.dao.DepartmentRepository;
import com.ideas2it.employeemanagement.model.Department;
import com.ideas2it.employeemanagement.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.ideas2it.employeemanagement.department.mapper.DepartmentMapper.mapToDepartmentDto;
import static com.ideas2it.employeemanagement.department.mapper.DepartmentMapper.mapToDepartment;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public DepartmentDto addDepartment(DepartmentDto departmentDto) {
        Department department = mapToDepartment(departmentDto);
        Department savedDepartment = departmentRepository.save(department);
        return mapToDepartmentDto(savedDepartment);
    }

    @Override
    public List<DepartmentDto> getAllDepartments() {
        List<DepartmentDto> result = new ArrayList<>();
        Iterable<Department> departments = departmentRepository.findAll();
        for (Department department : departments) {
            if (!department.isDeleted()) {
                result.add(mapToDepartmentDto(department));
            }
        }
        return result;
    }

    @Override
    public DepartmentDto getDepartmentById(int id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Department not found with ID: " + id));
        if (department.isDeleted()) {
            throw new IllegalArgumentException("Department is deleted with ID: " + id);
        }
        return mapToDepartmentDto(department);
    }

    @Override
    public DepartmentDto updateDepartment(int id, DepartmentDto departmentDto) {
        Department existingDepartment = departmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Department not found with ID: " + id));
        if (existingDepartment.isDeleted()) {
            throw new IllegalArgumentException("Cannot update a deleted department with ID: " + id);
        }
        existingDepartment.setName(departmentDto.getName());
        Department updatedDepartment = departmentRepository.save(existingDepartment);
        return mapToDepartmentDto(updatedDepartment);
    }

    @Override
    public void deleteDepartment(int id) {
        Department existingDepartment = departmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Department not found with ID: " + id));
        existingDepartment.setDeleted(true);
        departmentRepository.save(existingDepartment);
    }
    public List<Employee> getEmployeesByDepartmentId(int departmentId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new IllegalArgumentException("Department not found with ID: " + departmentId));

        List<Employee> activeEmployees = new ArrayList<>();
        for (Employee employee : department.getEmployees()) {
            if (employee.isActive()) {
                activeEmployees.add(employee);
            }
        }
        return activeEmployees;
    }
}
