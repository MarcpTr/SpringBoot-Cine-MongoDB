package com.marcptr.cine.security;

import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcptr.cine.dto.common.ApiError;
import com.marcptr.cine.dto.common.ApiResponse;
import com.marcptr.cine.model.enums.ErrorCode;
import com.marcptr.cine.utils.MessageResolver;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/**
 * Punto de entrada personalizado para gestionar errores de autenticación
 * dentro del contexto de Spring Security.
 *
 * <p>
 * Este componente se activa cuando un usuario intenta acceder a un recurso
 * protegido **sin estar autenticado**. En estos casos, Spring Security lanza
 * una {@link AuthenticationException}, indicando que el usuario debe iniciar
 * sesión antes de continuar.
 * </p>
 *
 * <p>
 * En lugar de redirigir a una página de login, este entry point devuelve
 * una respuesta JSON estandarizada con código HTTP 401 (Unauthorized).
 * </p>
 */
@RequiredArgsConstructor
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    /**
     * Mapper utilizado para serializar la respuesta de error en formato JSON.
     */
    private final ObjectMapper mapper;
    private final MessageResolver messageResolver;

    /**
     * Maneja las excepciones de tipo {@link AuthenticationException}, las cuales
     * se producen cuando un usuario no autenticado intenta acceder a un recurso
     * que requiere autenticación previa.
     *
     * <p>
     * Este método construye una respuesta JSON con un cuerpo estándar
     * {@link ApiResponse} que incluye un objeto {@link ApiError} describiendo
     * que la autenticación es obligatoria para acceder al recurso solicitado.
     * </p>
     *
     * @param request       la solicitud HTTP que provocó el intento de acceso sin
     *                      autenticación
     * @param response      la respuesta HTTP donde se escribirá el cuerpo JSON
     * @param authException la excepción lanzada por Spring Security
     * @throws IOException      si ocurre un error al escribir la respuesta
     * @throws ServletException si ocurre un error en el procesamiento del servlet
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        ApiResponse<Void> body = ApiResponse.fail(ErrorCode.UNAUTHORIZED, messageResolver.resolveMessage(ErrorCode.UNAUTHORIZED), null);
        mapper.writeValue(response.getOutputStream(), body);
    }


}
