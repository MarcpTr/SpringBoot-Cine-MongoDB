package com.marcptr.cine.client;

import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import com.marcptr.cine.dto.response.tmdb.TmdbMovieResponse;
import com.marcptr.cine.dto.response.tmdb.TmdbSearchMovieResponse;
import com.marcptr.cine.exception.tmdb.TmdbException;
import com.marcptr.cine.model.enums.ErrorCode;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Component
public class TmdbClient {
        private final WebClient webClient;
        @Value("${tmdb.api-key}")
        String apiKey;

        public TmdbSearchMovieResponse searchMovies(String title) {
                return webClient.get()
                                .uri(uriBuilder -> uriBuilder
                                                .path("/search/movie")
                                                .queryParam("query", title)
                                                .queryParam("api_key", apiKey)
                                                .build())
                                .retrieve()
                                .onStatus(
                                                HttpStatusCode::is4xxClientError,
                                                response -> Mono.error(
                                                                new TmdbException(ErrorCode.TMDB_CLIENT)))
                                .onStatus(
                                                HttpStatusCode::is5xxServerError,
                                                response -> Mono.error(
                                                                new TmdbException(ErrorCode.TMDB_SERVER)))
                                .bodyToMono(TmdbSearchMovieResponse.class)
                                .block();
        }

        public TmdbMovieResponse searchMovie(int id, String lang) {
                try {
                        return webClient.get()
                                        .uri(uriBuilder -> uriBuilder
                                                        .path("/movie/" + id)
                                                        .queryParam("language", lang)
                                                        .queryParam("append_to_response",
                                                                        "videos,credits")
                                                        .queryParam("api_key", apiKey)
                                                        .build())
                                        .retrieve()
                                        .onStatus(HttpStatusCode::is4xxClientError, response -> response
                                                        .bodyToMono(String.class)
                                                        .defaultIfEmpty("TMDB client error")
                                                        .flatMap(body -> Mono.error(
                                                                        new TmdbException(
                                                                                        ErrorCode.TMDB_CLIENT))))
                                        .onStatus(HttpStatusCode::is5xxServerError, response -> response
                                                        .bodyToMono(String.class)
                                                        .defaultIfEmpty("TMDB server error")
                                                        .flatMap(body -> Mono.error(
                                                                        new TmdbException(
                                                                                        ErrorCode.TMDB_SERVER))))

                                        .bodyToMono(TmdbMovieResponse.class)

                                        .timeout(Duration.ofSeconds(5))

                                        .doOnError(error -> log.error("Error calling TMDB API for id={}", id, error))

                                        .block();

                } catch (WebClientRequestException e) {
                        throw new TmdbException(ErrorCode.TMDB_CONNECTION, e);
                } catch (TmdbException e) {
                        throw e;
                } catch (Exception e) {
                        throw new TmdbException(ErrorCode.TMDB_UNKNOWN, e);
                }
        }
}
