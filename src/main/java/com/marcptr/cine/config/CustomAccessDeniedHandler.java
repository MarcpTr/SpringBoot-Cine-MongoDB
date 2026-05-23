package com.marcptr.cine.config;

import java.io.IOException;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcptr.cine.dto.ApiError;
import com.marcptr.cine.dto.ApiResponse;
import com.marcptr.cine.model.enums.ErrorCode;
import com.marcptr.cine.utils.MessageResolver;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/**
 * Manejador personalizado para gestionar excepciones de acceso denegado
 * dentro del contexto de Spring Security.
 *
 * <p>
 * Esta clase intercepta los intentos de acceso a recursos protegidos cuando
 * el usuario **sí está autenticado**, pero **no posee las autorizaciones
 * necesarias**
 * para acceder al recurso solicitado. En estos casos, Spring Security lanza
 * una {@link AccessDeniedException}, que es procesada por este manejador.
 * </p>
 *
 * <p>
 * En lugar de redirigir a una página de error, este manejador devuelve una
 * respuesta JSON estandarizada con código HTTP 403 (Forbidden).
 * </p>
 */
@RequiredArgsConstructor
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    /**
     * Mapper utilizado para serializar la respuesta de error en formato JSON.
     */
    private final ObjectMapper mapper;
    private final MessageSource messageSource;
    private final MessageResolver messageResolver;

    /**
     * Maneja las excepciones de tipo {@link AccessDeniedException}, las cuales
     * se producen cuando un usuario autenticado intenta acceder a un recurso
     * para el cual **no tiene permisos suficientes** según las reglas de
     * autorización definidas en Spring Security.
     *
     * <p>
     * Este método construye una respuesta JSON con un cuerpo estándar
     * {@link ApiResponse} que incluye un objeto {@link ApiError} describiendo
     * el motivo del acceso denegado.
     * </p>
     *
     * @param request               la solicitud HTTP que provocó el intento de
     *                              acceso no autorizado
     * @param response              la respuesta HTTP donde se escribirá el cuerpo
     *                              JSON
     * @param accessDeniedException la excepción lanzada por Spring Security
     * @throws IOException      si ocurre un error al escribir la respuesta
     * @throws ServletException si ocurre un error en el procesamiento del servlet
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
            AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType(("application/json"));
        ApiResponse<Void> body = ApiResponse.fail(ErrorCode.FORBIDDEN, messageResolver.resolveMessage(ErrorCode.FORBIDDEN), null);
        mapper.writeValue(response.getOutputStream(), body);
    }
}
