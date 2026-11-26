package com.github.ztmma31.cpm.app.controller;

import com.github.ztmma31.cpm.app.service.CapstoneProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/professor/projects")
public class CapstoneProjectController {

    private final CapstoneProjectService projectService;

    public CapstoneProjectController(
        CapstoneProjectService projectService) {
        this.projectService = projectService;
    }

    // List all projects belonging to the professor
    @GetMapping
    public String getProjects(Principal principal, Model model) {
        model.addAttribute("projects",
            projectService.getProjectsByProfessor(principal.getName()));

        return "professor/projects/index";
    }

    // Display the project creation form
    @GetMapping("/new")
    public String createProjectForm() {
        return "professor/projects/create";
    }

    // Create a new project
    @PostMapping
    public String createProject(
        @RequestParam String title,
        @RequestParam String description,
        Principal principal) {

        projectService.createProject(
            title, description, principal.getName());

        return "redirect:/professor/projects";
    }

    // Display an individual project
    @GetMapping("/{id}")
    public String getProject(
        @PathVariable Long id,
        Principal principal,
        Model model) {

        model.addAttribute("project",
            projectService.getProject(id, principal.getName()));

        return "professor/projects/details";
    }

    // Display the project editing form
    @GetMapping("/{id}/edit")
    public String editProjectForm(
        @PathVariable Long id,
        Principal principal,
        Model model) {

        model.addAttribute("project",
            projectService.getProject(id, principal.getName()));

        return "professor/projects/edit";
    }

    // Update an existing project
    @PostMapping("/{id}/edit")
    public String updateProject(
        @PathVariable Long id,
        @RequestParam String title,
        @RequestParam String description,
        Principal principal) {

        projectService.updateProject(
            id, title, description, principal.getName());

        return "redirect:/professor/projects";
    }

    // Delete an existing project
    @PostMapping("/{id}/delete")
    public String deleteProject(
        @PathVariable Long id,
        Principal principal) {

        projectService.deleteProject(id, principal.getName());

        return "redirect:/professor/projects";
    }
}
