package com.github.ztmma31.cpm.app.controller;

import com.github.ztmma31.cpm.app.dto.RegistrationForm;
import com.github.ztmma31.cpm.app.entities.Professor;
import com.github.ztmma31.cpm.app.entities.ProfessorRepository;
import com.github.ztmma31.cpm.app.entities.Student;
import com.github.ztmma31.cpm.app.entities.StudentRepository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class RegistrationController {
    private final StudentRepository studentRepository;
    private final ProfessorRepository professorRepository;
    private final PasswordEncoder passwordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(RegistrationController.class);

    public RegistrationController(
        StudentRepository studentRepository,
        ProfessorRepository professorRepository,
        PasswordEncoder passwordEncoder
    ) {
        this.studentRepository = studentRepository;
        this.professorRepository = professorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/register")
    public String showRegistration(Model model) {
        logger.info("RegistrationController GET on /register");

        model.addAttribute(
            "registrationForm",
            new RegistrationForm()
        );

        return "registration";
    }

    @PostMapping("/register")
    public String processRegistration(
        @Valid @ModelAttribute("registrationForm") RegistrationForm form,
        BindingResult bindingResult,
        HttpServletResponse response) {
        String username = form.getUsername();

        logger.info("RegistrationController POST on /register");
        logger.info("    Username: {}", username);
        logger.info("    Role: {}", form.isProfessor() ? "PROFESSOR" : "STUDENT");

        if (bindingResult.hasErrors()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return "registration";
        }

        if (anyUserExistsWithUsername(username)) {
            form.setPassword("");
            bindingResult.rejectValue("username", "duplicateUsername", "Username is already registered.");
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            return "registration";
        }

        String encodedPassword = passwordEncoder.encode(form.getPassword());
        if (form.isProfessor()) {
            Professor professor = new Professor();
            professor.setUsername(username);
            professor.setPassword(encodedPassword);

            professorRepository.save(professor);
        } else {
            Student student = new Student();
            student.setUsername(username);
            student.setPassword(encodedPassword);

            studentRepository.save(student);
        }

        logger.info("RegistrationController STATUS saved new user to database");
        logger.info("RegistrationController REDIRECT to /login");

        return "redirect:/login";
    }

    private boolean anyUserExistsWithUsername(String username) {
        return studentRepository.findByUsername(username).isPresent() ||
               professorRepository.findByUsername(username).isPresent();
    }

}
