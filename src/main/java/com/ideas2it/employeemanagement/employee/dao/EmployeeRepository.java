package com.ideas2it.employeemanagement.employee.dao;

import com.ideas2it.employeemanagement.model.Employee;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EmployeeRepository extends CrudRepository<Employee, Integer> {
}
