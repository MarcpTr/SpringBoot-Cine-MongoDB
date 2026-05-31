package com.marcptr.cine.dto.tmdb.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchRequest {
    @NotBlank
    private String query;
    @Min(1)
    private Integer page = 1;
}
