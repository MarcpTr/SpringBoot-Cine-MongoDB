package com.marcptr.cine.dto.response;

import java.time.LocalDate;
import java.util.List;

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
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieResponse {

    private Long id;

    private Boolean adult;

    @JsonProperty("backdrop_path")
    private String backdropPath;

    @JsonProperty("belongs_to_collection")
    private CollectionInfo belongsToCollection;

    private Long budget;

    private List<Genre> genres;

    private String homepage;

    @JsonProperty("imdb_id")
    private String imdbId;

    @JsonProperty("origin_country")
    private List<String> originCountry;

    @JsonProperty("original_language")
    private String originalLanguage;

    @JsonProperty("original_title")
    private String originalTitle;

    private String overview;

    private Double popularity;

    @JsonProperty("poster_path")
    private String posterPath;

    @JsonProperty("production_companies")
    private List<ProductionCompany> productionCompanies;

    @JsonProperty("production_countries")
    private List<ProductionCountry> productionCountries;

    @JsonProperty("release_date")
    private LocalDate releaseDate;

    private Long revenue;

    private Integer runtime;

    private Boolean softcore;

    @JsonProperty("spoken_languages")
    private List<SpokenLanguage> spokenLanguages;

    private String status;

    private String tagline;

    private String title;

    private Boolean video;

    @JsonProperty("vote_average")
    private Double voteAverage;

    @JsonProperty("vote_count")
    private Integer voteCount;

    private Videos videos;

    private Credits credits;

}