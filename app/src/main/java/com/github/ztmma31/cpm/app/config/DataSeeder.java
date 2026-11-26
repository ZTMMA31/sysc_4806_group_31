package com.github.ztmma31.cpm.app.config;

import com.github.ztmma31.cpm.app.entities.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

/** Demo accounts for Milestone 1. Remove later. */
@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedUsers(StudentRepository students, ProfessorRepository professors, CapstoneProjectRepository projects, PasswordEncoder encoder) {
        return args -> {
            if (professors.findByUsername("prof_1").isEmpty()) {
                Professor p = new Professor();
                p.setUsername("prof_1");
                p.setPassword(encoder.encode("password"));
                professors.save(p);
                CapstoneProject project = new CapstoneProject();
                project.setTitle("Demo Project");
                project.setDescription("This is a demo project.");
                project.setProfessor(p);
                projects.save(project);
            }
            if (students.findByUsername("stud_1").isEmpty()) {
                Student s = new Student();
                s.setUsername("stud_1");
                s.setPassword(encoder.encode("password"));
                s.setProject(projects.findAll().iterator().next());
                students.save(s);
            }
        };
    }
}
