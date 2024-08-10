package com.ideas2it.employeemanagement.department.dao;

import com.ideas2it.employeemanagement.model.Department;
import com.ideas2it.employeemanagement.model.Employee;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DepartmentRepository extends CrudRepository<Department, Integer> {

}
