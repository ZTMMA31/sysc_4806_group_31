package com.github.ztmma31.cpm.app;

import com.github.ztmma31.cpm.app.security.AppUserDetailsService;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.github.ztmma31.cpm.app.entities.Student;
import com.github.ztmma31.cpm.app.entities.Professor;
import com.github.ztmma31.cpm.app.entities.StudentRepository;
import com.github.ztmma31.cpm.app.entities.ProfessorRepository;

import java.util.Optional;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AppUserDetailsServiceTest {

    private final StudentRepository students = mock(StudentRepository.class);
    private final ProfessorRepository professors = mock(ProfessorRepository.class);
    private final AppUserDetailsService service = new AppUserDetailsService(students, professors);

    @Test
    void studentLoadsWithStudentRole() {
        Student s = new Student();
        s.setUsername("stud_1");
        s.setPassword("password");
        when(students.findByUsername("stud_1")).thenReturn(Optional.of(s));

        UserDetails d = service.loadUserByUsername("stud_1");

        assertThat(d.getAuthorities().iterator().next().getAuthority()).isEqualTo("ROLE_STUDENT");
    }

    @Test
    void professorLoadsWithProfRole() {
        Professor p = new Professor();
        p.setUsername("prof_1");
        p.setPassword("password");
        when(students.findByUsername("prof_1")).thenReturn(Optional.empty());
        when(professors.findByUsername("prof_1")).thenReturn(Optional.of(p));

        UserDetails d = service.loadUserByUsername("prof_1");

        assertThat(d.getAuthorities().iterator().next().getAuthority()).isEqualTo("ROLE_PROF");
    }

    @Test
    void unknownUserIsRejected() {
        when(students.findByUsername("x")).thenReturn(Optional.empty());
        when(professors.findByUsername("x")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername("x"));
    }
}
