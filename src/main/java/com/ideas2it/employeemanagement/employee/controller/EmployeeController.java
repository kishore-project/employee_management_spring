package com.ideas2it.employeemanagement.employee.controller;

import com.ideas2it.employeemanagement.department.service.DepartmentService;
import com.ideas2it.employeemanagement.employee.dto.EmployeeDto;
import com.ideas2it.employeemanagement.employee.mapper.EmployeeMapper;
import com.ideas2it.employeemanagement.employee.service.EmployeeService;
import com.ideas2it.employeemanagement.model.Address;
import com.ideas2it.employeemanagement.model.Department;
import com.ideas2it.employeemanagement.model.Employee;
import com.ideas2it.employeemanagement.model.Sport;
import com.ideas2it.employeemanagement.sport.service.SportService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * REST controller for managing Employee entities.
 * Provides endpoints to create, retrieve, update, delete employees,
 * and manage their associated department & sports.
 */
@RestController
@RequestMapping("api/v1/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private SportService sportService;

    private static final Logger logger = LogManager.getLogger(EmployeeController.class);

    /**
     * Creates a new employee.
     *
     * @param employeeDto {@link EmployeeDto} The DTO containing employee data.
     * @return The created employee DTO with HTTP status 201 Created.
     */
    @PostMapping("/add")
    public ResponseEntity<EmployeeDto> addEmployee(@Valid @RequestBody EmployeeDto employeeDto) {
        logger.info("Request to create employee with details: {}", employeeDto);
        try {
            Department department = departmentService.getDepartmentById(employeeDto.getDepartmentID());
            Address address = new Address(employeeDto.getStreet(), employeeDto.getCity(), employeeDto.getState(), employeeDto.getZip());

            Employee employee = EmployeeMapper.mapToEmployee(employeeDto);
            employee.setDepartment(department);
            employee.setAddress(address);

            Employee createdEmployee = employeeService.addEmployee(employee);
            EmployeeDto createdEmployeeDto = EmployeeMapper.mapToEmployeeDto(createdEmployee);

            logger.info("Employee created with ID: {}", createdEmployee.getId());
            return new ResponseEntity<>(createdEmployeeDto, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error creating employee", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Retrieves a list of all active employees.
     *
     * @return A list of employee DTOs with HTTP status 200 OK.
     */
    @GetMapping("/list")
    public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
        logger.info("Request to retrieve all employees");
        try {
            List<Employee> employees = employeeService.getAllEmployees();
            List<EmployeeDto> employeeDtos = new ArrayList<>();
            for (Employee employee : employees) {
                EmployeeDto employeeDto = EmployeeMapper.mapToEmployeeDto(employee);
                employeeDtos.add(employeeDto);
            }

            logger.info("Retrieved {} employees", employeeDtos.size());
            return new ResponseEntity<>(employeeDtos, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error retrieving employees", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Retrieves an employee by ID.
     *
     * @param id The ID of the employee.
     * @return The employee DTO with HTTP status 200 OK.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@Valid @PathVariable int id) {
        logger.info("Request to retrieve employee with ID: {}", id);
        try {
            Employee employee = employeeService.getEmployeeById(id);
            EmployeeDto employeeDto = EmployeeMapper.mapToEmployeeDto(employee);

            logger.info("Retrieved employee with ID: {}", id);
            return new ResponseEntity<>(employeeDto, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error retrieving employee with ID: {}", id, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Updates an existing employee.
     *
     * @param id The ID of the employee to be updated.
     * @param employeeDto {@link EmployeeDto} The DTO containing updated employee data.
     * @return The updated employee DTO with HTTP status 200 OK.
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<EmployeeDto> updateEmployee(@Valid @PathVariable int id, @RequestBody EmployeeDto employeeDto) {
        logger.info("Request to update employee with ID: {}", id);
        try {
            Employee employee = EmployeeMapper.mapToEmployee(employeeDto);
            Department department = departmentService.getDepartmentById(employeeDto.getDepartmentID());
            Address address = new Address(employeeDto.getStreet(), employeeDto.getCity(), employeeDto.getState(), employeeDto.getZip());

            employee.setId(id);
            employee.setDepartment(department);
            employee.setAddress(address);

            Employee updatedEmployee = employeeService.updateEmployee(employee);
            EmployeeDto updatedEmployeeDto = EmployeeMapper.mapToEmployeeDto(updatedEmployee);

            logger.info("Updated employee with ID: {}", id);
            return new ResponseEntity<>(updatedEmployeeDto, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error updating employee with ID: {}", id, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes an employee by ID.
     *
     * @param id The ID of the employee to be deleted.
     * @return HTTP status 204 No Content.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable int id) {
        logger.info("Request to delete employee with ID: {}", id);
        try {
            employeeService.deleteEmployee(id);
            logger.info("Employee with ID {} deleted successfully", id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error("Error deleting employee with ID: {}", id, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Adds a sport to an employee's list of sports.
     *
     * @param employeeId The ID of the employee.
     * @param sportId The ID of the sport to be added.
     * @return The updated employee DTO with HTTP status 200 OK.
     */
    @PutMapping("/{employeeId}/addSport/{sportId}")
    public ResponseEntity<EmployeeDto> addSportToEmployee(@PathVariable int employeeId, @PathVariable int sportId) {
        logger.info("Request to add sport with ID: {} to employee with ID: {}", sportId, employeeId);
        try {
            Sport sport = sportService.getSportById(sportId);
            Employee updatedEmployee = employeeService.addSportToEmployee(employeeId, sport);
            EmployeeDto updatedEmployeeDto = EmployeeMapper.mapToEmployeeDto(updatedEmployee);

            logger.info("Added sport with ID: {} to employee with ID: {}", sportId, employeeId);
            return new ResponseEntity<>(updatedEmployeeDto, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error adding sport with ID: {} to employee with ID: {}", sportId, employeeId, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Removes a sport from an employee's list of sports.
     *
     * @param employeeId The ID of the employee.
     * @param sportId The ID of the sport to be removed.
     * @return The updated employee DTO with HTTP status 200 OK.
     */
    @PutMapping("/{employeeId}/removeSport/{sportId}")
    public ResponseEntity<EmployeeDto> removeSportFromEmployee(@PathVariable int employeeId, @PathVariable int sportId) {
        logger.info("Request to remove sport with ID: {} from employee with ID: {}", sportId, employeeId);
        try {
            Sport sport = sportService.getSportById(sportId);
            Employee updatedEmployee = employeeService.removeSportFromEmployee(employeeId, sport);
            EmployeeDto updatedEmployeeDto = EmployeeMapper.mapToEmployeeDto(updatedEmployee);

            logger.info("Removed sport with ID: {} from employee with ID: {}", sportId, employeeId);
            return new ResponseEntity<>(updatedEmployeeDto, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error removing sport with ID: {} from employee with ID: {}", sportId, employeeId, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
