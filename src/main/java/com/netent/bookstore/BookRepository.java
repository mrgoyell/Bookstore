package com.netent.bookstore;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, String> {

    @Query(value = "select b from Book b where b.title like %:search% or b.isbn like :isbn or b.author like %:search%")
    List<Book> findByIsbnOrTitleLikeOrAuthorLike(String search, String isbn);

}
