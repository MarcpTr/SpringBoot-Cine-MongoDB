package com.marcptr.cine.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrendResults {
    private boolean adult;
    @JsonProperty("backdrop_path")
    private String backdropPath;
    private long id;
    private String title;
    @JsonProperty("original_title")
    private String originalTitle;
    private String overview;
    @JsonProperty("poster_path")
    private String posterPath;
    @JsonProperty("media_type")
    private String mediaType;
    @JsonProperty("original_language")
    private String originalLanguage;
    @JsonProperty("genres_ids")
    private List<Integer> genresId;
    private float popularity;
    @JsonProperty("release_date")
    private LocalDate releaseDate;
    private boolean softcore;
    private boolean video;
    @JsonProperty("vote_average")
    private float voteAverage;
    @JsonProperty("vote_count")
    private int voteCount;
}
