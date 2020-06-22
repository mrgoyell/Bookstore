package com.netent.bookstore;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "books", path = "books")
public interface BookRepository extends JpaRepository<Book, String> {

    @RestResource(exported = false)
    @Query(value = "select b from Book b where b.title like %:search% or b.isbn like :isbn or b.author like %:search%")
    List<Book> findByIsbnOrTitleLikeOrAuthorLike(String search, String isbn);

}
