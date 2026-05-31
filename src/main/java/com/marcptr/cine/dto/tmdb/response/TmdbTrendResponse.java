package com.marcptr.cine.dto.tmdb.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.marcptr.cine.model.TrendResults;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TmdbTrendResponse {
    private int page;
    private List<TrendResults> results;
    @JsonProperty("total_pages")
    private int totalPages;
    @JsonProperty("total_results")
    private int totalResults;
}
