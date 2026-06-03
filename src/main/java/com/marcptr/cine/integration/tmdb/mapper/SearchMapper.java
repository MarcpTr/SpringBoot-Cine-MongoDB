package com.marcptr.cine.integration.tmdb.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.marcptr.cine.document.SearchDocument;
import com.marcptr.cine.dto.response.SearchResponse;
import com.marcptr.cine.integration.tmdb.dto.TmdbSearchMovieResponse;

@Mapper(componentModel = "spring")
public interface SearchMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "results", source = "dto.results")
    SearchDocument toDocument(TmdbSearchMovieResponse dto, String id, String query, String lang, Integer page);
   
    SearchResponse toDto(SearchDocument doc);
}
