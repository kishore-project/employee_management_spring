package com.ideas2it.employeemanagement.employee.service;

import com.ideas2it.employeemanagement.department.dto.DepartmentDto;
import com.ideas2it.employeemanagement.department.mapper.DepartmentMapper;
import com.ideas2it.employeemanagement.department.service.DepartmentService;
import com.ideas2it.employeemanagement.department.service.DepartmentServiceImpl;
import com.ideas2it.employeemanagement.employee.dao.EmployeeRepository;
import com.ideas2it.employeemanagement.employee.dto.EmployeeDto;
import com.ideas2it.employeemanagement.employee.mapper.EmployeeMapper;
import com.ideas2it.employeemanagement.model.Address;
import com.ideas2it.employeemanagement.model.Employee;
import com.ideas2it.employeemanagement.sport.dto.SportDto;
import com.ideas2it.employeemanagement.sport.mapper.SportMapper;
import com.ideas2it.employeemanagement.sport.service.SportService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    private SportService sportService;

    @Autowired
    private DepartmentService departmentService;
    private static final Logger logger = LogManager.getLogger(DepartmentServiceImpl.class);

    @Override
    public EmployeeDto addEmployee(EmployeeDto employeeDto) {
        DepartmentDto department = departmentService.getDepartmentById(employeeDto.getDepartmentID());
        Address address = new Address(employeeDto.getStreet(), employeeDto.getCity(), employeeDto.getState(), employeeDto.getZip());

        Employee employee = mapToEmployee(employeeDto);
        employee.setDepartment(DepartmentMapper.mapToDepartment(department));
        employee.setAddress(address);
        logger.info("Adding Employee with name: {} department ID: {}",employeeDto.getName(),employeeDto.getDepartmentName());
        return EmployeeMapper.mapToEmployeeDto(employeeRepository.save(employee));
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
        List<Employee> allEmployees = (List<Employee>) employeeRepository.findAll();
        List<EmployeeDto> activeEmployeeDtos = new ArrayList<>();

        for (Employee employee : allEmployees) {
            if (employee.isActive()) {
                activeEmployeeDtos.add(mapToEmployeeDto(employee));
            }
        }
        logger.info("Retrieving list of all Employees");
        return activeEmployeeDtos;
    }

    @Override
    public EmployeeDto getEmployeeById(int id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found with ID: " + id));
        logger.error("Employee is deleted with ID: {}", id);
        if (!employee.isActive()) {
            throw new IllegalArgumentException("Employee is inactive with ID: " + id);
        }

        return mapToEmployeeDto(employee);
    }


    @Override
    public EmployeeDto updateEmployee(int id, EmployeeDto employeeDto) {
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found with ID: " + id));

        if (!existingEmployee.isActive()) {
            throw new IllegalArgumentException("Cannot update an inactive employee with ID: " + id);
        }

        DepartmentDto department = departmentService.getDepartmentById(employeeDto.getDepartmentID());
        Address address = new Address(employeeDto.getStreet(), employeeDto.getCity(), employeeDto.getState(), employeeDto.getZip());

        Employee employee = mapToEmployee(employeeDto);
        employee.setId(id);
        employee.setDepartment(DepartmentMapper.mapToDepartment(department));
        employee.setAddress(address);

        Employee updatedEmployee = employeeRepository.save(employee);
        logger.info("Updated Employee with name {}",updatedEmployee.getName());
        return mapToEmployeeDto(updatedEmployee);
    }

    @Override
    public void deleteEmployee(int id) {
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found with ID: " + id));
        logger.error("Employee not found  with ID: {}", id);
        existingEmployee.setActive(false);
        employeeRepository.save(existingEmployee);
    }

    @Override
    public EmployeeDto addSportToEmployee(int employeeId, int sportId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found with ID: " + employeeId));

        SportDto sport = sportService.getSportById(sportId);
        employee.getSports().add(SportMapper.mapToSport(sport));

        Employee updatedEmployee = employeeRepository.save(employee);
        logger.info("Adding sport {} to Employee {}",sport.getName(),employee.getName());
        return mapToEmployeeDto(updatedEmployee);
    }

    @Override
    public EmployeeDto removeSportFromEmployee(int employeeId, int sportId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found with ID: " + employeeId));

        SportDto sport = sportService.getSportById(sportId);
        employee.getSports().remove(SportMapper.mapToSport(sport));

        Employee updatedEmployee = employeeRepository.save(employee);
        logger.info("Removing sport {} to Employee {}",sport.getName(),employee.getName());
        return mapToEmployeeDto(updatedEmployee);
    }
}