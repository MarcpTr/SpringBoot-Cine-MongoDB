package com.marcptr.cine.service;

import java.time.Instant;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.marcptr.cine.client.TmdbClient;
import com.marcptr.cine.document.MovieDocument;
import com.marcptr.cine.dto.response.MovieResponse;
import com.marcptr.cine.dto.tmdb.response.TmdbMovieResponse;
import com.marcptr.cine.exception.tmdb.TmdbNotFoundException;
import com.marcptr.cine.mapper.MovieMapper;
import com.marcptr.cine.model.enums.ErrorCode;
import com.marcptr.cine.repository.MovieDocumentRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MovieService {
    private final TmdbClient tmdbClient;
    private final MovieDocumentRepository mDocumentRepository;
    private final MovieMapper movieMapper;

    @Cacheable(value = "movies", key = "#id + '-' + #lang")
    public MovieResponse getMovie(long id, String lang) {
        return mDocumentRepository
                .findByMovieIdAndLang(id, lang)
                .map(doc -> {

                    if (doc.isNotFound()) {
                        throw new TmdbNotFoundException(ErrorCode.TMDB_NOT_FOUND);
                    }

                    return movieMapper.toDto(doc);
                })
                .orElseGet(() -> fetchAndSaveMovie(id, lang));
    }

    private MovieResponse fetchAndSaveMovie(long id, String lang) {

        try {

            TmdbMovieResponse tmdb = tmdbClient.getMovie(id, lang);

            MovieDocument document = movieMapper.toDocument(tmdb, lang);

            mDocumentRepository.save(document);

            return movieMapper.toDto(document);

        } catch (TmdbNotFoundException e) {
            MovieDocument notFoundDoc = MovieDocument.builder()
                    .id(id + "_" + lang)
                    .movieId(id)
                    .lang(lang)
                    .notFound(true)
                    .cachedAt(Instant.now())
                    .build();

            mDocumentRepository.save(notFoundDoc);

            throw e;
        }
    }

}