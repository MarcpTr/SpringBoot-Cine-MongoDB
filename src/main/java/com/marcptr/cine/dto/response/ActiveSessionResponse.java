package com.marcptr.cine.dto.response;


public record ActiveSessionResponse(
        String  tokenId,
        String createdAt
) {}