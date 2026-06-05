package com.asyncteamsync.auth.service;

import com.asyncteamsync.auth.dto.*;
import com.asyncteamsync.auth.entity.RefreshToken;
import com.asyncteamsync.auth.repository.RefreshTokenRepository;
import com.asyncteamsync.security.JwtService;
import com.asyncteamsync.user.entity.User;
import com.asyncteamsync.user.entity.UserRole;
import com.asyncteamsync.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final RefreshTokenService refreshTokenService;

    private final RefreshTokenRepository refreshTokenRepository;

    public AuthResponse register(
            RegisterRequest request
    ) {

        if (userRepository.existsByEmail(request.getEmail())) {

            throw new RuntimeException(
                    "Email already registered"
            );
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(
                        passwordEncoder.encode(
                                request.getPassword()
                        )
                )
                .role(UserRole.EMPLOYEE)
                .active(true)
                .build();

        userRepository.save(user);

        String accessToken =
                jwtService.generateAccessToken(user);

        RefreshToken refreshToken =
                refreshTokenService
                        .createRefreshToken(user);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .build();
    }

    public AuthResponse login(
            LoginRequest request
    ) {

        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found"
                        ));

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        )) {

            throw new RuntimeException(
                    "Invalid credentials"
            );
        }

        String accessToken =
                jwtService.generateAccessToken(user);

        RefreshToken refreshToken =
                refreshTokenService
                        .createRefreshToken(user);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .build();
    }

    public AuthResponse refreshToken(
            RefreshTokenRequest request
    ) {
        RefreshToken refreshToken =
                refreshTokenRepository
                        .findByToken(request.getRefreshToken())
                        .orElseThrow(() ->
                                new RuntimeException("Refresh token not found"));


        if (refreshToken.isRevoked()) {
            throw new RuntimeException("Token revoked");
        }

        if (refreshToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Refresh token expired");
        }

        User user = refreshToken.getUser();

        String accessToken =
                jwtService.generateAccessToken(user);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .build();
    }

    public void logout(
            LogoutRequest request
    ) {

        refreshTokenService
                .revokeToken(
                        request.getRefreshToken()
                );
    }

}