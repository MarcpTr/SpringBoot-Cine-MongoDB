package com.marcptr.cine.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import java.util.HashMap;
import java.util.Map;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.marcptr.cine.document.Token;
import com.marcptr.cine.document.User;
import com.marcptr.cine.dto.request.LoginRequest;
import com.marcptr.cine.dto.request.RefreshRequest;
import com.marcptr.cine.dto.request.RegisterRequest;
import com.marcptr.cine.dto.response.AuthResponse;
import com.marcptr.cine.dto.response.RefreshResponse;
import com.marcptr.cine.exception.InvalidCredentialsException;
import com.marcptr.cine.exception.JwtAuthenticationException;
import com.marcptr.cine.exception.ResourceAlreadyExistsException;
import com.marcptr.cine.model.enums.ErrorCode;
import com.marcptr.cine.model.enums.Role;
import com.marcptr.cine.model.enums.TokenType;
import com.marcptr.cine.repository.TokenRepository;
import com.marcptr.cine.utils.MessageResolver;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final TokenService tokenService;
    private final TokenRepository tokenRepository;
    private final SessionPolicyService sessionPolicyService;
    private final MessageResolver messageResolver;

    public AuthResponse register(RegisterRequest request) {
        Map<ErrorCode, String> errors = new HashMap<>();

        if (userService.existsByEmail(request.email())) {
            errors.put(ErrorCode.EMAIL_ALREADY_EXISTS, messageResolver.resolveMessage(ErrorCode.EMAIL_ALREADY_EXISTS));
        }

        if (userService.existsByUsername(request.username())) {
            errors.put(ErrorCode.USERNAME_ALREADY_EXISTS,  messageResolver.resolveMessage(ErrorCode.USERNAME_ALREADY_EXISTS));
        }

        if (!errors.isEmpty()) {
            throw new ResourceAlreadyExistsException(ErrorCode.RESOURCE_ALREADY_EXIST, errors);
        }

        User user = User.create(
                request.username(),
                request.email(),
                passwordEncoder.encode(request.password()),
                Role.ROLE_USER);

        User savedUser = userService.save(user);

        tokenService.revokeAllUserTokens(savedUser);

        String refreshToken = jwtService.generateRefreshToken(savedUser);
        String accessToken = jwtService.generateAccessToken(savedUser, refreshToken);

        tokenService.saveUserToken(savedUser, refreshToken, TokenType.REFRESH);

        return new AuthResponse(
                new AuthResponse.User(savedUser.getId(), savedUser.getUsername(), savedUser.getEmail()),
                accessToken,
                refreshToken);
    }

    public AuthResponse login(LoginRequest request) {

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                request.usernameOrEmail(),
                request.password());

        Authentication auth;
        try {
            auth = authenticationManager.authenticate(authRequest);
        } catch (AuthenticationException e) {
            throw new InvalidCredentialsException(ErrorCode.INVALID_CREDENTIALS);
        }

        User user = (User) auth.getPrincipal();

        int maxSessions = sessionPolicyService.getMaxSessions();

        tokenService.enforceSessionLimit(user, maxSessions);

        String refreshToken = jwtService.generateRefreshToken(user);
        String accessToken = jwtService.generateAccessToken(user, refreshToken);

        tokenService.saveUserToken(user, refreshToken, TokenType.REFRESH);

        return new AuthResponse(
                new AuthResponse.User(user.getId(), user.getUsername(), user.getEmail()),
                accessToken,
                refreshToken);
    }

    public RefreshResponse refresh(RefreshRequest request) {
        String refreshToken = request.refreshToken();

        Claims claims;
        try {
            claims = jwtService.extractClaim(refreshToken, c -> c);
        } catch (JwtException e) {
            throw new JwtAuthenticationException(ErrorCode.INVALID_TOKEN, 
                    messageResolver.resolveMessage(ErrorCode.INVALID_TOKEN));
        }

        String username = claims.getSubject();
        String jti = claims.getId();

        User user = (User) userService.loadUserByUsername(username);

        Token storedToken = tokenRepository.findByJti(jti)
                .orElseThrow(() -> new JwtAuthenticationException(ErrorCode.INVALID_TOKEN,  messageResolver.resolveMessage(ErrorCode.TOKEN_NOT_FOUND)));

        if (storedToken.isExpired() || storedToken.isRevoked()) {
            throw new JwtAuthenticationException(ErrorCode.INVALID_TOKEN,  messageResolver.resolveMessage(ErrorCode.INVALID_TOKEN));
        }

        if (storedToken.getTokenType() != TokenType.REFRESH) {
            throw new JwtAuthenticationException(ErrorCode.INVALID_TOKEN, messageResolver.resolveMessage(ErrorCode.INVALID_TOKEN_TYPE));
        }

        if (!jwtService.isTokenValid(refreshToken, user, TokenType.REFRESH)) {
            throw new JwtAuthenticationException(ErrorCode.INVALID_TOKEN,  messageResolver.resolveMessage(ErrorCode.INVALID_TOKEN));
        }

        tokenService.revokeToken(refreshToken);

        String newRefreshToken = jwtService.generateRefreshToken(user);
        String newAccessToken = jwtService.generateAccessToken(user, newRefreshToken);

        tokenService.saveUserToken(user, newRefreshToken, TokenType.REFRESH);

        return new RefreshResponse(newAccessToken, newRefreshToken);
    }
}
