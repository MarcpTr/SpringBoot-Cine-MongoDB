package com.marcptr.cine.service;

import java.util.Locale;

import org.springframework.stereotype.Service;

import com.marcptr.cine.client.TmdbClient;
import com.marcptr.cine.dto.request.tmdb.SearchRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SearchService {
    private final TmdbClient tmdbClient;

  /*   @Cacheable(value = "search", key = "#query + '-' + #page + '-' #lang")
    public SearchMovieResponse getSearch(String query, Integer page, String lang) {
        throw new UnsupportedOperationException("Unimplemented method 'getSearch'");
    }
 */
}
