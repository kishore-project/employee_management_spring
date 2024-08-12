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
    public Department addDepartment(Department department) {
        return departmentRepository.save(department);
    }

    @Override
    public List<Department> getAllDepartments() {
        List<Department> result = new ArrayList<>();
        Iterable<Department> departments = departmentRepository.findAll();
        for (Department department : departments) {
            if (!department.isDeleted()) {
                result.add(department);
            }
        }
        return result;
    }

    @Override
    public Department getDepartmentById(int id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Department not found with ID: " + id));
        if (department.isDeleted()) {
            throw new IllegalArgumentException("Department is deleted with ID: " + id);
        }
        return department;
    }

    @Override
    public Department updateDepartment(int id, Department department) {
        Department existingDepartment = departmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Department not found with ID: " + id));
        if (existingDepartment.isDeleted()) {
            throw new IllegalArgumentException("Cannot update a deleted department with ID: " + id);
        }
        existingDepartment.setName(department.getName());
        return departmentRepository.save(existingDepartment);
    }

    @Override
    public void deleteDepartment(int id) {
        Department existingDepartment = departmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Department not found with ID: " + id));
        existingDepartment.setDeleted(true);
        departmentRepository.save(existingDepartment);
    }

    @Override
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