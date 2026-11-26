package com.github.ztmma31.cpm.app.security;

import com.github.ztmma31.cpm.app.entities.Professor;
import com.github.ztmma31.cpm.app.entities.ProfessorRepository;
import com.github.ztmma31.cpm.app.entities.Student;
import com.github.ztmma31.cpm.app.entities.StudentRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Resolves the {@link Student} or {@link Professor} behind a Spring Security
 * {@link Authentication}
 *
 * <p>The role on the authentication (ROLE_STUDENT / ROLE_PROF, set by
 * {@link AppUserDetailsService}) decides which table is searched. This matters
 * because a student and a professor could share a username, and the role says
 * which of them actually logged in.</p>
 */
@Service
public class CurrentUserService {

    private static final String STUDENT_AUTHORITY = "ROLE_STUDENT";
    private static final String PROFESSOR_AUTHORITY = "ROLE_PROFESSOR";

    private final StudentRepository students;
    private final ProfessorRepository professors;

    public CurrentUserService(StudentRepository students, ProfessorRepository professors) {
        this.students = students;
        this.professors = professors;
    }

    // The logged-in student, or empty if the user is not logged in/not a student
    public Optional<Student> findStudent(Authentication auth) {
        if (!isLoggedInWithAuthority(auth, STUDENT_AUTHORITY)) { return Optional.empty(); }
        return students.findByUsername(auth.getName());
    }

    // The logged-in professor, or empty if the user is not logged in/not a professor
    public Optional<Professor> findProfessor(Authentication auth) {
        if (!isLoggedInWithAuthority(auth, PROFESSOR_AUTHORITY)) { return Optional.empty(); }
        return professors.findByUsername(auth.getName());
    }

    public Student requireStudent(Authentication auth) { return findStudent(auth)
        .orElseThrow(() -> new AccessDeniedException("A student account is required.")); }

    public Professor requireProfessor(Authentication auth) { return findProfessor(auth)
        .orElseThrow(() -> new AccessDeniedException("A professor account is required.")); }

    private boolean isLoggedInWithAuthority(Authentication auth, String authority) {
        if (auth == null || !auth.isAuthenticated() || auth instanceof AnonymousAuthenticationToken) { return false; }

        return auth.getAuthorities().stream().anyMatch(a -> authority.equals(a.getAuthority()));
    }
}
