package com.ideas2it.employeemanagement.sport.controller;

import com.ideas2it.employeemanagement.department.dto.DepartmentDto;
import com.ideas2it.employeemanagement.sport.dto.SportDto;
import com.ideas2it.employeemanagement.sport.service.SportService;
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

import java.util.List;
@RestController
@RequestMapping("sport")
public class SportController {
    @Autowired
    private SportService sportService;

    @PostMapping("/add")
    public ResponseEntity<SportDto> createSport(@RequestBody SportDto sportDto) {
        SportDto createdSport = sportService.addSport(sportDto);
        return new ResponseEntity<>(createdSport, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSport(@PathVariable int id) {
        sportService.deleteSport(id);
        return new ResponseEntity<>(HttpStatus.FOUND);
    }

    @GetMapping("/list")
    public ResponseEntity<List<SportDto>> getAllSports() {
        List<SportDto> sports = sportService.getAllSports();
        return new ResponseEntity<>(sports, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SportDto> getSportById(@PathVariable int id) {
        SportDto sport = sportService.getSportById(id);
        return new ResponseEntity<>(sport, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<SportDto> updateSport(@PathVariable int id, @RequestBody SportDto sportDto) {
        SportDto updatedSport = sportService.updateSport(id, sportDto);
        return new ResponseEntity<>(updatedSport, HttpStatus.OK);
    }

}
