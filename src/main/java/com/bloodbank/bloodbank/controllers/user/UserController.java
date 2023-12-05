package com.bloodbank.bloodbank.controllers.user;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @RequestMapping("/index")
    public String home() {
        return "mediplus/index";
    }

    @RequestMapping("/donation")
    public String donation() {
        return "mediplus/donation";
    }

    @RequestMapping("/about")
    public String about() {
        return "mediplus/about";
    }

    @RequestMapping("/faq")
    public String faq() {
        return "mediplus/faq";
    }

    @RequestMapping("/info")
    public String info(@RequestParam(value = "id", required = false) Integer id) {
        if (id == null)
            return "mediplus/info";
        if (id == 1)
            return "mediplus/info-01";
        if (id == 2)
            return "mediplus/info-02";
        return "mediplus/info";
    }

    @RequestMapping("/profile")
    public String profile() {
        return "mediplus/profile";
    }

    @RequestMapping("/contact")
    public String contacts() {
        return "mediplus/contact";
    }

    @RequestMapping("/whole")
    public String whole() {
        return "mediplus/whole";
    }

    @RequestMapping("/blog")
    public String blog() {
        return "mediplus/blog-single";
    }

}
