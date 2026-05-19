package com.marcptr.cine.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import com.marcptr.cine.dto.response.tmdb.TmdbMovieResponse;
import com.marcptr.cine.dto.response.tmdb.TmdbSearchMovieResponse;
import com.marcptr.cine.exception.tmdb.TmdbException;
import com.marcptr.cine.model.enums.ErrorCode;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
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
                System.out.print(id);
                System.out.print(lang);
                return webClient.get()
                                .uri(uriBuilder -> uriBuilder
                                                .path("/movie/" + id)
                                                .queryParam("language", lang)
                                                .queryParam("append_to_response",
                                                                "videos,credits,images,keywords,recommendations,similar,release_dates,watch/providers")
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
                                .bodyToMono(TmdbMovieResponse.class)
                                .block();
        }
}
