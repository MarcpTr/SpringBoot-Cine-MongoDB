package com.marcptr.cine.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
    public class Cast {

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