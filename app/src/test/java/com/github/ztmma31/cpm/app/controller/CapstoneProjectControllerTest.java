package com.github.ztmma31.cpm.app.controller;

import com.github.ztmma31.cpm.app.entities.CapstoneProject;
import com.github.ztmma31.cpm.app.entities.Professor;
import com.github.ztmma31.cpm.app.entities.ProfessorRepository;
import com.github.ztmma31.cpm.app.service.CapstoneProjectService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
class CapstoneProjectControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ProfessorRepository professorRepository;

    @Autowired
    CapstoneProjectService capstoneProjectService;

    @WithMockUser(username = "test_prof", roles = {"PROF"})
    @Test
    void createsAndListsDatabaseProject() throws Exception {

        professorRepository.save(Professor.of("test_prof", "1234"));

        // Create a new project as the test prof
        mockMvc.perform(post("/professor/projects")
                .with(csrf())
                .param("title", "Integration Project")
                .param("description", "Created through the controller"))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/professor/projects"));

        // Ensure that the project was created and is listed for the test prof

        List<CapstoneProject> projects = capstoneProjectService.getProjectsByProfessor("test_prof");
        assertThat(projects).hasSize(1);
        assertThat(projects.getFirst().getTitle()).isEqualTo("Integration Project");

        mockMvc.perform(get("/professor/projects"))
            .andExpect(status().isOk())
            .andExpect(view().name("professor/projects/index"));
    }
}
