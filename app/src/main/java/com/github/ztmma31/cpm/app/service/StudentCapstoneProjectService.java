package com.github.ztmma31.cpm.app.service;

import com.github.ztmma31.cpm.app.entities.CapstoneProject;
import com.github.ztmma31.cpm.app.entities.CapstoneProjectRepository;
import com.github.ztmma31.cpm.app.entities.Student;
import com.github.ztmma31.cpm.app.entities.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class StudentCapstoneProjectService {
    private final StudentRepository studentRepository;
    private final CapstoneProjectRepository projectRepository;

    public StudentCapstoneProjectService(
        StudentRepository studentRepository,
        CapstoneProjectRepository projectRepository) {
        this.studentRepository = studentRepository;
        this.projectRepository = projectRepository;
    }

    /// Returns the capstone project that the student is registered to
    public CapstoneProject getProject(String name) {
        Student student = studentRepository
            .findByUsername(name)
            .orElseThrow();

        return student.getProject();
    }

    /// Returns a list of all capstone projects
    public List<CapstoneProject> getAllProjects() {
        return new ArrayList<>(projectRepository.findAll());
    }

    /// Returns true if the student was successfully registered to the project,
    /// false if the student is already registered to a project
    ///
    /// @throws NoSuchElementException if the student or project does not exist
    public boolean joinProject(String username, Long projectId) {
        // Find student
        Student student = studentRepository
            .findByUsername(username)
            .orElseThrow();

        if (student.getProject() != null) {
            return false;
        }

        CapstoneProject project = projectRepository
            .findById(projectId)
            .orElseThrow();

        student.setProject(project);
        studentRepository.save(student);
        return true;
    }
}
