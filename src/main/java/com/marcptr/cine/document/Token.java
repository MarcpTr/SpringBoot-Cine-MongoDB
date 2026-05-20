package com.marcptr.cine.document;

import java.time.LocalDateTime;

import java.util.UUID;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.marcptr.cine.model.enums.TokenType;

@Document(collection = "tokens")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Token {

    @Id
    private String id;

    private String token;

    private boolean revoked;

    private boolean expired;

    private TokenType tokenType;

    private String jti;

    private LocalDateTime createdAt;

    // referencia simple
    private String userId;

    public static Token create(
            String token,
            TokenType tokenType,
            String jti,
            String userId) {
        return Token.builder()
                .id(UUID.randomUUID().toString())
                .token(token)
                .revoked(false)
                .expired(false)
                .tokenType(tokenType)
                .jti(jti)
                .userId(userId)
                .createdAt(LocalDateTime.now())
                .build();
    }
}