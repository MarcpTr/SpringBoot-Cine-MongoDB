package com.marcptr.cine.dto.response.tmdb;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.marcptr.cine.model.SearchResult;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TmdbSearchMovieResponse {
    private int page;
    private List<SearchResult> results;
    @JsonProperty("total_pages")
    private int totalPages;
    @JsonProperty("total_results")
    private int totalResults;
}
