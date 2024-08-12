package com.ideas2it.employeemanagement.department.service;

import com.ideas2it.employeemanagement.department.dto.DepartmentDto;
import com.ideas2it.employeemanagement.model.Department;
import com.ideas2it.employeemanagement.model.Employee;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
public interface DepartmentService {

    /**
     * Adds a new department to the Database.
     *
     * @param department the department entity to be added
     * @return the saved department entity.
     */
    Department addDepartment(Department department);

    /**
     * Retrieves all active department.
     *
     * @return a list of active department entities
     */
    List<Department> getAllDepartments();

    /**
     * Retrieves an departemnt by their ID.
     *
     * @param id the ID of the department to be retrieved
     * @return the employee entity with the specified ID
     */
    Department getDepartmentById(int id);

    /**
     * Updates an existing department.
     *
     * @param department the department entity with updated information
     * @return the updated department entity
     */
    Department updateDepartment(int id, Department department);

    /**
     * Soft deletes an department by setting them as inactive.
     *
     * @param id the ID of the sport to be deleted
     */
    void deleteDepartment(int id);

    /**
     * Retrieves all active employee By department ID.
     *
     * @return a list of active employee entities
     */
    List<Employee> getEmployeesByDepartmentId(int departmentId);
}

