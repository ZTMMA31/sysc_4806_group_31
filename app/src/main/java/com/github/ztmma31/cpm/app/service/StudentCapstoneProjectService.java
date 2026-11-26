package com.github.ztmma31.cpm.app.service;

import com.github.ztmma31.cpm.app.entities.CapstoneProject;
import com.github.ztmma31.cpm.app.entities.CapstoneProjectRepository;
import com.github.ztmma31.cpm.app.entities.ProfessorRepository;
import com.github.ztmma31.cpm.app.entities.Student;
import com.github.ztmma31.cpm.app.entities.StudentRepository;

public class StudentCapstoneProjectService {
    private final StudentRepository studentRepository;

    public StudentCapstoneProjectService(
        StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public CapstoneProject GetProject(String name)
    {
        Student student = studentRepository
            .findByUsername(name)
            .orElseThrow();

        return student.getProject();
    }
}
