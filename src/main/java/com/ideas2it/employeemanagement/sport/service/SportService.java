package com.ideas2it.employeemanagement.sport.service;

import com.ideas2it.employeemanagement.department.dto.DepartmentDto;
import com.ideas2it.employeemanagement.model.Employee;
import com.ideas2it.employeemanagement.model.Sport;
import com.ideas2it.employeemanagement.sport.dto.SportDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SportService {

    /**
     * Adds a new sport to the Database.
     *
     * @param sport the sport entity to be added
     * @return the saved sport entity.
     */
    Sport addSport(Sport sport);

    /**
     * Retrieves all active sport.
     *
     * @return a list of active sport entities
     */
    List<Sport> getAllSports();

    /**
     * Retrieves a sport by their ID.
     *
     * @param id the ID of the sport to be retrieved
     * @return the sport entity with the specified ID
     */
    Sport getSportById(int id);

    /**
     * Updates an existing sport.
     *
     * @param sport the sport entity with updated information
     * @return the updated sport entity
     */
    Sport updateSport(int id, Sport sport);

    /**
     * Soft deletes a sport by setting them as inactive.
     *
     * @param id the ID of the sport to be deleted
     */
    void deleteSport(int id);
    /**
     * Retrieves all active employee By sport ID.
     *
     * @return a list of active employee entities
     */
    List<Employee> getEmployeesBySportId(int sportId);
}
