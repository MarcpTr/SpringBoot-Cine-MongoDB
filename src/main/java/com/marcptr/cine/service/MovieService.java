package com.marcptr.cine.service;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.marcptr.cine.document.MovieDocument;
import com.marcptr.cine.dto.response.MovieResponse;
import com.marcptr.cine.exception.tmdb.TmdbNotFoundException;
import com.marcptr.cine.integration.tmdb.client.TmdbClient;
import com.marcptr.cine.integration.tmdb.dto.TmdbMovieResponse;
import com.marcptr.cine.integration.tmdb.mapper.MovieMapper;
import com.marcptr.cine.model.enums.ErrorCode;
import com.marcptr.cine.repository.MovieDocumentRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MovieService {
    private final TmdbClient tmdbClient;
    private final MovieDocumentRepository mDocumentRepository;
    private final MovieMapper movieMapper;
    private final CacheManager cManager;
    private final ConcurrentHashMap<String, Boolean> refreshInProgress = new ConcurrentHashMap<>();
    private Cache movieCache;

    @PostConstruct
    public void init() {
        this.movieCache = cManager.getCache("movies");
    }

    public MovieResponse getMovie(long id, String lang) {
        String key = buildKey(id, lang);

        MovieDocument cached = movieCache.get(key, MovieDocument.class);

        if (cached != null) {
            return handleCachedDocument(cached, key);
        }

        MovieDocument document = mDocumentRepository
                .findByMovieIdAndLang(id, lang)
                .orElse(null);

        if (document == null) {
            return fetchAndSaveMovie(id, lang);
        }

        updateLastAccessAsync(document);

        movieCache.put(key, document);

        return handleCachedDocument(document, key);
    }

    private MovieResponse handleCachedDocument(MovieDocument document, String cacheKey) {

        if (document.isNotFound()) {
            long ageDays = Duration.between(
                    document.getUpdatedAt(),
                    Instant.now()).toDays();

            if (ageDays < 1) {
                throw new TmdbNotFoundException(ErrorCode.TMDB_NOT_FOUND);
            }

            MovieDocument refreshed = refreshSynchronously(
                    document.getMovieId(),
                    document.getLang(),
                    cacheKey);

            if (refreshed.isNotFound()) {
                throw new TmdbNotFoundException(ErrorCode.TMDB_NOT_FOUND);
            }

            return movieMapper.toDto(refreshed);
        }

        long ageDays = Duration.between(
                document.getUpdatedAt(),
                Instant.now()).toDays();

        if (ageDays < 7) {
            return movieMapper.toDto(document);
        }

        if (ageDays <= 10) {

            refreshAsyncIfNeeded(
                    document.getMovieId(),
                    document.getLang(),
                    cacheKey);

            return movieMapper.toDto(document);
        }

        MovieDocument refreshed = refreshSynchronously(
                document.getMovieId(),
                document.getLang(),
                cacheKey);

        return movieMapper.toDto(refreshed);
    }

    private MovieDocument refreshSynchronously(
            long movieId,
            String lang,
            String cacheKey) {

        try {

            TmdbMovieResponse tmdb = tmdbClient.getMovie(movieId, lang);

            MovieDocument updated = movieMapper.toDocument(tmdb, lang);
            Instant now = Instant.now();

            updated.setUpdatedAt(now);
            updated.setLastAccessedAt(now);

            mDocumentRepository.save(updated);

            movieCache.put(cacheKey, updated);

            return updated;
        } catch (TmdbNotFoundException e) {

            MovieDocument notFoundDoc = MovieDocument.builder()
                    .id(buildKey(movieId, lang))
                    .movieId(movieId)
                    .lang(lang)
                    .notFound(true)
                    .updatedAt(Instant.now())
                    .lastAccessedAt(Instant.now())
                    .build();

            mDocumentRepository.save(notFoundDoc);

            throw e;
        }

    }

    private void refreshAsyncIfNeeded(Long movieId, String lang, String cacheKey) {
        String refreshKey = buildKey(movieId, lang);

        if (refreshInProgress.putIfAbsent(
                refreshKey,
                true) != null) {
            return;
        }

        refreshAsync(movieId, lang, cacheKey, refreshKey);
    }

    @Async
    public void refreshAsync(
            long movieId,
            String lang,
            String cacheKey,
            String refreshKey) {

        try {

            TmdbMovieResponse tmdb = tmdbClient.getMovie(movieId, lang);

            MovieDocument updated = movieMapper.toDocument(tmdb, lang);

            Instant now = Instant.now();
            updated.setUpdatedAt(now);
            updated.setLastAccessedAt(now);

            mDocumentRepository.save(updated);
            movieCache.put(cacheKey, updated);

        } catch (Exception e) {
        } finally {
            refreshInProgress.remove(refreshKey);
        }
    }

    @Async
    private void updateLastAccessAsync(
            MovieDocument document) {

        Instant now = Instant.now();

        if (document.getLastAccessedAt() != null &&
                Duration.between(
                        document.getLastAccessedAt(),
                        now).toHours() < 24) {

            return;
        }

        document.setLastAccessedAt(now);

        mDocumentRepository.save(document);
    }

    private MovieResponse fetchAndSaveMovie(long id, String lang) {

        try {

            TmdbMovieResponse tmdb = tmdbClient.getMovie(id, lang);

            MovieDocument document = movieMapper.toDocument(tmdb, lang);

            Instant now = Instant.now();
            document.setUpdatedAt(now);
            document.setLastAccessedAt(now);

            mDocumentRepository.save(document);
            movieCache.put(buildKey(id, lang), document);

            return movieMapper.toDto(document);

        } catch (TmdbNotFoundException e) {

            MovieDocument notFoundDoc = MovieDocument.builder()
                    .id(buildKey(id, lang))
                    .movieId(id)
                    .lang(lang)
                    .notFound(true)
                    .updatedAt(Instant.now())
                    .lastAccessedAt(Instant.now())
                    .build();

            mDocumentRepository.save(notFoundDoc);

            throw e;
        }
    }

    private String buildKey(long id, String lang) {
        return id + "-" + lang;
    }
}