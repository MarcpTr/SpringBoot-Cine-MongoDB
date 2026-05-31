package com.marcptr.cine.controller;

import org.springframework.web.bind.annotation.RestController;

import com.marcptr.cine.dto.response.MovieResponse;
import com.marcptr.cine.dto.tmdb.request.SearchRequest;
import com.marcptr.cine.dto.tmdb.request.TrendingRequest;
import com.marcptr.cine.dto.tmdb.response.TmdbSearchMovieResponse;
import com.marcptr.cine.dto.tmdb.response.TmdbTrendResponse;
import com.marcptr.cine.service.MovieService;
import com.marcptr.cine.service.SearchService;
import com.marcptr.cine.service.TrendService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Locale;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/data")
public class TmdbController {

    private final MovieService mService;
    private final TrendService tService;
    private final SearchService sService;

    @GetMapping("/trending")
    public TmdbTrendResponse getTrending(@Valid TrendingRequest tRequest, Locale locale) {
        switch (tRequest.getPeriod()) {
            case WEEK:
                return tService.getTrendingWeek(tRequest.getPage(), locale.getLanguage() + "-" + locale.getCountry());
            case DAY:
                return tService.getTrendingDay(tRequest.getPage(), locale.getLanguage() + "-" + locale.getCountry());
            default:
                return tService.getTrendingDay(tRequest.getPage(), locale.getLanguage() + "-" + locale.getCountry());
        }
    }

    @GetMapping("/movie/{id}")
    public MovieResponse getMovie(@PathVariable long id, Locale locale) {
        return mService.getMovie(id, locale.getLanguage() + "-" + locale.getCountry());
    }

    /* @GetMapping("/search")
    public SearchMovieResponse search(@Valid SearchRequest sRequest, Locale locale) {
        sService.getSearch(sRequest.getQuery(), sRequest.getPage(), locale.getLanguage()+locale.getCountry());
        return sRequest.getQuery() + sRequest.getPage();
    } */
}
