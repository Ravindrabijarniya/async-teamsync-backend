package com.asyncteamsync.auth.dto;

import lombok.*;


@Data
@Builder
public class AuthResponse {

    private String accessToken;

    private String refreshToken;
}
