package com.bloodbank.bloodbank.controllers.bank;

import com.bloodbank.bloodbank.service.bank.BloodBankService;
import org.springframework.beans.factory.annotation.Autowired;

//@Controller
public class BloodBankController {

    @Autowired
    private BloodBankService bloodBankService;

//    @GetMapping("/donations")
//    public List<BloodBank> getAll() {
//        return bloodBankService.getAll();
//    }
}
