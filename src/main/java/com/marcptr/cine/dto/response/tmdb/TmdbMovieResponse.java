package com.marcptr.cine.dto.response.tmdb;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Map;

@Data
public class TmdbMovieResponse {

    private Boolean adult;

    @JsonProperty("backdrop_path")
    private String backdropPath;

    @JsonProperty("belongs_to_collection")
    private CollectionInfo belongsToCollection;

    private Long budget;

    private List<Genre> genres;

    private String homepage;

    private Long id;

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

    private Images images;

    private Keywords keywords;

    private RecommendationResponse recommendations;

    private RecommendationResponse similar;

    @JsonProperty("release_dates")
    private ReleaseDates releaseDates;

    @JsonProperty("watch/providers")
    private WatchProviders watchProviders;

    // =========================
    // COLLECTION
    // =========================

    @Data
    public static class CollectionInfo {

        private Long id;
        private String name;

        @JsonProperty("poster_path")
        private String posterPath;

        @JsonProperty("backdrop_path")
        private String backdropPath;
    }

    // =========================
    // GENRE
    // =========================

    @Data
    public static class Genre {

        private Long id;
        private String name;
    }

    // =========================
    // PRODUCTION COMPANY
    // =========================

    @Data
    public static class ProductionCompany {

        private Long id;

        @JsonProperty("logo_path")
        private String logoPath;

        private String name;

        @JsonProperty("origin_country")
        private String originCountry;
    }

    // =========================
    // PRODUCTION COUNTRY
    // =========================

    @Data
    public static class ProductionCountry {

        @JsonProperty("iso_3166_1")
        private String iso31661;

        private String name;
    }

    // =========================
    // SPOKEN LANGUAGE
    // =========================

    @Data
    public static class SpokenLanguage {

        @JsonProperty("english_name")
        private String englishName;

        @JsonProperty("iso_639_1")
        private String iso6391;

        private String name;
    }

    // =========================
    // VIDEOS
    // =========================

    @Data
    public static class Videos {

        private List<VideoResult> results;
    }

    @Data
    public static class VideoResult {

        @JsonProperty("iso_639_1")
        private String iso6391;

        @JsonProperty("iso_3166_1")
        private String iso31661;

        private String name;
        private String key;
        private String site;
        private Integer size;
        private String type;
        private Boolean official;
        private String id;

        @JsonProperty("published_at")
        private OffsetDateTime publishedAt;
    }

    // =========================
    // CREDITS
    // =========================

    @Data
    public static class Credits {

        private List<Cast> cast;
        private List<Crew> crew;
    }

    @Data
    public static class Cast {

        private Boolean adult;
        private Integer gender;
        private Long id;

        @JsonProperty("known_for_department")
        private String knownForDepartment;

        private String name;

        @JsonProperty("original_name")
        private String originalName;

        private Double popularity;

        @JsonProperty("profile_path")
        private String profilePath;

        @JsonProperty("cast_id")
        private Integer castId;

        private String character;

        @JsonProperty("credit_id")
        private String creditId;

        private Integer order;
    }

    @Data
    public static class Crew {

        private Boolean adult;
        private Integer gender;
        private Long id;

        @JsonProperty("known_for_department")
        private String knownForDepartment;

        private String name;

        @JsonProperty("original_name")
        private String originalName;

        private Double popularity;

        @JsonProperty("profile_path")
        private String profilePath;

        @JsonProperty("credit_id")
        private String creditId;

        private String department;
        private String job;
    }

    // =========================
    // IMAGES
    // =========================

    @Data
    public static class Images {

        private List<ImageItem> backdrops;
        private List<ImageItem> logos;
        private List<ImageItem> posters;
    }

    @Data
    public static class ImageItem {

        @JsonProperty("aspect_ratio")
        private Double aspectRatio;

        private Integer height;

        @JsonProperty("iso_3166_1")
        private String iso31661;

        @JsonProperty("iso_639_1")
        private String iso6391;

        @JsonProperty("file_path")
        private String filePath;

        @JsonProperty("vote_average")
        private Double voteAverage;

        @JsonProperty("vote_count")
        private Integer voteCount;

        private Integer width;
    }

    // =========================
    // KEYWORDS
    // =========================

    @Data
    public static class Keywords {

        private List<Keyword> keywords;
    }

    @Data
    public static class Keyword {

        private Long id;
        private String name;
    }

    // =========================
    // RECOMMENDATIONS / SIMILAR
    // =========================

    @Data
    public static class RecommendationResponse {

        private Integer page;
        private List<MovieSummary> results;

        @JsonProperty("total_pages")
        private Integer totalPages;

        @JsonProperty("total_results")
        private Integer totalResults;
    }

    @Data
    public static class MovieSummary {

        private Boolean adult;

        @JsonProperty("backdrop_path")
        private String backdropPath;

        private Long id;
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

        @JsonProperty("genre_ids")
        private List<Integer> genreIds;

        private Double popularity;

        @JsonProperty("release_date")
        private LocalDate releaseDate;

        private Boolean softcore;
        private Boolean video;

        @JsonProperty("vote_average")
        private Double voteAverage;

        @JsonProperty("vote_count")
        private Integer voteCount;
    }

    // =========================
    // RELEASE DATES
    // =========================

    @Data
    public static class ReleaseDates {

        private List<CountryReleaseDates> results;
    }

    @Data
    public static class CountryReleaseDates {

        @JsonProperty("iso_3166_1")
        private String iso31661;

        @JsonProperty("release_dates")
        private List<ReleaseDateItem> releaseDates;
    }

    @Data
    public static class ReleaseDateItem {

        private String certification;
        private List<String> descriptors;

        @JsonProperty("iso_639_1")
        private String iso6391;

        private String note;

        @JsonProperty("release_date")
        private OffsetDateTime releaseDate;

        private Integer type;
    }

    // =========================
    // WATCH PROVIDERS
    // =========================

    @Data
    public static class WatchProviders {

        private Map<String, CountryProvider> results;
    }

    @Data
    public static class CountryProvider {

        private String link;

        private List<ProviderItem> rent;
        private List<ProviderItem> buy;
        private List<ProviderItem> flatrate;
    }

    @Data
    public static class ProviderItem {

        @JsonProperty("logo_path")
        private String logoPath;

        @JsonProperty("provider_id")
        private Integer providerId;

        @JsonProperty("provider_name")
        private String providerName;

        @JsonProperty("display_priority")
        private Integer displayPriority;
    }
}