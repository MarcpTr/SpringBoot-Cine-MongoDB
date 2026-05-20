package com.marcptr.cine.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.marcptr.cine.client.TmdbClient;
import com.marcptr.cine.document.MovieDocument;
import com.marcptr.cine.dto.response.tmdb.TmdbMovieResponse;
import com.marcptr.cine.mapper.MovieMapper;
import com.marcptr.cine.repository.MovieDocumentRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MovieService {
    private final TmdbClient tmdbClient;
    private final MovieDocumentRepository mDocumentRepository;
    private final MovieMapper movieMapper;

   @Cacheable(value = "movies", key = "#id + '-' + #lang")
    public TmdbMovieResponse searchMovie(int id, String lang) {

        return mDocumentRepository
                .findByMovieIdAndLang((long) id, lang)
                .map(movieMapper::toDto)
                .orElseGet(() -> fetchAndSaveMovie(id, lang));
    }

    private TmdbMovieResponse fetchAndSaveMovie(int id, String lang) {

        TmdbMovieResponse tmdb = tmdbClient.searchMovie(id, lang);

        MovieDocument document = movieMapper.toDocument(tmdb, lang);

        mDocumentRepository.save(document);

        return movieMapper.toDto(document);
    }
}
