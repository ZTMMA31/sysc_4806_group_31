package com.github.ztmma31.cpm.app.controller;

import com.github.ztmma31.cpm.app.service.StudentCapstoneProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/student/project")
public class StudentCapstoneProjectController {
    private final StudentCapstoneProjectService projectService;
    private static final Logger logger = LoggerFactory.getLogger(StudentCapstoneProjectController.class);

    public StudentCapstoneProjectController(
        StudentCapstoneProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public String getProject(
        Principal principal,
        Model model) {

        logger.info("StudentCapstoneProjectController GET on student/project");
        logger.info("    Student: {}", principal.getName());

        model.addAttribute("project", projectService.GetProject(principal.getName()));

        return "student/details";
    }


}
