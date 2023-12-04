package com.bloodbank.bloodbank.controllers;

import com.bloodbank.bloodbank.model.InactiveDonor;
import com.bloodbank.bloodbank.service.bank.InactiveDonorService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

//@Controller
public class InactiveDonorController {
    private final InactiveDonorService inactiveDonorService;

    public InactiveDonorController(InactiveDonorService inactiveDonorService) {
        this.inactiveDonorService = inactiveDonorService;
    }

//    @GetMapping("/inactiveDonor")
//    public List<InactiveDonor> getAll() {
//        return inactiveDonorService.getAll();
//    }
}
