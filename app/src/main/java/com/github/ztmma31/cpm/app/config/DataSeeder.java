package com.github.ztmma31.cpm.app.config;

import com.github.ztmma31.cpm.app.entities.Student;
import com.github.ztmma31.cpm.app.entities.Professor;
import com.github.ztmma31.cpm.app.entities.StudentRepository;
import com.github.ztmma31.cpm.app.entities.ProfessorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

/** Demo accounts for Milestone 1. Remove later. */
@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedUsers(StudentRepository students, ProfessorRepository professors,
                                PasswordEncoder encoder) {
        return args -> {
            if (students.findByUsername("stud_1").isEmpty()) {
                Student s = new Student();
                s.setUsername("stud_1");
                s.setPassword(encoder.encode("password"));
                students.save(s);
            }
            if (professors.findByUsername("prof_1").isEmpty()) {
                Professor p = new Professor();
                p.setUsername("prof_1");
                p.setPassword(encoder.encode("password"));
                professors.save(p);
            }
        };
    }
}
