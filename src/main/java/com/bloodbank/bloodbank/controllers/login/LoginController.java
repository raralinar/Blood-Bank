package com.bloodbank.bloodbank.controllers;

import com.bloodbank.bloodbank.model.login.User;
import com.bloodbank.bloodbank.repository.login.dto.UserDTO;
import com.bloodbank.bloodbank.service.login.PasswordEncoder;
import com.bloodbank.bloodbank.service.login.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class LoginController {

    private UserService userService;
    private PasswordEncoder encoder;

    public LoginController(UserService userService, PasswordEncoder encoder) {
        this.userService = userService;
        this.encoder = encoder;
    }

    @GetMapping("/")
    public String home(){
        return "login/index";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        // create model object to store form data
        UserDTO user = new UserDTO();
        model.addAttribute("user", user);
        return "login/register";
    }

    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDTO userDto,
                               BindingResult result,
                               Model model){
        User existingUser = userService.findUserByEmail(userDto.getEmail());

        if(existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()){
            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
        }

        if(result.hasErrors()){
            model.addAttribute("user", userDto);
            return "login/register";
        }

        userService.saveUser(userDto);
        return "home/index";
    }

    @GetMapping("/users")
    public String users(Model model){
        List<UserDTO> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "login/users";
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    static class LoginUser {
        String email;
        String password;
    }
    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new LoginUser());
        return "login/login";
    }
    @PostMapping("/login/check")
    public String checkLogin(@Valid @ModelAttribute("user") LoginUser user){
        User regUser = userService.findUserByEmail(user.getEmail());
        if (regUser != null && regUser.getEmail() != null) {
            if (regUser.getPassword().equals(encoder.encode(user.getPassword()))) {
                Long id = regUser.getRole().getId();
                return id == 2L ? "user/index" : id == 1L ? "redirect:/donors" : "medic/index";
            }
        }
        return "redirect:/login?error";
    }
}
