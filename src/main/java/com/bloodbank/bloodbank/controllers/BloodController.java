package com.bloodbank.bloodbank.controllers;

import com.bloodbank.bloodbank.model.blood.Blood;
import com.bloodbank.bloodbank.service.bank.BloodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class BloodController {

    @Autowired
    private final BloodService bloodService;

    public BloodController(BloodService bloodService) {
        this.bloodService = bloodService;
    }

    @GetMapping("/bloodInfo")
    public List<Blood> getAll() {
        return bloodService.getAll();
    }
}
