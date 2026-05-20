package com.marcptr.cine.controller;

import org.springframework.web.bind.annotation.RestController;

import com.marcptr.cine.dto.request.tmdb.SearchRequest;
import com.marcptr.cine.dto.response.tmdb.TmdbMovieResponse;
import com.marcptr.cine.service.MovieService;

import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Locale;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/tmdb")
public class TmdbController {

    private final MovieService mService;

    @GetMapping("/search")
    public String getMethodName(@Valid @ModelAttribute SearchRequest sRequest, Locale locale) {
        return "";
    }

    @GetMapping("/movie/{id}")
    public TmdbMovieResponse getMethodName(@PathVariable int id, Locale locale) {
        return mService.searchMovie(id, locale.getLanguage()+"-"+locale.getCountry() );
    }

}
