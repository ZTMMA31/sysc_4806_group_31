
package com.github.ztmma31.cpm.app.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Authentication authentication) {

        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";
        }

        boolean isProfessor = authentication.getAuthorities().stream()
            .anyMatch(a -> a.getAuthority().equals("ROLE_PROFESSOR"));

        if (isProfessor) {
            return "redirect:/professor/dashboard";
        }

        return "redirect:/student/dashboard";
    }
}


