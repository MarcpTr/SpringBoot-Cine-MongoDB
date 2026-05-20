package com.marcptr.cine.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
    @Data

public class CollectionInfo {
  private Long id;
        private String name;

        @JsonProperty("poster_path")
        private String posterPath;

        @JsonProperty("backdrop_path")
        private String backdropPath;
}
