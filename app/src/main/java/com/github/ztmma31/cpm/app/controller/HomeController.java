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

        if (auth == null || !auth.isAuthenticated()) {
            return "redirect:/login";
        }

        String role = auth.getAuthorities().iterator().next().getAuthority().replace("ROLE_", "");
        model.addAttribute("name", auth.getName());
        model.addAttribute("role", role);

        return "home";
    }
}
