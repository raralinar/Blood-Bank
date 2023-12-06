package com.bloodbank.bloodbank.controllers.bank;

import com.bloodbank.bloodbank.dto.EmployeeDTO;
import com.bloodbank.bloodbank.dto.ShiftDTO;
import com.bloodbank.bloodbank.dto.validation.employee.EmployeeDatesValidator;
import com.bloodbank.bloodbank.model.bank.Employee;
import com.bloodbank.bloodbank.model.bank.Shift;
import com.bloodbank.bloodbank.service.bank.EmployeeService;
import com.bloodbank.bloodbank.service.bank.ShiftService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ManagementController {
    private final EmployeeService employeeService;
    private final ShiftService shiftService;
    private String action = "add";

    @Autowired
    public ManagementController(EmployeeService employeeService, ShiftService shiftService) {
        this.employeeService = employeeService;
        this.shiftService = shiftService;
    }

    @GetMapping("/manage")
    public String manage(Model model) {
        setModelAttributes(model, employeeService.getAll(), shiftService.getAll());
        return "admin/edittest";
    }

    @PostMapping("/manage/em/search")
    public String emSearch(@Valid @ModelAttribute(name = "searchEm") Search search, Model model) {
        List<String> employees = employeeService.findByName().stream()
                .map(em -> em[1] + " " + em[2] + " " + em[3]).toList()
                .stream().filter(em -> em.toUpperCase().contains(search.getString().toUpperCase())).toList();
        employees = employees.stream()
                .distinct()
                .collect(Collectors.toList());
        List<Employee> employeeList = new ArrayList<>();
        for (String s : employees) {
            String[] nameParts = s.split(" ");
            employeeList.addAll(employeeService.findByName(nameParts[0], nameParts[1], nameParts[2]));
        }
        setModelAttributes(model, search, new Date(), employeeList, shiftService.getAll());
        return "admin/management";
    }

    @GetMapping("/manage/em/add")
    public String emAdd(Model model) {
        action = "add";
        model.addAttribute("employee", new EmployeeDTO());
        return "admin/editEM";
    }

    @RequestMapping(value = "/manage/em/add", method = RequestMethod.POST)
    public String emAdd(@Valid @ModelAttribute(name = "employee") EmployeeDTO employee, BindingResult result, Model model) {
        if (employee.getDob() != null && employee.getEmployed() != null) {
            employee.setDifference(EmployeeDatesValidator.getDiffYears(employee.getDob(), employee.getEmployed()));
            if (!employee.getDifference()) {
                result.rejectValue("difference", "error.difference", "Разница между датами должна быть не менее 16 лет.");
            }
        }
        if (!employee.getPhone().isBlank() && action.equals("edit")) {
            List<FieldError> errorsToKeep = result.getFieldErrors().stream()
                    .filter(fer -> !fer.getField().equals("phone")).toList();

            result = new BeanPropertyBindingResult(employee, "employee");

            for (FieldError fieldError : errorsToKeep) {
                result.addError(fieldError);
            }
        }
        if (result.hasErrors()) {
            return "admin/editEM";
        }
        if (action.equals("add")) {
            employeeService.add(employee);
        }
        if (action.equals("edit")) {
            employeeService.update(employee);
        }
        return "redirect:/manage";
    }

    @RequestMapping(value = "/manage/em/edit", method = RequestMethod.GET)
    public String emEdit(@RequestParam Long id, Model model) {
        action = "edit";
        model.addAttribute("employee", employeeService.maptoDTO(employeeService.findById(id)));
        return "admin/editEM";
    }

    @RequestMapping(value = "/manage/em/delete", method = RequestMethod.GET)
    public String emDelete(@RequestParam Long id, Model model) {
        if (shiftService.isEmployeeEngaged(id))
            return "redirect:/manage?error";
        employeeService.delete(id);
        return "redirect:/manage";
    }


    @RequestMapping("/manage/sh/search")
    public String shSearch(@Valid @ModelAttribute Date date, Model model) {
        List<Shift> shifts;
        if (date.from == null && date.to == null) {
            shifts = shiftService.getAll();
            setModelAttributes(model, new Search(), date, employeeService.getAll(), shifts);
            return "admin/management";
        }
        date.setFrom(date.from == null ? LocalDate.now() : date.from);
        date.setTo(date.to == null ? LocalDate.now() : date.to);
        shifts = shiftService.findAllShiftsWithDateRange(date.from, date.to);
        setModelAttributes(model, new Search(), date, employeeService.getAll(), shifts);
        return "admin/management";
    }

    @GetMapping("/manage/sh/add")
    public String shAdd(Model model) {
        action = "add";
        model.addAttribute("shift", new ShiftDTO());
        model.addAttribute("employees", employeeService.findByName().stream()
                .map(em -> em[0] + " " + em[1] + " " + em[2] + " " + em[3]).toList());
        model.addAttribute("searchEm", new Search());
        return "admin/editSH";
    }

    @PostMapping("/manage/sh/add")
    public String shAdd(@Valid @ModelAttribute("shift") ShiftDTO shiftDTO, BindingResult bindingResult, Model model) {
        if (shiftDTO.getDate() != null && shiftService.isShiftThisDay(shiftDTO.getDate()) && action.equals("add"))
            bindingResult.rejectValue("date", "error.date", "На эту дату уже назначена смена.");
        if (bindingResult.hasErrors()) {
            model.addAttribute("shift", shiftDTO);
            model.addAttribute("employees", employeeService.findByName().stream()
                    .map(em -> em[0] + " " + em[1] + " " + em[2] + " " + em[3]).toList());
            return "admin/editSH";
        }
        if (action.equals("add"))
            shiftService.add(shiftDTO);
        if (action.equals("edit"))
            shiftService.update(shiftDTO);
        return "redirect:/manage";
    }

    @RequestMapping("/manage/sh/delete")
    public String shDelete(@RequestParam Long id, Model model) {
        shiftService.delete(id);
        return "redirect:/manage";
    }

    @RequestMapping("/manage/sh/edit")
    public String shEdit(@RequestParam Long id, Model model) {
        action = "edit";
        model.addAttribute("shift", shiftService.mapToDTO(shiftService.findById(id)));
        model.addAttribute("employees", employeeService.findByName().stream()
                .map(em -> em[0] + " " + em[1] + " " + em[2] + " " + em[3]).toList());
        return "admin/editSH";
    }


    private void setModelAttributes(Model model, Search search, Date date, List<Employee> em, List<Shift> sh) {
        model.addAttribute("searchEm", search);
        model.addAttribute("date", date);
        model.addAttribute("employees", em);
        model.addAttribute("shifts", sh);
    }

    private void setModelAttributes(Model model, List<Employee> em, List<Shift> sh) {
        model.addAttribute("currentShift", List.of(shiftService.findByDate(LocalDate.now())));
        model.addAttribute("searchEm", new Search());
        model.addAttribute("date", new Date());
        model.addAttribute("employees", em);
        model.addAttribute("shifts", sh);
    }

//    private void setModelForShiftEdit(Model model, Shift shift) {
//        model.addAttribute("employees", employeeService.findByName().stream()
//                .map(em -> em[0] + " " + em[1] + " " + em[2] + " " + em[3]).toList());
//        model.addAttribute("shift", shift);
//    }

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
