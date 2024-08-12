package com.ideas2it.employeemanagement.sport.controller;

import com.ideas2it.employeemanagement.employee.dto.EmployeeDto;
import com.ideas2it.employeemanagement.employee.mapper.EmployeeMapper;
import com.ideas2it.employeemanagement.model.Employee;
import com.ideas2it.employeemanagement.model.Sport;
import com.ideas2it.employeemanagement.sport.dto.SportDto;
import com.ideas2it.employeemanagement.sport.mapper.SportMapper;
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
 * REST controller for managing Sport entities.
 * Provides endpoints to create, retrieve, update, delete sport,
 * and manage their associated employees.
 */
@RestController
@RequestMapping("api/v1/sport")
public class SportController {
    @Autowired
    private SportService sportService;

    private static final Logger logger = LogManager.getLogger(SportController.class);

    /**
     * Creates a new sport.
     *
     * @param sportDto The DTO containing sport data.
     * @return The created sport DTO with HTTP status 201 Created.
     */
    @PostMapping("/add")
    public ResponseEntity<SportDto> createSport(@Valid @RequestBody SportDto sportDto) {
        logger.info("Request to create sport with name: {}", sportDto.getName());
        try {
            Sport sport = SportMapper.mapToSport(sportDto);
            Sport createdSport = sportService.addSport(sport);
            SportDto createdSportDto = SportMapper.mapToSportDto(createdSport);
            logger.info("Sport created with ID: {}", createdSport.getId());
            return new ResponseEntity<>(createdSportDto, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error creating sport", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes a sport by ID.
     *
     * @param id The ID of the sport to be deleted.
     * @return HTTP status 302 Found.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSport(@PathVariable int id) {
        logger.info("Request to delete sport with ID: {}", id);
        try {
            sportService.deleteSport(id);
            logger.info("Sport with ID {} deleted successfully", id);
            return new ResponseEntity<>(HttpStatus.FOUND);
        } catch (Exception e) {
            logger.error("Error deleting sport with ID: {}", id, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Retrieves a list of all active sports.
     *
     * @return A list of sport DTOs with HTTP status 200 OK.
     */
    @GetMapping("/list")
    public ResponseEntity<List<SportDto>> getAllSports() {
        logger.info("Retrieving list of all sports");
        try {
            List<Sport> sports = sportService.getAllSports();
            List<SportDto> sportDtos = new ArrayList<>();
            for (Sport sport : sports) {
                SportDto sportDto = SportMapper.mapToSportDto(sport);
                sportDtos.add(sportDto);
            }
            logger.info("Retrieved {} sports", sportDtos.size());
            return new ResponseEntity<>(sportDtos, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error retrieving list of sports", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Retrieves a sport by ID.
     *
     * @param id The ID of the sport.
     * @return The sport DTO with HTTP status 200 OK.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SportDto> getSportById(@PathVariable int id) {
        logger.info("Request to retrieve sport with ID: {}", id);
        try {
            Sport sport = sportService.getSportById(id);
            SportDto sportDto = SportMapper.mapToSportDto(sport);
            logger.info("Retrieved sport with ID: {}", id);
            return new ResponseEntity<>(sportDto, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error retrieving sport with ID: {}", id, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Updates an existing sport.
     *
     * @param id The ID of the sport to be updated.
     * @param sportDto The DTO containing updated sport data.
     * @return The updated sport DTO with HTTP status 200 OK.
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<SportDto> updateSport(@Valid @PathVariable int id, @RequestBody SportDto sportDto) {
        logger.info("Request to update sport with ID: {}", id);
        try {
            Sport sport = SportMapper.mapToSport(sportDto);
            Sport updatedSport = sportService.updateSport(id, sport);
            SportDto updatedSportDto = SportMapper.mapToSportDto(updatedSport);
            logger.info("Updated sport with ID: {}", id);
            return new ResponseEntity<>(updatedSportDto, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error updating sport with ID: {}", id, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Gets the employee list by Sport Id.
     *
     * @param sportId The ID of the sport.
     * @return The EmployeeDto list by sport Id with HTTP status 200 OK.
     */
    @GetMapping("/{sportId}/employees")
    public ResponseEntity<List<EmployeeDto>> getEmployeesBySportId(@PathVariable int sportId) {
        logger.info("Request to retrieve employees for sport with ID: {}", sportId);
        try {
            List<Employee> employees = sportService.getEmployeesBySportId(sportId);
            List<EmployeeDto> employeeDtos = new ArrayList<>();
            for (Employee employee : employees) {
                EmployeeDto employeeDto = EmployeeMapper.mapToEmployeeDto(employee);
                employeeDtos.add(employeeDto);
            }
            logger.info("Retrieved {} employees for sport with ID: {}", employeeDtos.size(), sportId);
            return new ResponseEntity<>(employeeDtos, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error retrieving employees for sport with ID: {}", sportId, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
