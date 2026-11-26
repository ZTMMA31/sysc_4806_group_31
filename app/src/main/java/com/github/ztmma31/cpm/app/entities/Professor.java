package com.github.ztmma31.cpm.app.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;

    @OneToMany(mappedBy = "professor", cascade = CascadeType.ALL)
    private List<CapstoneProject> projects;

    public void addProject(CapstoneProject capstoneProject) {
        this.projects.add(capstoneProject);
    }

    @Override
    public String toString() {
        return String.format(
            "Professor: [id=%s, username=%s]", id, username
        );
    }
}
