package com.github.ztmma31.cpm.app.controller;

import com.github.ztmma31.cpm.app.dto.RegistrationForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegistrationController {

    @GetMapping("/register")
    public String showRegistration(Model model) {

        model.addAttribute(
            "registrationForm",
            new RegistrationForm()
        );

        return "registration";
    }

    @PostMapping("/register")
    public String processRegistration(
        @ModelAttribute("registrationForm") RegistrationForm form) {

        // TODO: Pass the form to a service to create the user.
        // Do not store the plaintext password.

        return "redirect:/login";
    }

}
