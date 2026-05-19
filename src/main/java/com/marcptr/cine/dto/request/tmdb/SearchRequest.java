package com.marcptr.cine.dto.request.tmdb;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchRequest {
    @NotBlank
    private String query;

    private Boolean includeAdult = false;

    private String primaryReleaseYear;

    @Min(1)
    private Integer page = 1;

    private String region;

    private String year;
}
