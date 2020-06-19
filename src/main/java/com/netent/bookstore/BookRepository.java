package com.netent.bookstore;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "books", path = "books")
public interface BookRepository extends JpaRepository<Book, String> {

    @RestResource(path = "/byParams")
    @Query(value = "select b from Book b where b.isbn = ?1 or b.title like %?1% or b.author like %?1%")
    List<Book> findByIsbnOrTitleLikeOrAuthorLike(String search);
}
