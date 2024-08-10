package com.ideas2it.employeemanagement.sport.mapper;


import com.ideas2it.employeemanagement.model.Sport;
import com.ideas2it.employeemanagement.sport.dto.SportDto;

public class SportMapper {
    // Convert entity to DTO
    public static SportDto mapToSportDto(Sport sport) {
        return SportDto.builder()
                .id(sport.getId())
                .name(sport.getName())
                .build();
    }

    // Convert DTO to entity
    public static Sport mapToSport(SportDto sportDto) {
        return Sport.builder()
                .id(sportDto.getId())
                .name(sportDto.getName())
                .build();
    }
}
