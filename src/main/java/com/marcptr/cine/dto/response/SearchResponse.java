package com.marcptr.cine.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.marcptr.cine.model.SearchResult;

import lombok.Data;

@Data

public class SearchResponse {
   private int page;
    private List<SearchResult> results;
    @JsonProperty("total_pages")
    private int totalPages;
    @JsonProperty("total_results")
    private int totalResults;
}
