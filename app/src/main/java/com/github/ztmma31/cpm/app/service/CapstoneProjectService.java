package com.github.ztmma31.cpm.app.service;

import com.github.ztmma31.cpm.app.entities.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

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


    /**
     * Creates and saves a new capstone project associated with the
     * professor identified by their username.
     *
     * @param title the title of the project
     * @param description the description of the project
     * @param username the username of the professor creating the project
     * @return the newly created and persisted capstone project
     * @throws NoSuchElementException if the professor does not exist
     */
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

    public CapstoneProject getProject(Long id, String expectedProfessorUsername) {

        CapstoneProject project = projectRepository
            .findById(id)
            .orElseThrow();

        if (!project.getProfessor().getUsername().equals(expectedProfessorUsername)) {
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
