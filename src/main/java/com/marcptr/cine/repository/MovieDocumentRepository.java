package com.marcptr.cine.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.marcptr.cine.document.MovieDocument;

public interface MovieDocumentRepository extends MongoRepository<MovieDocument, String> {

    Optional<MovieDocument> findByMovieIdAndLang(
            Long movieId,
            String language);
}