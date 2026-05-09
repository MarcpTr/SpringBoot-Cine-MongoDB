package com.marcptr.cine.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
    @NotBlank(message = "{error.login.USERNAME_OR_EMAIL_REQUIRED}")
    String usernameOrEmail,

    @NotBlank(message = "{error.login.PASSWORD_REQUIRED}")
    String password
) {}