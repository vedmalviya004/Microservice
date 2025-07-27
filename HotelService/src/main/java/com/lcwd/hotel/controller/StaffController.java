package com.lcwd.hotel.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StaffController {

    @GetMapping("/staff")
    public ResponseEntity<List> getStaff(){
        List<String> staff = List.of("Harsh", "Vedant", "Sourabh");
        return ResponseEntity.ok(staff);
    }
}
