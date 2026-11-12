package com.github.ztmma31.cpm.app;

import com.github.ztmma31.cpm.app.entities.Professor;
import com.github.ztmma31.cpm.app.entities.ProfessorRepository;
import com.github.ztmma31.cpm.app.entities.Student;
import com.github.ztmma31.cpm.app.entities.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CpmApplication {
    public static void main(String[] args) {
        SpringApplication.run(CpmApplication.class, args);
    }

    // initial app test
    @Bean
    CommandLineRunner clRunner(ProfessorRepository professorRepository, StudentRepository studentRepository) {
        return args -> {
            Professor p1 = new Professor();
            p1.setPassword("prof_1_pass");
            p1.setUsername("prof_1");
            professorRepository.save(p1);

            professorRepository.findAll().forEach(prof -> System.out.println(prof.toString()));

            Student s1 = new Student();
            s1.setPassword("stud_1_pass");
            s1.setUsername("stud_1");
            studentRepository.save(s1);

            studentRepository.findAll().forEach(student -> System.out.println(student.toString()));
        };
    }
}
