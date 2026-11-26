package com.github.ztmma31.cpm.app.controller;

import com.github.ztmma31.cpm.app.entities.ProfessorRepository;
import com.github.ztmma31.cpm.app.entities.StudentRepository;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;

import static com.google.common.truth.Truth.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class RegistrationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    static Stream<Arguments> blankCredentials() {
        return Stream.of(false, true).flatMap(isProfessor ->
            Stream.of("", "   ", null).flatMap(invalidString -> Stream.of(
                Arguments.of(isProfessor, invalidString, "valid-password", "username"),
                Arguments.of(isProfessor, "valid-username", invalidString, "password"),
                Arguments.of(isProfessor, invalidString, invalidString, "password")
            ))
        );
    }

    @ParameterizedTest
    @MethodSource("blankCredentials")
    void cannotRegisterWithBlankCredentials(
        boolean isProfessor,
        String username,
        String password,
        String invalidFieldName
    ) throws Exception {

        long studentCount = studentRepository.count();
        long professorCount = professorRepository.count();

        // Prepare invalid register request
        MockHttpServletRequestBuilder request = post("/register")
            .with(csrf())
            .param("professor", Boolean.toString(isProfessor));
        if (username != null) {
            request.param("username", username);
        }
        if (password != null) {
            request.param("password", password);
        }

        // Perform invalid register request and verify that it is rejected
        mockMvc.perform(request)
            .andExpect(status().isBadRequest())
            .andExpect(view().name("registration"))
            .andExpect(model().attributeHasFieldErrors("registrationForm", invalidFieldName))
            .andExpect(content().string(containsString("must not be blank")));

        assertThat(studentRepository.count()).isEqualTo(studentCount);
        assertThat(professorRepository.count()).isEqualTo(professorCount);
    }

    @ParameterizedTest(name = "existing user is professor={0}, new user is professor={1}")
    @CsvSource({
        "false, false", // student   already exists, new user is student
        "false, true",  // student   already exists, new user is professor
        "true, false",  // professor already exists, new user is student
        "true, true"    // professor already exists, new user is professor
    })
    void cannotRegisterAnExistingUsername(boolean existingUserIsProfessor, boolean newProfessorIsUser) throws Exception {
        String username = "duplicate-registration-user";

        // Register the first user
        mockMvc.perform(post("/register")
                .with(csrf())
                .param("username", username)
                .param("password", "original-password")
                .param("professor", Boolean.toString(existingUserIsProfessor)))
            .andExpect(status().is3xxRedirection());

        // Store the result for later comparison
        long studentCount = studentRepository.count();
        long professorCount = professorRepository.count();

        String originalPassword = existingUserIsProfessor
            ? professorRepository.findByUsername(username).orElseThrow().getPassword()
            : studentRepository.findByUsername(username).orElseThrow().getPassword();

        // Try to register a duplicate user
        mockMvc.perform(post("/register")
                .with(csrf())
                .param("username", username)
                .param("password", "replacement-password")
                .param("professor", Boolean.toString(newProfessorIsUser)))
            .andExpect(status().isConflict())
            .andExpect(view().name("registration"))
            .andExpect(content().string(containsString("Username is already registered.")));

        // Verify that the counts have not changed
        assertThat(studentRepository.count()).isEqualTo(studentCount);
        assertThat(professorRepository.count()).isEqualTo(professorCount);

        // Verify that the password has not changed
        String storedPassword = existingUserIsProfessor
            ? professorRepository.findByUsername(username).orElseThrow().getPassword()
            : studentRepository.findByUsername(username).orElseThrow().getPassword();
        assertThat(storedPassword).isEqualTo(originalPassword);
    }
}
