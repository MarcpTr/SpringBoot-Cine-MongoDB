package com.marcptr.cine.controller;

import org.springframework.web.bind.annotation.RestController;

import com.marcptr.cine.dto.request.tmdb.TrendingRequest;
import com.marcptr.cine.dto.response.MovieResponse;
import com.marcptr.cine.dto.response.tmdb.TmdbTrendResponse;
import com.marcptr.cine.service.MovieService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Locale;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/tmdb")
public class TmdbController {

    private final MovieService mService;

    @GetMapping("/trending")
    public TmdbTrendResponse getTrending(@Valid TrendingRequest tRequest, Locale locale) {
        switch (tRequest.getPeriod()) {
            case WEEK:
                return mService.getTrendingWeek(tRequest.getPage(), locale.getLanguage() + "-" + locale.getCountry());
            case DAY:
                return mService.getTrendingDay(tRequest.getPage(), locale.getLanguage() + "-" + locale.getCountry());
            default:
                return mService.getTrendingDay(tRequest.getPage(), locale.getLanguage() + "-" + locale.getCountry());
        }
    }

    @GetMapping("/movie/{id}")
    public MovieResponse getMovie(@PathVariable long id, Locale locale) {
        return mService.getMovie(id, locale.getLanguage() + "-" + locale.getCountry());
    }

}
