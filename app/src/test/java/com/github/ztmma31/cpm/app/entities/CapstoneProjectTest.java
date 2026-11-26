package com.github.ztmma31.cpm.app.entities;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.google.common.truth.Truth.assertThat;

class CapstoneProjectTest {

    @Test
    void storesProjectDetailsAndRelationships() {
        Professor professor = new Professor();
        Student student = new Student();
        Student appliedStudent = new Student();
        CapstoneProject project = new CapstoneProject();

        project.setId(1L);
        project.setTitle("Test Project");
        project.setDescription("Project description");
        project.setProfessor(professor);
        project.setStudents(Set.of(student));
        project.setAppliedStudents(Set.of(appliedStudent));

        assertThat(project.getId()).isEqualTo(1L);
        assertThat(project.getTitle()).isEqualTo("Test Project");
        assertThat(project.getDescription()).isEqualTo("Project description");
        assertThat(project.getProfessor()).isSameInstanceAs(professor);
        assertThat(project.getStudents()).containsExactly(student);
        assertThat(project.getAppliedStudents()).containsExactly(appliedStudent);
    }

    @Test
    void toStringIncludesProjectDetailsAndRelationships() {
        CapstoneProject project = new CapstoneProject();
        project.setId(1L);
        project.setTitle("Test Project");
        project.setDescription("Project description");

        assertThat(project.toString())
            .isEqualTo("CapstoneProject(id=1, title=Test Project, description=Project description, "
                + "restrictions=null, students=null, appliedStudents=null, professor=null)");
    }

    @Test
    void addsAndRemovesRestrictions() {
        Program firstRestriction = new Program();
        Program secondRestriction = new Program();
        CapstoneProject project = new CapstoneProject();
        project.setRestrictions(new HashSet<>());

        project.addRestrictions(Set.of(firstRestriction, secondRestriction));
        project.removeRestrictions(List.of(firstRestriction));

        assertThat(project.getRestrictions()).containsExactly(secondRestriction);
    }

    @Test
    void addsAndRemovesStudents() {
        Student firstStudent = new Student();
        Student secondStudent = new Student();
        CapstoneProject project = new CapstoneProject();
        project.setStudents(new HashSet<>());

        project.addStudent(firstStudent);
        project.addStudent(secondStudent);
        project.removeStudent(firstStudent);

        assertThat(project.getStudents()).containsExactly(secondStudent);
    }
}
