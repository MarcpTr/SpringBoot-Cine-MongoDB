package com.marcptr.cine.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.marcptr.cine.document.Token;
import com.marcptr.cine.model.enums.TokenType;

public interface TokenRepository extends MongoRepository<Token, String> {

        Optional<Token> findByJti(String jti);

        List<Token> findAllByUserIdAndExpiredFalseAndRevokedFalse(String userId);

        List<Token> findAllByUserIdAndTokenTypeAndExpiredFalseAndRevokedFalse(
                        String userId,
                        TokenType tokenType);

        Optional<Token> findByToken(String token);

        List<Token> findAllByUserIdAndTokenTypeAndExpiredFalseAndRevokedFalseOrderByCreatedAtDesc(
                        String userId,
                        TokenType tokenType);
}