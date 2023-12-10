package com.bloodbank.bloodbank.controllers.bank;

import com.bloodbank.bloodbank.model.bank.Employee;
import com.bloodbank.bloodbank.model.bank.Shift;
import com.bloodbank.bloodbank.service.bank.EmployeeService;
import com.bloodbank.bloodbank.service.bank.ShiftService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ManagementController {
    private final EmployeeService employeeService;
    private final ShiftService shiftService;


    @Autowired
    public ManagementController(EmployeeService employeeService, ShiftService shiftService) {
        this.employeeService = employeeService;
        this.shiftService = shiftService;
    }

    @GetMapping("/manage")
    public String init(Model model) {
        setModelAttributes(model, employeeService.getAll(), shiftService.getAll());
        return "admin/manage/management";
    }

    private void setModelAttributes(Model model, List<Employee> em, List<Shift> sh) {
        Shift current = shiftService.findByDate(LocalDate.now());
        model.addAttribute("currentShift", current == null ? new ArrayList<>() : List.of(current));
        model.addAttribute("search", new Search());
        model.addAttribute("date", new Date());
        model.addAttribute("employees", em);
        model.addAttribute("shifts", sh);
    }

    @Getter
    @Setter
    @NoArgsConstructor
    static
    class Search {
        private String string;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    static
    class Date {
        private LocalDate from;
        private LocalDate to;
    }

}
