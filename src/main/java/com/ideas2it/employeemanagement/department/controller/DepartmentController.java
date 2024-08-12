package com.ideas2it.employeemanagement.department.controller;

import com.ideas2it.employeemanagement.department.dto.DepartmentDto;
import com.ideas2it.employeemanagement.department.mapper.DepartmentMapper;
import com.ideas2it.employeemanagement.department.service.DepartmentService;
import com.ideas2it.employeemanagement.employee.dto.EmployeeDto;
import com.ideas2it.employeemanagement.employee.mapper.EmployeeMapper;
import com.ideas2it.employeemanagement.model.Department;
import com.ideas2it.employeemanagement.model.Employee;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller for managing Department entities.
 * Provides endpoints to create, retrieve, update, delete department,
 * and manage their associated employee.
 */
@RestController
@RequestMapping("api/v1/department")
public class DepartmentController {
    @Autowired
    private  DepartmentService departmentService;

    private static final Logger logger = LogManager.getLogger(DepartmentController.class);

    /**
     * Creates a new department.
     *
     * @param departmentDto {@link DepartmentDto}The DTO containing department data.
     * @return The created department DTO with HTTP status 201 Created.
     */
    @PostMapping("/add")
    public ResponseEntity<DepartmentDto> createDepartment(@Valid @RequestBody DepartmentDto departmentDto) {
        Department department = DepartmentMapper.mapToDepartment(departmentDto);
        Department createdDepartment = departmentService.addDepartment(department);
        DepartmentDto createdDepartmentDto = DepartmentMapper.mapToDepartmentDto(createdDepartment);
        return new ResponseEntity<>(createdDepartmentDto, HttpStatus.CREATED);
    }

    /**
     * Deletes an department by ID.
     *
     * @param id The ID of the department to be deleted.
     * @return HTTP status 204 No Content.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable int id) {
        departmentService.deleteDepartment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Retrieves a list of all active department.
     *
     * @return A list of department DTOs with HTTP status 200 OK.
     */
    @GetMapping("/list")
    public ResponseEntity<List<DepartmentDto>> getAllDepartments() {
        logger.debug("Getting department list");
        List<Department> departments = departmentService.getAllDepartments();
        List<DepartmentDto> departmentDtos = departments.stream()
                .map(DepartmentMapper::mapToDepartmentDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(departmentDtos, HttpStatus.OK);
    }

    /**
     * Retrieves an department by ID.
     *
     * @param id The ID of the department.
     * @return The department DTO with HTTP status 200 OK.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDto> getDepartmentById(@PathVariable int id) {
        Department department = departmentService.getDepartmentById(id);
        DepartmentDto departmentDto = DepartmentMapper.mapToDepartmentDto(department);
        return new ResponseEntity<>(departmentDto, HttpStatus.OK);
    }

    /**
     * Updates an department employee.
     *
     * @param id The ID of the department to be updated.
     * @param departmentDto {@link DepartmentDto} The DTO containing updated department data.
     * @return The updated department DTO with HTTP status 200 OK.
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<DepartmentDto> updateDepartment( @Valid @PathVariable int id, @RequestBody DepartmentDto departmentDto) {
        Department department = DepartmentMapper.mapToDepartment(departmentDto);
        Department updatedDepartment = departmentService.updateDepartment(id, department);
        DepartmentDto updatedDepartmentDto = DepartmentMapper.mapToDepartmentDto(updatedDepartment);
        return new ResponseEntity<>(updatedDepartmentDto, HttpStatus.OK);
    }

    /**
     * Getting  employee List by Department Id.
     *
     * @param departmentId The ID of the department.
     * @return The EmployeeDto List by department Id with HTTP status 200 OK.
     */
    @GetMapping("/employees/{departmentId}")
    public ResponseEntity<List<EmployeeDto>>  getEmployeesByDepartmentId(@PathVariable int departmentId) {
        List<Employee> employees = departmentService.getEmployeesByDepartmentId(departmentId);
        List<EmployeeDto> employeeDtos = new ArrayList<>();
        for(Employee employee : employees) {
            EmployeeDto employeeDto = EmployeeMapper.mapToEmployeeDto(employee);
            employeeDtos.add(employeeDto);
        }
        return new ResponseEntity<>(employeeDtos, HttpStatus.OK);
    }
}