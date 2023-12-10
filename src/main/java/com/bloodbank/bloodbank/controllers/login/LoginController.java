package com.bloodbank.bloodbank.controllers.login;

import com.bloodbank.bloodbank.model.login.User;
import com.bloodbank.bloodbank.dto.login.UserDTO;
import com.bloodbank.bloodbank.service.login.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@Controller
public class LoginController {

    private UserService userService;
    private org.springframework.security.crypto.password.PasswordEncoder encoder;

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
                    "Такой email уже зарегистрирован, попробуйте другой.");
        }

        if(result.hasErrors()){
            model.addAttribute("user", userDto);
            return "login/register";
        }

        userService.saveUser(userDto);
        return "redirect:/index";
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
}
