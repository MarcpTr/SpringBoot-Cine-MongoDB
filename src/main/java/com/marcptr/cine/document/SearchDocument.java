package com.marcptr.cine.document;

import java.time.Instant;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.marcptr.cine.dto.response.SearchResponse;
import com.marcptr.cine.model.SearchResult;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Document(collection = "searches")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchDocument {
    @Id
    private String id; // SHA-256 de {query}_{lang}_{page}
    @Indexed
    private String query;
    @Indexed
    private Integer page;
    @Indexed
    private String lang;
    private List<SearchResult> results;
    @JsonProperty("total_pages")
    private int totalPages;
    @JsonProperty("total_results")
    private int totalResults;
    @Indexed(expireAfter  = "86400s")
    @JsonProperty("cached_at")
    private Instant cachedAt;
}
