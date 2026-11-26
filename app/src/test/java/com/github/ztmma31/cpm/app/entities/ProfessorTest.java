package com.github.ztmma31.cpm.app.entities;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static com.google.common.truth.Truth.assertThat;

class ProfessorTest {

    @Test
    void storesProfessorDetailsAndManagedProjects() {
        CapstoneProject project = new CapstoneProject();
        Professor professor = Professor.of("professor1", "secret");

        professor.setId(1L);
        professor.setProjects(Set.of(project));

        assertThat(professor.getId()).isEqualTo(1L);
        assertThat(professor.getUsername()).isEqualTo("professor1");
        assertThat(professor.getPassword()).isEqualTo("secret");
        assertThat(professor.getProjects()).containsExactly(project);
    }

    @Test
    void toStringIncludesProfessorDetailsButExcludesPassword() {
        Professor professor = Professor.of("professor1", "secret");
        professor.setId(1L);

        assertThat(professor.toString())
            .isEqualTo("Professor(id=1, username=professor1, projects=null)");
        assertThat(professor.toString()).doesNotContain("secret");
    }
}
