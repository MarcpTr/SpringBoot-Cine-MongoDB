package com.marcptr.cine.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.marcptr.cine.document.SearchDocument;
import java.util.List;


public interface SearchDocumentRepository extends MongoRepository<SearchDocument, String> {
 Optional<SearchDocument> findById(String id);
}
