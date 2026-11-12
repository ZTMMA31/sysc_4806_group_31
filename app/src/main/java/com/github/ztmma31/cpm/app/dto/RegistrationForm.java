package com.github.ztmma31.cpm.app.dto;

public class RegistrationForm {

    private String username;
    private String password;
    private boolean professor;

    public RegistrationForm() {
    }

    public boolean isProfessor(){
        return professor;
    }

    public void setProfessor(boolean professor){
        this.professor = professor;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String email) {
        this.username = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

