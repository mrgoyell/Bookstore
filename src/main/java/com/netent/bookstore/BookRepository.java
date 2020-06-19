package com.netent.bookstore;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "books", path = "book")
public interface BookRepository extends JpaRepository<Book, Integer> {
}
