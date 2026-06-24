package com.marcptr.cine.service;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.cache.Cache;
import com.marcptr.cine.document.MovieDocument;
import com.marcptr.cine.integration.tmdb.client.TmdbClient;
import com.marcptr.cine.integration.tmdb.dto.TmdbMovieResponse;
import com.marcptr.cine.integration.tmdb.mapper.MovieMapper;
import com.marcptr.cine.repository.MovieDocumentRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MovieAsyncService {

    private final TmdbClient tmdbClient;
    private final MovieDocumentRepository movieDocumentRepository;
    private final MovieMapper movieMapper;
    private final CacheManager cacheManager;

    @Async
    public void refreshMovie(
            long movieId,
            String lang,
            String cacheKey,
            String refreshKey,
            ConcurrentHashMap<String, Boolean> refreshInProgress) {

        Cache movieCache = cacheManager.getCache("movies");

        try {

            TmdbMovieResponse tmdb = tmdbClient.getMovie(movieId, lang);

            MovieDocument updated = movieMapper.toDocument(tmdb, lang);

            Instant now = Instant.now();
            updated.setUpdatedAt(now);
            updated.setLastAccessedAt(now);

            movieDocumentRepository.save(updated);

            if (movieCache != null) {
                movieCache.put(cacheKey, updated);
            }

        } catch (Exception e) {
            System.out.println("Error refreshing movie " + movieId + " (" + lang + ")");
            e.printStackTrace();
        } finally {
            refreshInProgress.remove(refreshKey);
        }
    }

    @Async
    public void updateLastAccess(MovieDocument document) {

        Instant now = Instant.now();

        if (document.getLastAccessedAt() != null &&
                Duration.between(
                        document.getLastAccessedAt(),
                        now).toHours() < 24) {

            return;
        }

        document.setLastAccessedAt(now);

        movieDocumentRepository.save(document);
    }
}