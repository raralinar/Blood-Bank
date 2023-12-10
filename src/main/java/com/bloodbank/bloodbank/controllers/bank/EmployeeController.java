package com.bloodbank.bloodbank.controllers.bank;

import com.bloodbank.bloodbank.controllers.bank.ManagementController.Date;
import com.bloodbank.bloodbank.controllers.bank.ManagementController.Search;
import com.bloodbank.bloodbank.dto.EmployeeDTO;
import com.bloodbank.bloodbank.dto.login.UserDTO;
import com.bloodbank.bloodbank.dto.validation.employee.EmployeeDatesValidator;
import com.bloodbank.bloodbank.model.bank.Employee;
import com.bloodbank.bloodbank.model.bank.Shift;
import com.bloodbank.bloodbank.service.bank.EmployeeService;
import com.bloodbank.bloodbank.service.bank.ShiftService;
import com.bloodbank.bloodbank.service.login.EmailGenerator;
import com.bloodbank.bloodbank.service.login.PasswordGenerator;
import com.bloodbank.bloodbank.service.login.UserService;
import jakarta.validation.Valid;
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
public class EmployeeController {
    private final EmployeeService employeeService;
    private final ShiftService shiftService;
    private final UserService userService;

    private String action = "add", mainPage = "admin/manage/management", editPage = "admin/manage/editEM";

    @Autowired
    public EmployeeController(EmployeeService employeeService, ShiftService shiftService, UserService userService) {
        this.employeeService = employeeService;
        this.shiftService = shiftService;
        this.userService = userService;
    }

    @PostMapping("/employee/search")
    public String search(@Valid @ModelAttribute(name = "search") Search search, Model model) {
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
        Shift current = shiftService.findByDate(LocalDate.now());
        model.addAttribute("currentShift", current == null ? new ArrayList<>() : List.of(current));
        setModelAttributes(model, search, new Date(), employeeList, shiftService.getAll());
        return mainPage;
    }

    @GetMapping("/employee/add")
    public String add(Model model) {
        action = "add";
        model.addAttribute("condition", false);
        model.addAttribute("employee", new EmployeeDTO());
        return editPage;
    }

    @RequestMapping(value = "/employee/add", method = RequestMethod.POST)
    public String add(@Valid @ModelAttribute(name = "employee") EmployeeDTO employee, BindingResult result) {
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
            return editPage;
        }
        if (action.equals("add")) {
            String email = EmailGenerator.generateRandomEmail();
            while (userService.findUserByEmail(email) != null) {
                email = EmailGenerator.generateRandomEmail();
            }
            String pass = PasswordGenerator.generatePassword();
            employee.setEmail(email);
            employee.setPassword(pass);
            employeeService.add(employee);
            UserDTO userDTO = new UserDTO();
            userDTO.setName(employee.getName());
            userDTO.setSurname(employee.getSurname());
            userDTO.setEmail(email);
            userDTO.setPassword(pass);
            userDTO.setEmployee_id(employeeService.findByEmail(email).getId());
            userService.addEmployee(userDTO, 3L);
        }
        if (action.equals("edit")) {
            employeeService.update(employee);
        }
        return "redirect:/manage";
    }

    @RequestMapping(value = "/employee/edit", method = RequestMethod.GET)
    public String edit(@RequestParam Long id, Model model) {
        action = "edit";
        model.addAttribute("condition", true);
        model.addAttribute("employee", employeeService.maptoDTO(employeeService.findById(id)));
        return editPage;
    }

    @RequestMapping(value = "/employee/delete", method = RequestMethod.GET)
    public String delete(@RequestParam Long id) {
        if (shiftService.isEmployeeEngaged(id))
            return "redirect:/manage?error";
        employeeService.delete(id);
        return "redirect:/manage";
    }

    private void setModelAttributes(Model model, Search search, Date date, List<Employee> em, List<Shift> sh) {
        model.addAttribute("searchEm", search);
        model.addAttribute("date", date);
        model.addAttribute("employees", em);
        model.addAttribute("shifts", sh);
    }
}
