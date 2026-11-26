package com.github.ztmma31.cpm.app.service;

import com.github.ztmma31.cpm.app.entities.CapstoneProject;
import com.github.ztmma31.cpm.app.entities.CapstoneProjectRepository;
import com.github.ztmma31.cpm.app.entities.Professor;
import com.github.ztmma31.cpm.app.entities.ProfessorRepository;
import com.github.ztmma31.cpm.app.entities.Student;
import com.github.ztmma31.cpm.app.entities.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StudentCapstoneProjectServiceTest {
    private StudentRepository studentRepository;
    private StudentCapstoneProjectService service;

    private Student student;
    private CapstoneProject project;

    @BeforeEach
    void setUp() {
        studentRepository = mock(StudentRepository.class);

        service = new StudentCapstoneProjectService(
            studentRepository
        );

        Professor professor = mock(Professor.class);
        when(professor.getUsername()).thenReturn("professor1");

        student = new Student();
        student.setUsername("student1");

        project = new CapstoneProject();
        project.setTitle("Test Project");
        project.setDescription("Test Description");
        project.setProfessor(professor);
    }

    @Test
    void testGetProject()
    {
        student.setProject(project);
        when(studentRepository.findByUsername("student1")).thenReturn(Optional.of(student));
        var result = service.GetProject(student.getUsername());
        assertSame(project, result);
    }

    @Test
    void testGetNullProject()
    {
        when(studentRepository.findByUsername("student1")).thenReturn(Optional.of(student));
        assertNull(service.GetProject(student.getUsername()));
    }

    @Test
    void throwWhenStudentNotFound()
    {
        when(student.getUsername()).thenReturn("student1");
        when(studentRepository.findByUsername("student1")).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> service.GetProject(student.getUsername()));
    }

}
