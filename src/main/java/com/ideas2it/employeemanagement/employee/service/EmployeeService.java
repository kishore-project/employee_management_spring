package com.ideas2it.employeemanagement.employee.service;

import com.ideas2it.employeemanagement.model.Employee;
import com.ideas2it.employeemanagement.model.Sport;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface EmployeeService {

    /**
     * Adds a new employee to the system.
     *
     * @param employee the employee entity to be added
     * @return the saved employee entity
     */
    Employee addEmployee(Employee employee);

    /**
     * Retrieves all active employees.
     *
     * @return a list of active employee entities
     */
    List<Employee> getAllEmployees();

    /**
     * Retrieves an employee by their ID.
     *
     * @param id the ID of the employee to be retrieved
     * @return the employee entity with the specified ID
     */
    Employee getEmployeeById(int id);

    /**
     * Updates an existing employee.
     *
     * @param employee the employee entity with updated information
     * @return the updated employee entity
     */
    Employee updateEmployee(Employee employee);

    /**
     * Soft deletes an employee by setting them as inactive.
     *
     * @param id the ID of the employee to be deleted
     */
    void deleteEmployee(int id);

    /**
     * Adds a sport to an employee's list of sports.
     *
     * @param employeeId the ID of the employee
     * @param sport the sport entity to be added
     * @return the updated employee entity
     */
    Employee addSportToEmployee(int employeeId, Sport sport);

    /**
     * Removes a sport from an employee's list of sports.
     *
     * @param employeeId the ID of the employee
     * @param sport the sport entity to be removed
     * @return the updated employee entity
     */
    Employee removeSportFromEmployee(int employeeId, Sport sport);
}
