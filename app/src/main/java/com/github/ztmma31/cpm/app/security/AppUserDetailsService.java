package com.github.ztmma31.cpm.app.security;

import com.github.ztmma31.cpm.app.entities.Student;
import com.github.ztmma31.cpm.app.entities.Professor;
import com.github.ztmma31.cpm.app.entities.StudentRepository;
import com.github.ztmma31.cpm.app.entities.ProfessorRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/** Loads a login account from the student or professor table. */
@Service
public class AppUserDetailsService implements UserDetailsService {

    private final StudentRepository students;
    private final ProfessorRepository professors;

    public AppUserDetailsService(StudentRepository students, ProfessorRepository professors) {
        this.students = students;
        this.professors = professors;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Student> student = students.findByUsername(username);
        if (student.isPresent()) {
            return build(student.get().getUsername(), student.get().getPassword(), "STUDENT");
        }
        Optional<Professor> prof = professors.findByUsername(username);
        if (prof.isPresent()) {
            return build(prof.get().getUsername(), prof.get().getPassword(), "PROF");
        }
        throw new UsernameNotFoundException("Invalid username or password");
    }

    private UserDetails build(String username, String password, String role) {
        return User.withUsername(username).password(password).roles(role).build();
    }
}
