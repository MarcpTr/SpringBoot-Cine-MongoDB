package com.marcptr.cine.service;

import java.time.Instant;
import java.util.Locale;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;
import org.apache.commons.codec.digest.DigestUtils;
import com.marcptr.cine.document.MovieDocument;
import com.marcptr.cine.document.SearchDocument;
import com.marcptr.cine.dto.request.SearchRequest;
import com.marcptr.cine.dto.response.MovieResponse;
import com.marcptr.cine.dto.response.SearchResponse;
import com.marcptr.cine.exception.tmdb.TmdbNotFoundException;
import com.marcptr.cine.integration.tmdb.client.TmdbClient;
import com.marcptr.cine.integration.tmdb.dto.TmdbMovieResponse;
import com.marcptr.cine.integration.tmdb.dto.TmdbSearchMovieResponse;
import com.marcptr.cine.integration.tmdb.mapper.SearchMapper;
import com.marcptr.cine.repository.SearchDocumentRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SearchService {
  private final TmdbClient tmdbClient;
  private final SearchDocumentRepository sDocumentRepository;
  private final SearchMapper sMapper;

  @Cacheable(value = "search", key = "#query + '-' + #page + '-' + #lang")
  public SearchResponse getSearch(String query, Integer page, String lang) {
    String rawKey = page + "|" + lang + "|" + query;
    String id = DigestUtils.sha256Hex(rawKey);

    return sDocumentRepository
        .findById(id)
        .map(doc -> {
          return sMapper.toDto(doc);
        })
        .orElseGet(() -> fetchAndSaveSearch(id, query, page, lang));

  }

  private SearchResponse fetchAndSaveSearch(String id, String query, Integer page, String lang) {

    TmdbSearchMovieResponse tmdb = tmdbClient.searchMovies(query, page, lang);
    SearchDocument document = sMapper.toDocument(tmdb, id, query, lang, page);

    sDocumentRepository.save(document);

    return sMapper.toDto(document);
  }
}
