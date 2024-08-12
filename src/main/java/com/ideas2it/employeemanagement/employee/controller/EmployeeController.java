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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

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

    /**
     * Creates a new employee.
     *
     * @param employeeDto {@link EmployeeDto} The DTO containing employee data.
     * @return The created employee DTO with HTTP status 201 Created.
     */
    @PostMapping("/add")
    public ResponseEntity<EmployeeDto> addEmployee(@RequestBody EmployeeDto employeeDto) {
        Department department = departmentService.getDepartmentById(employeeDto.getDepartmentID());
        Address address = new Address(employeeDto.getStreet(), employeeDto.getCity(), employeeDto.getState(), employeeDto.getZip());

        Employee employee = EmployeeMapper.mapToEmployee(employeeDto);
        employee.setDepartment(department);
        employee.setAddress(address);

        Employee createdEmployee = employeeService.addEmployee(employee);
        return new ResponseEntity<>(EmployeeMapper.mapToEmployeeDto(createdEmployee), HttpStatus.CREATED);
    }

    /**
     * Retrieves a list of all active employees.
     *
     * @return A list of employee DTOs with HTTP status 200 OK.
     */
    @GetMapping("/list")
    public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
        List<EmployeeDto> employeeDtos = employeeService.getAllEmployees().stream()
                .map(EmployeeMapper::mapToEmployeeDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(employeeDtos, HttpStatus.OK);
    }

    /**
     * Retrieves an employee by ID.
     *
     * @param id The ID of the employee.
     * @return The employee DTO with HTTP status 200 OK.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@Valid @PathVariable int id) {
        Employee employee = employeeService.getEmployeeById(id);
        return new ResponseEntity<>(EmployeeMapper.mapToEmployeeDto(employee), HttpStatus.OK);
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
        Employee employee = EmployeeMapper.mapToEmployee(employeeDto);
        Department department = departmentService.getDepartmentById(employeeDto.getDepartmentID());
        Address address = new Address(employeeDto.getStreet(), employeeDto.getCity(), employeeDto.getState(), employeeDto.getZip());

        employee.setId(id);
        employee.setDepartment(department);
        employee.setAddress(address);

        Employee updatedEmployee = employeeService.updateEmployee(employee);
        return new ResponseEntity<>(EmployeeMapper.mapToEmployeeDto(updatedEmployee), HttpStatus.OK);
    }

    /**
     * Deletes an employee by ID.
     *
     * @param id The ID of the employee to be deleted.
     * @return HTTP status 204 No Content.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable int id) {
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
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
        Sport sport = sportService.getSportById(sportId);
        Employee updatedEmployee = employeeService.addSportToEmployee(employeeId, sport);
        return new ResponseEntity<>(EmployeeMapper.mapToEmployeeDto(updatedEmployee), HttpStatus.OK);
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
        Sport sport = sportService.getSportById(sportId);
        Employee updatedEmployee = employeeService.removeSportFromEmployee(employeeId, sport);
        return new ResponseEntity<>(EmployeeMapper.mapToEmployeeDto(updatedEmployee), HttpStatus.OK);
    }
}
