package com.ideas2it.employeemanagement.sport.service;

import com.ideas2it.employeemanagement.department.dto.DepartmentDto;
import com.ideas2it.employeemanagement.sport.dto.SportDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SportService {
    SportDto addSport(SportDto sportDTO);
    void deleteSport(int id);
    List<SportDto> getAllSports() ;
    SportDto getSportById(int id);
    SportDto updateSport(int id, SportDto sportDTO);
}
