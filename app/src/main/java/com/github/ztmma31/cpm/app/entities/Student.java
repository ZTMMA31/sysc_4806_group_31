package com.github.ztmma31.cpm.app.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Student {
    private Long id;
    private String username;
    private String password;
    private CapstoneProject project;

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

    @OneToOne(cascade = CascadeType.ALL)
    public CapstoneProject getProject() {
        return project;
    }

    public void setProject(CapstoneProject project) {
        this.project = project;
    }

    @Override
    public String toString() {
        return String.format(
            "Student: [id=%s, username=%s]", id, username
        );
    }
}
