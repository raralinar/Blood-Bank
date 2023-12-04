package com.bloodbank.bloodbank.controllers;

import com.bloodbank.bloodbank.model.Donor;
import com.bloodbank.bloodbank.service.bank.DonorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

//@Controller
public class DonorController {

    private final DonorService donorService;

    public DonorController(DonorService donorService) {
        this.donorService = donorService;
    }

//    @GetMapping("/donors")
//    public String getAll(Model model) {
//        List<Donor> donors = donorService.getAll();
//        model.addAttribute("donors", donors);
//        return "admin/index";
//    }
}
