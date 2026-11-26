package com.github.ztmma31.cpm.app.service;

import com.github.ztmma31.cpm.app.entities.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CapstoneProjectServiceTest {

    private CapstoneProjectRepository projectRepository;
    private ProfessorRepository professorRepository;
    private StudentRepository studentRepository;
    private CapstoneProjectService service;

    private Professor professor;
    private CapstoneProject project;

    @BeforeEach
    void setUp() {
        projectRepository = mock(CapstoneProjectRepository.class);
        professorRepository = mock(ProfessorRepository.class);

        service = new CapstoneProjectService(
            projectRepository,
            professorRepository,
            studentRepository
        );

        professor = mock(Professor.class);
        when(professor.getUsername()).thenReturn("professor1");

        project = new CapstoneProject();
        project.setTitle("Test Project");
        project.setDescription("Test Description");
        project.setProfessor(professor);
    }

    @Test
    void testCreateProject() {

        when(professorRepository.findByUsername("professor1"))
            .thenReturn(Optional.of(professor));

        when(projectRepository.save(any(CapstoneProject.class)))
            .thenAnswer(invocation -> invocation.getArgument(0));


        CapstoneProject result = service.createProject(
            "New Project", "Description", "professor1"
        );


        assertEquals("New Project", result.getTitle());
        assertEquals("Description", result.getDescription());
        assertEquals(professor, result.getProfessor());

        verify(projectRepository).save(result);
    }

    @Test
    void testCreateProjectProfessorNotFound() {
        when(professorRepository.findByUsername("unknown"))
            .thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () ->
            service.createProject("Title", "Description", "unknown")
        );

        verifyNoInteractions(projectRepository);
    }

    @Test
    void testGetProjectsByProfessor() {
        when(professorRepository.findByUsername("professor1"))
            .thenReturn(Optional.of(professor));

        when(projectRepository.findByProfessor(professor))
            .thenReturn(List.of(project));

        List<CapstoneProject> result =
            service.getProjectsByProfessor("professor1");

        assertEquals(List.of(project), result);

        verify(projectRepository).findByProfessor(professor);
    }

    @Test
    void testGetProjectsByProfessorNotFound() {
        when(professorRepository.findByUsername("unknown"))
            .thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () ->
            service.getProjectsByProfessor("unknown")
        );

        verifyNoInteractions(projectRepository);
    }

    @Test
    void testGetProject() {
        when(projectRepository.findById(1L))
            .thenReturn(Optional.of(project));

        CapstoneProject result =
            service.getProject(1L, "professor1");

        assertSame(project, result);
    }

    @Test
    void testGetProjectUnauthorized() {
        when(projectRepository.findById(1L))
            .thenReturn(Optional.of(project));

        assertThrows(SecurityException.class, () ->
            service.getProject(1L, "professor2")
        );
    }

    @Test
    void testGetProjectNotFound() {
        when(projectRepository.findById(1L))
            .thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () ->
            service.getProject(1L, "professor1")
        );
    }

    @Test
    void testUpdateProject() {
        when(projectRepository.findById(1L))
            .thenReturn(Optional.of(project));

        when(projectRepository.save(project))
            .thenReturn(project);

        CapstoneProject result = service.updateProject(
            1L, "Updated Title", "Updated Description", "professor1"
        );

        assertEquals("Updated Title", result.getTitle());
        assertEquals("Updated Description", result.getDescription());
        assertEquals(professor, result.getProfessor());

        verify(projectRepository).save(project);
    }

    @Test
    void testUpdateProjectUnauthorized() {
        when(projectRepository.findById(1L))
            .thenReturn(Optional.of(project));

        assertThrows(SecurityException.class, () ->
            service.updateProject(
                1L, "Updated Title", "Updated Description", "professor2"
            )
        );

        verify(projectRepository, never()).save(any());

        assertEquals("Test Project", project.getTitle());
        assertEquals("Test Description", project.getDescription());
    }

    @Test
    void testDeleteProject() {
        when(projectRepository.findById(1L))
            .thenReturn(Optional.of(project));

        service.deleteProject(1L, "professor1");

        verify(projectRepository).delete(project);
    }


}
