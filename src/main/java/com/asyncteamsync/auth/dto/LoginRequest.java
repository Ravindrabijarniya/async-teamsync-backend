package com.asyncteamsync.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
public class LoginRequest {

    @Email
    private String email;

    @NotBlank
    private  String password;
}
