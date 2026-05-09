package com.marcptr.cine.dto;


public record ActiveSessionResponse(
        String  tokenId,
        String createdAt
) {}