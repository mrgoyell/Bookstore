package com.netent.bookstore;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class BookRepositoryTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private BookRepository bookRepository;

    @Test
    void injectedComponentsAreNotNull() {
        assertNotNull(jdbcTemplate);
        assertNotNull(entityManager);
        assertNotNull(bookRepository);
    }

/*
    @Test
    void inBuiltUsedMethods() {
        assertEquals(3, bookRepository.findAll().size(), "findAll");
        assertEquals(new Book("dwe", "tesg", "erfre", 2f, 3), bookRepository.findById("dwe").orElse(null), "findById:");
    }
*/

    @Test
    void findByIsbnOrTitleLikeOrAuthorLike() {
        List<Book> books = bookRepository.findByIsbnOrTitleLikeOrAuthorLike("ew");
        Book book = new Book("greg"), book1 = new Book("ywef");
        String messageMismatchValue = "wrong entries";
        assertEquals(1, books.size(), "size mismatch");
        assertTrue(books.contains(book1), messageMismatchValue);

        books = bookRepository.findByIsbnOrTitleLikeOrAuthorLike("klddkssf");
        assertEquals(books.size(), 0);
    }

}
