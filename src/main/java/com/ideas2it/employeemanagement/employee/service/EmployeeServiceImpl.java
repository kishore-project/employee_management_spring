package com.ideas2it.employeemanagement.employee.service;

import com.ideas2it.employeemanagement.department.dto.DepartmentDto;
import com.ideas2it.employeemanagement.department.service.DepartmentService;
import com.ideas2it.employeemanagement.employee.dao.EmployeeRepository;
import com.ideas2it.employeemanagement.employee.dto.EmployeeDto;
import com.ideas2it.employeemanagement.employee.mapper.EmployeeMapper;
import com.ideas2it.employeemanagement.model.Address;
import com.ideas2it.employeemanagement.model.Department;
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

    @Autowired
    private DepartmentService departmentService; // Use this for department operations

    @Override
    public EmployeeDto addEmployee(EmployeeDto employeeDto) {
        // Retrieve Department by ID from the service
        DepartmentDto departmentDto = departmentService.getDepartmentById(employeeDto.getDepartmentID());
        Department department = EmployeeMapper.mapToDepartment(departmentDto); // Convert DTO to Entity

        Address address = new Address(employeeDto.getStreet(), employeeDto.getCity(), employeeDto.getState(), employeeDto.getZip());

        // Map DTO to entity
        Employee employee = EmployeeMapper.mapToEmployee(employeeDto);
        employee.setDepartment(department);
        employee.setAddress(address);

        // Save employee
        Employee savedEmployee = employeeRepository.save(employee);
        return EmployeeMapper.mapToEmployeeDto(savedEmployee);
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
        List<EmployeeDto> result = new ArrayList<>();
        Iterable<Employee> employees = employeeRepository.findAll();
        for (Employee employee : employees) {
            if (employee.isActive()) { //getIsActive()
                result.add(EmployeeMapper.mapToEmployeeDto(employee));
            }
        }
        return result;
    }

    @Override
    public EmployeeDto getEmployeeById(int id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found with ID: " + id));
        if (!employee.isActive()) {
            throw new IllegalArgumentException("Employee is inactive with ID: " + id);
        }
        return EmployeeMapper.mapToEmployeeDto(employee);
    }

    @Override
    public EmployeeDto updateEmployee(int id, EmployeeDto employeeDto) {
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found with ID: " + id));
        if (!existingEmployee.isActive()) {
            throw new IllegalArgumentException("Cannot update an inactive employee with ID: " + id);
        }

        // Update existing employee fields
        existingEmployee.setName(employeeDto.getName());
        existingEmployee.setDob(employeeDto.getDob());
        existingEmployee.setEmailId(employeeDto.getEmailId());

        // Retrieve Department by ID from the service
        DepartmentDto departmentDto = departmentService.getDepartmentById(employeeDto.getDepartmentID());
        Department department = EmployeeMapper.mapToDepartment(departmentDto); // Convert DTO to Entity

        Address address = new Address(employeeDto.getStreet(), employeeDto.getCity(), employeeDto.getState(), employeeDto.getZip());

        existingEmployee.setDepartment(department);
        existingEmployee.setAddress(address);

        // Save updated employee
        Employee updatedEmployee = employeeRepository.save(existingEmployee);
        return EmployeeMapper.mapToEmployeeDto(updatedEmployee);
    }

    @Override
    public void deleteEmployee(int id) {
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found with ID: " + id));
        existingEmployee.setActive(false);
        employeeRepository.save(existingEmployee);
    }

}
