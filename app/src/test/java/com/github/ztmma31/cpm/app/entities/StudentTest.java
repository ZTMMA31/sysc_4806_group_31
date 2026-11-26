package com.github.ztmma31.cpm.app.entities;

import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;

class StudentTest {

    @Test
    void storesStudentDetailsAndProjectAssociations() {
        CapstoneProject project = new CapstoneProject();
        CapstoneProject appliedProject = new CapstoneProject();
        Student student = new Student();

        student.setId(1L);
        student.setUsername("student1");
        student.setPassword("secret");
        student.setProject(project);
        student.setAppliedProject(appliedProject);

        assertThat(student.getId()).isEqualTo(1L);
        assertThat(student.getUsername()).isEqualTo("student1");
        assertThat(student.getPassword()).isEqualTo("secret");
        assertThat(student.getProject()).isSameInstanceAs(project);
        assertThat(student.getAppliedProject()).isSameInstanceAs(appliedProject);
    }

    @Test
    void toStringIncludesStudentDetailsButExcludesPassword() {
        Student student = new Student();
        student.setId(1L);
        student.setUsername("student1");
        student.setPassword("secret");

        assertThat(student.toString())
            .isEqualTo("Student(id=1, username=student1, project=null, appliedProject=null)");
        assertThat(student.toString()).doesNotContain("secret");
    }
}
