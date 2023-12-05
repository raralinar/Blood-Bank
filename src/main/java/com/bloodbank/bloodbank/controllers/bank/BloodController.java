package com.bloodbank.bloodbank.controllers.bank;

import com.bloodbank.bloodbank.service.bank.BloodService;
import org.springframework.beans.factory.annotation.Autowired;

//@Controller
public class BloodController {

    @Autowired
    private final BloodService bloodService;

    public BloodController(BloodService bloodService) {
        this.bloodService = bloodService;
    }

//    @GetMapping("/bloodInfo")
//    public List<Blood> getAll() {
//        return bloodService.getAll();
//    }
}
