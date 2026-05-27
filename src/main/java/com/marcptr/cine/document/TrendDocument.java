package com.marcptr.cine.document;

import java.time.Instant;
import java.util.List;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.marcptr.cine.model.TrendResults;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Document(collection = "trends")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrendDocument {
    private String id;
    @Indexed
    private String lang;
    private int page;
    private List<TrendResults> results;
    private int totalPages;
    private int totalResults;
    private Instant cachedAt;

}
