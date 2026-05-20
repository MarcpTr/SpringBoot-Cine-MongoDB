package com.marcptr.cine.mapper;

import com.marcptr.cine.document.MovieDocument;
import com.marcptr.cine.dto.response.tmdb.TmdbMovieResponse;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MovieMapper {

    @Mapping(target = "id", source = "movieId")
    TmdbMovieResponse toDto(MovieDocument doc);

    @Mapping(target = "movieId", source = "dto.id")
    @Mapping(target = "id", expression = "java(MovieDocument.buildId(dto.getId(), lang))")
    @Mapping(target = "lang", source = "lang")
    @Mapping(target = "cachedAt", expression = "java(java.time.Instant.now())")
    MovieDocument toDocument(TmdbMovieResponse dto, String lang);
}