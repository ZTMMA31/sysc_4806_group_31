package com.github.ztmma31.cpm.app.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class HomeController {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @GetMapping({"/","/home"})
    public String home(
        Model model,
        Authentication auth
    ) {
        logger.info("HomeController GET on /, /home");

        String role = auth.getAuthorities().iterator().next().getAuthority().replace("ROLE_", "");
        model.addAttribute("name", auth.getName());
        model.addAttribute("role", role);

        logger.info("    Username: {}", auth.getName());
        logger.info("    Role: {}", role);

        return "home";
    }
}
