package com.ideas2it.employeemanagement.employee.service;

import com.ideas2it.employeemanagement.employee.dao.EmployeeRepository;
import com.ideas2it.employeemanagement.employee.dto.EmployeeDto;
import com.ideas2it.employeemanagement.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.ideas2it.employeemanagement.employee.mapper.EmployeeMapper.mapToEmployee;
import static com.ideas2it.employeemanagement.employee.mapper.EmployeeMapper.mapToEmployeeDto;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public EmployeeDto addEmployee(EmployeeDto employeeDto) {
        Employee employee = mapToEmployee(employeeDto);
        Employee savedEmployee = employeeRepository.save(employee);
        return mapToEmployeeDto(savedEmployee);
    }

    @Override
    public void deleteEmployee(int id) {
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Department not found with ID: " + id));
        existingEmployee.setActive(true);
        employeeRepository.save(existingEmployee);
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
        List<EmployeeDto> result = new ArrayList<>();
        Iterable<Employee> employees = employeeRepository.findAll();
        for (Employee employee : employees) {
            if (!employee.isActive()) {
                result.add(mapToEmployeeDto(employee));
            }
        }
        return result;
    }

    @Override
    public EmployeeDto getEmployeeById(int id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Department not found with ID: " + id));
        if (employee.) {
            throw new IllegalArgumentException("Department is deleted with ID: " + id);
        }
        return mapToEmployeeDto(employee);
    }

    @Override
    public EmployeeDto updateEmployee(int id, EmployeeDto employeeDto) {
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Department not found with ID: " + id));
        if (existingEmployee.isActive()) {
            throw new IllegalArgumentException("Cannot update a deleted department with ID: " + id);
        }
        existingEmployee.setName(employeeDto.getName());
        Employee updatedEmployee = employeeRepository.save(existingEmployee);
        return mapToEmployeeDto(updatedEmployee);
    }

}
