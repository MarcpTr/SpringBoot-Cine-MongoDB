package com.marcptr.cine.service;

import org.springframework.stereotype.Service;

import com.marcptr.cine.client.TmdbClient;
import com.marcptr.cine.dto.response.tmdb.TmdbMovieResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MovieService {
    private final TmdbClient tmdbClient;

    public TmdbMovieResponse searchMovie(int query,String lang){

        return tmdbClient.searchMovie(query, lang);
    }
}
