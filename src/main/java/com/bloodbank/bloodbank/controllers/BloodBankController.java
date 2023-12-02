package com.bloodbank.bloodbank.controllers;

import com.bloodbank.bloodbank.model.BloodBank;
import com.bloodbank.bloodbank.service.bank.BloodBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class BloodBankController {

    @Autowired
    private BloodBankService bloodBankService;

    @GetMapping("/donations")
    public List<BloodBank> getAll() {
        return bloodBankService.getAll();
    }
}
