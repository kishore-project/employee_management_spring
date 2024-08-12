package com.ideas2it.employeemanagement.employee.service;

import com.ideas2it.employeemanagement.department.service.DepartmentService;
import com.ideas2it.employeemanagement.employee.dao.EmployeeRepository;
import com.ideas2it.employeemanagement.model.Employee;
import com.ideas2it.employeemanagement.model.Sport;
import com.ideas2it.employeemanagement.sport.service.SportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private SportService sportService;

    @Autowired
    private DepartmentService departmentService;

    @Override
    public Employee addEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getAllEmployees() {
        List<Employee> allEmployees = (List<Employee>) employeeRepository.findAll();
        List<Employee> activeEmployees = new ArrayList<>();

        for (Employee employee : allEmployees) {
            if (employee.isActive()) {
                activeEmployees.add(employee);
            }
        }

        return activeEmployees;
    }

    @Override
    public Employee getEmployeeById(int id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found with ID: " + id));
        if (!employee.isActive()) {
            throw new IllegalArgumentException("Employee is inactive with ID: " + id);
        }
        return employee;
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        Employee existingEmployee = employeeRepository.findById(employee.getId())
                .orElseThrow(() -> new IllegalArgumentException("Employee not found with ID: " + employee.getId()));
        if (!existingEmployee.isActive()) {
            throw new IllegalArgumentException("Cannot update an inactive employee with ID: " + employee.getId());
        }
        return employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployee(int id) {
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found with ID: " + id));
        existingEmployee.setActive(false);
        employeeRepository.save(existingEmployee);
    }

    @Override
    public Employee addSportToEmployee(int employeeId, Sport sport) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found with ID: " + employeeId));

        employee.getSports().add(sport);
        return employeeRepository.save(employee);
    }

    @Override
    public Employee removeSportFromEmployee(int employeeId, Sport sport) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found with ID: " + employeeId));

        employee.getSports().remove(sport);
        return employeeRepository.save(employee);
    }
}