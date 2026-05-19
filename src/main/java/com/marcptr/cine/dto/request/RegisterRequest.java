package com.marcptr.cine.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record RegisterRequest(

        @Pattern(regexp = "^[a-zA-Z0-9](?:[a-zA-Z0-9_]{0,8}[a-zA-Z0-9])?$", message = "{error.register.USERNAME_FORMAT}") @NotBlank(message = "{error.register.USERNAME_REQUIRED}") String username,

        @Email(message = "{error.register.EMAIL_FORMAT}") @NotBlank(message = "{error.register.EMAIL_REQUIRED}") String email,

        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{8,}$", message = "{error.register.PASSWORD_FORMAT}") @NotBlank(message = "{error.register.PASSWORD_REQUIRED}") String password

) {
}