package com.github.ztmma31.cpm.app.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegistrationForm {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    private boolean professor;

    public RegistrationForm() {
    }

}

