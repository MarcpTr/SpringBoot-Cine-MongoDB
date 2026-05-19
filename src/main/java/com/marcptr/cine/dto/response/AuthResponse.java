package com.marcptr.cine.dto.response;



public record AuthResponse(
    User user,
    String accessToken,
    String refreshToken
) {
    public record User(
        String  id,
        String email,
        String name
    ) {}
}