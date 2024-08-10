package com.ideas2it.employeemanagement.sport.service;

import com.ideas2it.employeemanagement.model.Sport;
import com.ideas2it.employeemanagement.sport.dao.SportRepository;
import com.ideas2it.employeemanagement.sport.dto.SportDto;
import com.ideas2it.employeemanagement.sport.mapper.SportMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.ideas2it.employeemanagement.sport.mapper.SportMapper.mapToSport;
import static com.ideas2it.employeemanagement.sport.mapper.SportMapper.mapToSportDto;

@Service
public class SportServiceImpl implements SportService {
    @Autowired
    private SportRepository sportRepository;

    @Override
    public SportDto addSport(SportDto sportDto) {
        Sport sport = mapToSport(sportDto);
        Sport savedSport = sportRepository.save(sport);
        return mapToSportDto(savedSport);
    }
    @Override
    public List<SportDto> getAllSports() {
        List<SportDto> result = new ArrayList<>();
        Iterable<Sport> sports = sportRepository.findAll();
        for (Sport sport : sports) {
            if (!sport.isActive()) { //getIsActive()
                result.add(SportMapper.mapToSportDto(sport));
            }
        }
        return result;
    }

    @Override
    public SportDto getSportById(int id) {
        Sport sport = sportRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Sport not found with ID: " + id));
        if (!sport.isActive()) {
            throw new IllegalArgumentException("Sport is deleted with ID: " + id);
        }
        return mapToSportDto(sport);
    }

    @Override
    public SportDto updateSport(int id, SportDto sportDto) {
        Sport existingSport = sportRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Sport not found with ID: " + id));
        if (existingSport.isActive()) {
            throw new IllegalArgumentException("Cannot update a deleted department with ID: " + id);
        }
        existingSport.setName(sportDto.getName());
        Sport updatedSport = sportRepository.save(existingSport);
        return mapToSportDto(updatedSport);
    }

    @Override
    public void deleteSport(int id) {
        Sport existingSport = sportRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Sport not found with ID: " + id));
        existingSport.setActive(true);
        sportRepository.save(existingSport);
    }

}
