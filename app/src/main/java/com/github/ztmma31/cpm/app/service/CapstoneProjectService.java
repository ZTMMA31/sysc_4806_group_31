package com.github.ztmma31.cpm.app.service;

import com.github.ztmma31.cpm.app.entities.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CapstoneProjectService {

    private final CapstoneProjectRepository projectRepository;
    private final ProfessorRepository professorRepository;

    public CapstoneProjectService(
        CapstoneProjectRepository projectRepository,
        ProfessorRepository professorRepository) {
        this.projectRepository = projectRepository;
        this.professorRepository = professorRepository;
    }

    public CapstoneProject createProject(
        String title, String description, String username) {

        Professor professor = professorRepository
            .findByUsername(username)
            .orElseThrow();

        CapstoneProject project = new CapstoneProject();

        project.setTitle(title);
        project.setDescription(description);
        project.setProfessor(professor);

        return projectRepository.save(project);
    }

    public List<CapstoneProject> getProjectsByProfessor(String username) {

        Professor professor = professorRepository
            .findByUsername(username)
            .orElseThrow();

        return projectRepository.findByProfessor(professor);
    }

    public CapstoneProject getProject(Long id, String username) {

        CapstoneProject project = projectRepository
            .findById(id)
            .orElseThrow();

        if (!project.getProfessor().getUsername().equals(username)) {
            throw new SecurityException("Professor does not own this project");
        }

        return project;
    }

    public CapstoneProject updateProject(
        Long id, String title, String description, String username) {

        CapstoneProject project = getProject(id, username);

        project.setTitle(title);
        project.setDescription(description);

        return projectRepository.save(project);
    }

    public void deleteProject(Long id, String username) {

        CapstoneProject project = getProject(id, username);

        projectRepository.delete(project);
    }


}
