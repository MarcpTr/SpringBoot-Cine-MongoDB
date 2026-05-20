package com.marcptr.cine.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
    @Data

public class ProductionCompany {

        private Long id;

        @JsonProperty("logo_path")
        private String logoPath;

        private String name;

        @JsonProperty("origin_country")
        private String originCountry;
}
