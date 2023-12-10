package com.bloodbank.bloodbank.controllers.bank;

import com.bloodbank.bloodbank.controllers.bank.ManagementController.Date;
import com.bloodbank.bloodbank.controllers.bank.ManagementController.Search;
import com.bloodbank.bloodbank.dto.ShiftDTO;
import com.bloodbank.bloodbank.model.bank.Employee;
import com.bloodbank.bloodbank.model.bank.Shift;
import com.bloodbank.bloodbank.service.bank.EmployeeService;
import com.bloodbank.bloodbank.service.bank.ShiftService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ShiftController implements IController<Date, ShiftDTO> {
    private final EmployeeService employeeService;
    private final ShiftService shiftService;
    private String action = "add", mainPage = "admin/manage/management", editPage = "admin/manage/editSH";

    @Autowired
    public ShiftController(EmployeeService employeeService, ShiftService shiftService) {
        this.employeeService = employeeService;
        this.shiftService = shiftService;
    }

    @RequestMapping("/shift/search")
    public String search(@Valid @ModelAttribute Date date, Model model) {
        List<Shift> shifts;
        if (date.getFrom() == null && date.getTo() == null) {
            shifts = shiftService.getAll();
            setModelAttributes(model, new Search(), date, employeeService.getAll(), shifts);
            return mainPage;
        }
        date.setFrom(date.getFrom() == null ? LocalDate.now() : date.getFrom());
        date.setTo(date.getTo() == null ? LocalDate.now() : date.getTo());
        shifts = shiftService.findAllShiftsWithDateRange(date.getFrom(), date.getTo());
        Shift current = shiftService.findByDate(LocalDate.now());
        model.addAttribute("currentShift", current == null ? new ArrayList<>() : List.of(current));
        setModelAttributes(model, new Search(), date, employeeService.getAll(), shifts);
        return mainPage;
    }

    @GetMapping("/shift/add")
    public String add(Model model) {
        action = "add";
        model.addAttribute("shift", new ShiftDTO());
        model.addAttribute("employees", employeeService.findByName().stream()
                .map(em -> em[0] + " " + em[1] + " " + em[2] + " " + em[3]).toList());
        model.addAttribute("search", new Search());
        return editPage;
    }

    @PostMapping("/shift/add")
    public String add(@Valid @ModelAttribute("shift") ShiftDTO shiftDTO, BindingResult bindingResult, Model model) {
        if (shiftDTO.getDate() != null && shiftService.isShiftThisDay(shiftDTO.getDate()) && action.equals("add"))
            bindingResult.rejectValue("date", "error.date", "На эту дату уже назначена смена.");
        if (bindingResult.hasErrors()) {
            model.addAttribute("shift", shiftDTO);
            model.addAttribute("employees", employeeService.findByName().stream()
                    .map(em -> em[0] + " " + em[1] + " " + em[2] + " " + em[3]).toList());
            return editPage;
        }
        if (action.equals("add"))
            shiftService.add(shiftDTO);
        if (action.equals("edit"))
            shiftService.update(shiftDTO);
        return "redirect:/manage";
    }

    @RequestMapping("/shift/delete")
    public String delete(@RequestParam Long id) {
        shiftService.delete(id);
        return "redirect:/manage";
    }

    @RequestMapping("/shift/edit")
    public String edit(@RequestParam Long id, Model model) {
        action = "edit";
        model.addAttribute("shift", shiftService.mapToDTO(shiftService.findById(id)));
        model.addAttribute("employees", employeeService.findByName().stream()
                .map(em -> em[0] + " " + em[1] + " " + em[2] + " " + em[3]).toList());
        return editPage;
    }

    private void setModelAttributes(Model model, Search search, Date date, List<Employee> em, List<Shift> sh) {
        model.addAttribute("search", search);
        model.addAttribute("date", date);
        model.addAttribute("employees", em);
        model.addAttribute("shifts", sh);
    }
}
