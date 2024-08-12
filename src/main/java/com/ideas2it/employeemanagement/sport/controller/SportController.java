package com.ideas2it.employeemanagement.sport.controller;

import com.ideas2it.employeemanagement.employee.dto.EmployeeDto;
import com.ideas2it.employeemanagement.employee.mapper.EmployeeMapper;
import com.ideas2it.employeemanagement.model.Employee;
import com.ideas2it.employeemanagement.model.Sport;
import com.ideas2it.employeemanagement.sport.dto.SportDto;
import com.ideas2it.employeemanagement.sport.mapper.SportMapper;
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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller for managing Sport entities.
 * Provides endpoints to create, retrieve, update, delete sport,
 * and manage their associated employee.
 */
@RestController
@RequestMapping("api/v1/sport")
public class SportController {
    @Autowired
    private SportService sportService;

    /**
     * Creates a new sport.
     *
     * @param sportDto The DTO containing sport data.
     * @return The created sport DTO with HTTP status 201 Created.
     */
    @PostMapping("/add")
    public ResponseEntity<SportDto> createSport(@Valid @RequestBody SportDto sportDto) {
        Sport sport = SportMapper.mapToSport(sportDto);
        Sport createdSport = sportService.addSport(sport);
        SportDto createdSportDto = SportMapper.mapToSportDto(createdSport);
        return new ResponseEntity<>(createdSportDto, HttpStatus.CREATED);
    }

    /**
     * Deletes an sport by ID.
     *
     * @param id The ID of the sport to be deleted.
     * @return HTTP status 302 Found.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSport(@PathVariable int id) {
        sportService.deleteSport(id);
        return new ResponseEntity<>(HttpStatus.FOUND);
    }

    /**
     * Retrieves a list of all active sport.
     *
     * @return A list of sport DTOs with HTTP status 200 OK.
     */
    @GetMapping("/list")
    public ResponseEntity<List<SportDto>> getAllSports() {
        List<Sport> sports = sportService.getAllSports();
        List<SportDto> sportDtos = sports.stream()
                .map(SportMapper::mapToSportDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(sportDtos, HttpStatus.OK);
    }

    /**
     * Retrieves an sport by ID.
     *
     * @param id The ID of the sport.
     * @return The sport DTO with HTTP status 200 OK.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SportDto> getSportById(@PathVariable int id) {
        Sport sport = sportService.getSportById(id);
        SportDto sportDto = SportMapper.mapToSportDto(sport);
        return new ResponseEntity<>(sportDto, HttpStatus.OK);
    }

    /**
     * Updates an existing sport.
     *
     * @param id The ID of the sport to be updated.
     * @param sportDto The DTO containing updated employee data.
     * @return The updated sport DTO with HTTP status 200 OK.
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<SportDto> updateSport(@Valid @PathVariable int id, @RequestBody SportDto sportDto) {
        Sport sport = SportMapper.mapToSport(sportDto);
        Sport updatedSport = sportService.updateSport(id, sport);
        SportDto updatedSportDto = SportMapper.mapToSportDto(updatedSport);
        return new ResponseEntity<>(updatedSportDto, HttpStatus.OK);
    }

    /**
     * Getting  employee List by Sport Id.
     *
     * @param sportId The ID of the sport.
     * @return The EmployeeDto List by department Id with HTTP status 200 OK.
     */
    @GetMapping("/{sportId}/employees")
    public ResponseEntity<List<EmployeeDto>> getEmployeesBySportId(@PathVariable int sportId) {
        List<Employee> employees = sportService.getEmployeesBySportId(sportId);

        List<EmployeeDto> employeeDtos = new ArrayList<>();
        for(Employee employee : employees) {
            EmployeeDto employeeDto = EmployeeMapper.mapToEmployeeDto(employee);
            employeeDtos.add(employeeDto);
        }

        return ResponseEntity.ok(employeeDtos);
    }
}
