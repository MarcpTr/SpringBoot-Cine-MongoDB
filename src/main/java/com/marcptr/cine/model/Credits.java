package com.marcptr.cine.model;

import java.util.List;

import lombok.Data;

@Data
    public class Credits {
        private List<Cast> cast;
        private List<Crew> crew;
    }