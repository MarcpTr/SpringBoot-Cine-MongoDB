package com.marcptr.cine.document;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.marcptr.cine.model.CollectionInfo;
import com.marcptr.cine.model.Credits;
import com.marcptr.cine.model.Genre;
import com.marcptr.cine.model.ProductionCompany;
import com.marcptr.cine.model.ProductionCountry;
import com.marcptr.cine.model.SpokenLanguage;
import com.marcptr.cine.model.Videos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Document(collection = "movies")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieDocument {

    @Id
    private String id; // {movieId}_{lang}

    @Indexed
    private Long movieId;

    @Indexed
    private String lang;
    @JsonProperty("updated_at")
    private Instant updatedAt;

    @Indexed(expireAfter = "30d")
    @JsonProperty("last_accessed_at")
    private Instant lastAccessedAt;

    private boolean notFound;

    private Boolean adult;
    private String backdropPath;

    private CollectionInfo belongsToCollection;

    private Long budget;
    private List<Genre> genres;

    private String homepage;

    private String imdbId;

    private List<String> originCountry;

    private String originalLanguage;
    private String originalTitle;

    private String overview;
    private Double popularity;

    private String posterPath;

    private List<ProductionCompany> productionCompanies;
    private List<ProductionCountry> productionCountries;

    private LocalDate releaseDate;

    private Long revenue;
    private Integer runtime;

    private Boolean softcore;

    private List<SpokenLanguage> spokenLanguages;

    private String status;
    private String tagline;
    private String title;

    private Boolean video;

    private Double voteAverage;
    private Integer voteCount;

    private Videos videos;
    private Credits credits;

    public static String buildId(Long movieId, String lang) {
        return movieId + "_" + lang;
    }
}