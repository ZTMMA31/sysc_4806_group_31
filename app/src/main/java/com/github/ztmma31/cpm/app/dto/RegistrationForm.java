package com.github.ztmma31.cpm.app.dto;

public class RegistrationForm {

    private String name;
    private String email;
    private String password;
    private boolean professor;

    public RegistrationForm() {
    }

    public String getName() {
        return name;
    }

    public boolean isProfessor(){
        return professor;
    }

    public void setProfessor(){
        this.professor = true;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

