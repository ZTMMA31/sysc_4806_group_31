package com.github.ztmma31.cpm.app.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;

    @ToString.Exclude
    private String password;

    /// The project that this student is registered to.
    @ManyToOne
    @JoinColumn(name = "project_id")
    private CapstoneProject project;

    /// The project that this student has applied to, if any.
    @ManyToOne
    private CapstoneProject appliedProject;

    public void removeProject() {
        if (this.project != null) {
            this.project = null;
        }
    }
}
