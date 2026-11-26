package com.github.ztmma31.cpm.app.controller;

import com.github.ztmma31.cpm.app.service.StudentCapstoneProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.NoSuchElementException;

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

        logger.info("StudentCapstoneProjectController GET on /student/project");
        logger.info("    Student: {}", principal.getName());

        model.addAttribute("project", projectService.getProject(principal.getName()));

        return "student/project/project";
    }

    @GetMapping("/search")
    public String getAvailableProjects(Principal principal, Model model) {
        logger.info("StudentCapstoneProjectController GET on /student/project/search");
        logger.info("    Student: {}", principal.getName());

        model.addAttribute("projects", projectService.getAllProjects());
        model.addAttribute("project", projectService.getProject(principal.getName()));

        return "student/project/search";
    }

    @PostMapping("/search/{projectId}/join")
    public String joinProject(
        @PathVariable Long projectId,
        Principal principal,
        RedirectAttributes redirectAttributes) {

        logger.info("StudentCapstoneProjectController POST join project {}", projectId);

        boolean isNewlyRegistered;
        try {
            isNewlyRegistered = projectService.joinProject(principal.getName(), projectId);
        } catch (NoSuchElementException e) {
            redirectAttributes.addFlashAttribute("message", "The student or project does not exist.");
            return "redirect:/student/project/search";
        }

        if (isNewlyRegistered) {
            redirectAttributes.addFlashAttribute("message", "You have joined the project.");
            return "redirect:/student/project";
        } else {
            redirectAttributes.addFlashAttribute("message",
                "You are already registered in a project." +
                    "If you want to leave your current project, ask the project's professor supervisor."
            );
            return "redirect:/student/project/search";
        }
    }
}
