package com.marcptr.cine.model;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
    @Data

public class VideoResult {

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
        private Instant  publishedAt;
}
