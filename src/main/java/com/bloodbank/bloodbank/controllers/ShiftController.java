package com.bloodbank.bloodbank.controllers;

import com.bloodbank.bloodbank.model.Shift;
import com.bloodbank.bloodbank.service.bank.ShiftService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ShiftController {
    private final ShiftService shiftService;

    public ShiftController(ShiftService shiftService) {
        this.shiftService = shiftService;
    }

    @GetMapping("/shifts")
    public List<Shift> getAll() {
        return shiftService.getAll();
    }
}
