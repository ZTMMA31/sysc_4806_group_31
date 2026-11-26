package com.github.ztmma31.cpm.app.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping({"/","/home"})
    public String home(
        Model model,
        Authentication auth
    ) {
        System.out.println("HomeController GET on /, /home");

        String role = auth.getAuthorities().iterator().next().getAuthority().replace("ROLE_", "");
        model.addAttribute("name", auth.getName());
        model.addAttribute("role", role);

        System.out.println("    Username: " + auth.getName());
        System.out.println("    Role: " + role);

        return "home";
    }
}
