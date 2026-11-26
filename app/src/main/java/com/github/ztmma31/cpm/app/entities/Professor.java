package com.github.ztmma31.cpm.app.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Getter
@Setter
@ToString
@Entity
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @ToString.Exclude
    private String password;

    @OneToMany(mappedBy = "professor", cascade = CascadeType.ALL)
    private Set<CapstoneProject> projects;

    public static Professor of(String username, String password) {
        Professor professor = new Professor();
        professor.setUsername(username);
        professor.setPassword(password);
        return professor;
    }
}
