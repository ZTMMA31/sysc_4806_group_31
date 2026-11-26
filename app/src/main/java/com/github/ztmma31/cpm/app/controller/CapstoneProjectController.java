package com.github.ztmma31.cpm.app.controller;

import com.github.ztmma31.cpm.app.service.CapstoneProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.security.Principal;

@Controller
@RequestMapping("/professor/projects")
public class CapstoneProjectController {
    private final CapstoneProjectService projectService;
    private static final Logger logger = LoggerFactory.getLogger(CapstoneProjectController.class);

    public CapstoneProjectController(
        CapstoneProjectService projectService) {
        this.projectService = projectService;
    }

    // List all projects belonging to the professor
    @GetMapping
    public String getProjects(Principal principal, Model model) {
        logger.info("CapstoneProjectController GET on /professor/projects");

        model.addAttribute("projects",
            projectService.getProjectsByProfessor(principal.getName()));

        return "professor/projects/index";
    }

    // Display the project creation form
    @GetMapping("/new")
    public String createProjectForm() {
        logger.info("CapstoneProjectController GET on /professor/projects/new");

        return "professor/projects/create";
    }

    // Create a new project
    @PostMapping
    public String createProject(
        @RequestParam String title,
        @RequestParam String description,
        Principal principal) {

        logger.info("CapstoneProjectController POST on /professor/projects");
        logger.info("    Title: {}", title);
        logger.info("    Description: {}", description);
        logger.info("    Professor: {}", principal.getName());

        projectService.createProject(
            title, description, principal.getName());
        
        logger.info("CapstoneProjectController STATUS new project created for professor {}", principal.getName());
        logger.info("CapstoneProjectController REDIRECT to /professor/projects");

        return "redirect:/professor/projects";
    }

    // Display an individual project
    @GetMapping("/{id}")
    public String getProject(
        @PathVariable Long id,
        Principal principal,
        Model model) {

        logger.info("CapstoneProjectController GET on /professor/projects/{}", id);
        logger.info("    Project ID: {}", id);
        logger.info("    Professor: {}", principal.getName());

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

        logger.info("CapstoneProjectController GET on /professor/projects/{}/edit", id);

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
        
        logger.info("CapstoneProjectController POST on /professor/projects/{}/edit", id);
        logger.info("    Project ID: {}", id);
        logger.info("    Title: {}", title);
        logger.info("    Description: {}", description);
        logger.info("    Professor: {}", principal.getName());

        projectService.updateProject(
            id, title, description, principal.getName());

        logger.info("CapstoneProjectController STATUS project {} updated for professor {}", id, principal.getName());
        logger.info("CapstoneProjectController REDIRECT to /professor/projects");

        return "redirect:/professor/projects";
    }

    // Delete an existing project
    @PostMapping("/{id}/delete")
    public String deleteProject(
        @PathVariable Long id,
        Principal principal) {

        logger.info("CapstoneProjectController POST on /professor/projects/{}/delete", id);
        logger.info("    Project ID: {}", id);
        logger.info("    Professor: {}", principal.getName());

        projectService.deleteProject(id, principal.getName());

        logger.info("CapstoneProjectController STATUS project {} deleted for professor {}", id, principal.getName());
        logger.info("CapstoneProjectController REDIRECT to /professor/projects");

        return "redirect:/professor/projects";
    }

    @PostMapping ("/{projectId}/students/{studentId}/remove")
    public String removeStudentFromProject(@PathVariable Long projectId, @PathVariable int studentId, Principal principal) {
        logger.info("CapstoneProjectController POST on /professor/projects/{}/students/{}/remove", projectId, studentId);
        logger.info("    Project ID: {}", projectId);
        logger.info("    Student ID: {}", studentId);
        logger.info("    Professor: {}", principal.getName());

        projectService.removeStudentFromProject(projectId, studentId, principal.getName());

        logger.info("CapstoneProjectController STATUS student {} removed from project {} for professor {}", studentId, projectId, principal.getName());
        logger.info("CapstoneProjectController REDIRECT to /professor/projects/{}", projectId);

        return "redirect:/professor/projects/" + projectId;
    }
}
