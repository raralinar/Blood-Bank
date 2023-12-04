package com.bloodbank.bloodbank.controllers;

import com.bloodbank.bloodbank.model.Employee;
import com.bloodbank.bloodbank.service.bank.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

//@Controller
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

//    @GetMapping("/employees")
//    public List<Employee> getAll() {
//        return employeeService.getAll();
//    }
}
