package com.github.ztmma31.cpm.app.security;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.github.ztmma31.cpm.app.entities.Professor;
import com.github.ztmma31.cpm.app.entities.ProfessorRepository;
import com.github.ztmma31.cpm.app.entities.Student;
import com.github.ztmma31.cpm.app.entities.StudentRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class CurrentUserServiceTest {

    private final StudentRepository students = mock(StudentRepository.class);
    private final ProfessorRepository professors = mock(ProfessorRepository.class);
    private final CurrentUserService service = new CurrentUserService(students, professors);

    private static Authentication login (String username, String role) {
        return new UsernamePasswordAuthenticationToken(
            username, null, List.of(new SimpleGrantedAuthority(role)));
    }

    @Test
    void studentAuthenticationResolvesToStudent() {
        Student s = new Student();
        s.setUsername("Max Bui");
        when(students.findByUsername("Max Bui")).thenReturn(Optional.of(s));

        assertThat(service.findStudent(login("Max Bui", "ROLE_STUDENT"))).hasValue(s);
        assertThat(service.requireStudent(login("Max Bui", "ROLE_STUDENT"))).isSameInstanceAs(s);
    }

    @Test
    void professorAuthenticationResolvesToProfessor() {
        Professor p = new Professor();
        p.setUsername("Abdullah Kadri");
        when(professors.findByUsername("Abdullah Kadri")).thenReturn(Optional.of(p));

        assertThat(service.findProfessor(login("Abdullah Kadri", "ROLE_PROFESSOR"))).hasValue(p);
        assertThat(service.requireProfessor(login("Abdullah Kadri", "ROLE_PROFESSOR"))).isSameInstanceAs(p);
    }

    @Test
    void professorIsNotResolvedAsStudentEvenWithSameUsername() {
        Authentication prof = login("Babak", "ROLE_PROFESSOR");

        assertThat(service.findProfessor(prof)).isEmpty();
        verifyNoInteractions(students);
    }

    @Test
    void nullAuthenticationResolvesToNothing() {
        assertThat(service.findStudent(null)).isEmpty();
        assertThat(service.findProfessor(null)).isEmpty();
    }

    @Test
    void anonymousAuthenticationResolvesToNothing() {
        Authentication anon = new AnonymousAuthenticationToken(
            "key", "anonymousUser", List.of(new SimpleGrantedAuthority("ROLE_ANONYMOUS")));

        assertThat(service.findStudent(anon)).isEmpty();
        assertThat(service.findProfessor(anon)).isEmpty();
    }

    @Test
    void requireThrowsWhenAccountIsMissingOrWrongRole() {
        when(students.findByUsername("ghost")).thenReturn(Optional.empty());

        assertThrows(AccessDeniedException.class, () -> service.requireStudent(login("ghost", "ROLE_STUDENT")));
        assertThrows(AccessDeniedException.class, () -> service.requireProfessor(login("ghost", "ROLE_PROFESSOR")));
    }
}
