package com.ideas2it.employeemanagement.department.dao;

import com.ideas2it.employeemanagement.model.Department;
import org.springframework.data.repository.CrudRepository;

public interface DepartmentRepository extends CrudRepository<Department, Integer> {
}
