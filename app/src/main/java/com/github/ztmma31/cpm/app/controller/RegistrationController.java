package com.github.ztmma31.cpm.app.controller;

import com.github.ztmma31.cpm.app.dto.RegistrationForm;
import com.github.ztmma31.cpm.app.entities.Professor;
import com.github.ztmma31.cpm.app.entities.ProfessorRepository;
import com.github.ztmma31.cpm.app.entities.Student;
import com.github.ztmma31.cpm.app.entities.StudentRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegistrationController {

    private final StudentRepository studentRepository;
    private final ProfessorRepository professorRepository;
    private final PasswordEncoder passwordEncoder;

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

        model.addAttribute(
            "registrationForm",
            new RegistrationForm()
        );

        return "registration";
    }

    @PostMapping("/register")
    public String processRegistration(
        @ModelAttribute("registrationForm") RegistrationForm form) {

        String username = form.getUsername();
        String encodedPassword = passwordEncoder.encode(form.getPassword());

        // FIXME: reject if the username is already registered
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

        return "redirect:/login";
    }

}
