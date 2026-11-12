package com.github.ztmma31.cpm.app.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Professor {
    private Long id;
    private String username;
    private String password;
    private List<CapstoneProject> projects;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @OneToMany(cascade = CascadeType.ALL)
    public List<CapstoneProject> getProjects() {
        return projects;
    }

    public void setProjects(List<CapstoneProject> projects) {
        this.projects = projects;
    }

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
