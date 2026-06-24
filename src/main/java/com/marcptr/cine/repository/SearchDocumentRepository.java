package com.marcptr.cine.repository;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.marcptr.cine.document.SearchDocument;

public interface SearchDocumentRepository extends MongoRepository<SearchDocument, String> {
 Optional<SearchDocument> findById(String id);
}
