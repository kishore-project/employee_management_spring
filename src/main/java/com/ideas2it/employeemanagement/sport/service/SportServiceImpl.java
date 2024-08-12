package com.ideas2it.employeemanagement.sport.service;

import com.ideas2it.employeemanagement.model.Employee;
import com.ideas2it.employeemanagement.model.Sport;
import com.ideas2it.employeemanagement.sport.dao.SportRepository;
import com.ideas2it.employeemanagement.sport.dto.SportDto;
import com.ideas2it.employeemanagement.sport.mapper.SportMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.ideas2it.employeemanagement.sport.mapper.SportMapper.mapToSport;
import static com.ideas2it.employeemanagement.sport.mapper.SportMapper.mapToSportDto;

@Service
public class SportServiceImpl implements SportService {
    @Autowired
    private SportRepository sportRepository;

    @Override
    public Sport addSport(Sport sport) {
        return sportRepository.save(sport);
    }

    @Override
    public List<Sport> getAllSports() {
        List<Sport> result = new ArrayList<>();
        Iterable<Sport> sports = sportRepository.findAll();
        for (Sport sport : sports) {
            if (sport.isActive()) {
                result.add(sport);
            }
        }
        return result;
    }

    @Override
    public Sport getSportById(int id) {
        Sport sport = sportRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Sport not found with ID: " + id));
        if (sport.isActive()) {
            throw new IllegalArgumentException("Sport is deleted with ID: " + id);
        }
        return sport;
    }

    @Override
    public Sport updateSport(int id, Sport sport) {
        Sport existingSport = sportRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Sport not found with ID: " + id));
        if (existingSport.isActive()) {
            throw new IllegalArgumentException("Cannot update a deleted sport with ID: " + id);
        }
        existingSport.setName(sport.getName());
        return sportRepository.save(existingSport);
    }

    @Override
    public void deleteSport(int id) {
        Sport existingSport = sportRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Sport not found with ID: " + id));
        existingSport.setActive(false);
        sportRepository.save(existingSport);
    }

    @Override
    public List<Employee> getEmployeesBySportId(int sportId) {
        Sport sport = sportRepository.findById(sportId)
                .orElseThrow(() -> new IllegalArgumentException("Sport not found with ID: " + sportId));

        List<Employee> activeEmployees = new ArrayList<>();
        for (Employee employee : sport.getEmployees()) {
            if (employee.isActive()) {
                activeEmployees.add(employee);
            }
        }
        return activeEmployees;
    }
}
