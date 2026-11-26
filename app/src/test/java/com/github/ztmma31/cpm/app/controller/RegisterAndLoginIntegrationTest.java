package com.github.ztmma31.cpm.app.controller;

import com.github.ztmma31.cpm.app.entities.Professor;
import com.github.ztmma31.cpm.app.entities.ProfessorRepository;
import com.github.ztmma31.cpm.app.entities.Student;
import com.github.ztmma31.cpm.app.entities.StudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static com.google.common.truth.Truth.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/// Integration test for the following controllers
/// - RegistrationController
/// - LoginController
/// - HomeController
@SpringBootTest
@AutoConfigureMockMvc
public class RegisterAndLoginIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void registerAndLoginAsStudentThenSeeHomePage() throws Exception {
        // Register a new test student
        mockMvc.perform(post("/register")
                .with(csrf())
                .param("username", "home-integration-student")
                .param("password", "password1")
                .param("professor", "false"))
            .andExpect(status().is3xxRedirection());

        // Check that it was saved in the database
        Student student = studentRepository.findByUsername("home-integration-student").orElseThrow();
        assertThat(student.getUsername()).isEqualTo("home-integration-student");
        assertThat(passwordEncoder.matches("password1", student.getPassword())).isTrue();

        // Login as this new student
        MvcResult loginResult = mockMvc.perform(formLogin()
                .user("home-integration-student")
                .password("password1"))
            .andExpect(status().is3xxRedirection())
            .andReturn();

        MockHttpSession session = (MockHttpSession) loginResult.getRequest().getSession(false);
        assertThat(session).isNotNull();

        // Access the home page as the logged-in student
        mockMvc.perform(get("/home").session(session))
            .andExpect(status().isOk())
            .andExpect(view().name("home"))
            .andExpect(model().attribute("name", "home-integration-student"))
            .andExpect(model().attribute("role", "STUDENT"));

        assertThat(studentRepository.findByUsername("home-integration-student")).isPresent();
    }

    @Test
    void registerAndLoginAsProfessorThenSeeHomePage() throws Exception {
        // Register a new test professor
        mockMvc.perform(post("/register")
                .with(csrf())
                .param("username", "home-integration-professor")
                .param("password", "password2")
                .param("professor", "true"))
            .andExpect(status().is3xxRedirection());

        // Check that it was saved in the database
        Professor professor = professorRepository.findByUsername("home-integration-professor").orElseThrow();
        assertThat(professor.getUsername()).isEqualTo("home-integration-professor");
        assertThat(passwordEncoder.matches("password2", professor.getPassword())).isTrue();

        // Login as this new professor
        MvcResult loginResult = mockMvc.perform(formLogin()
                .user("home-integration-professor")
                .password("password2"))
            .andExpect(status().is3xxRedirection())
            .andReturn();

        MockHttpSession session = (MockHttpSession) loginResult.getRequest().getSession(false);
        assertThat(session).isNotNull();

        // Access the home page as the logged-in professor
        mockMvc.perform(get("/home").session(session))
            .andExpect(status().isOk())
            .andExpect(view().name("home"))
            .andExpect(model().attribute("name", "home-integration-professor"))
            .andExpect(model().attribute("role", "PROF"));

        assertThat(professorRepository.findByUsername("home-integration-professor")).isPresent();
    }
}
