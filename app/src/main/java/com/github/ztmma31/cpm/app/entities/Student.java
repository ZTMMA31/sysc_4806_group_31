package com.github.ztmma31.cpm.app.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private CapstoneProject project;

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
