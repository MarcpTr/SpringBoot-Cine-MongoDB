package com.marcptr.cine.dto;

public record RefreshResponse(
   String accessToken,
   String refreshToken
) {}