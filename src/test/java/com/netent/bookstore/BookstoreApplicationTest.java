package com.netent.bookstore;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
class BookstoreApplicationTest {

    @Autowired
    BookService bookService;
    List<Book> bookList;
    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    public void initBooks() {
        bookList = Arrays.asList(
                new Book("isbn1", "title1", "author1", 3.2f, 4),
                new Book("isbn2", "reprehenderit", "author2", 41.3f, 2),
                new Book("isbn3", "title3", "author3", 12.2f, 1)
        );
        bookRepository.saveAll(bookList);
    }

    @Test
    void contextLoads() {
    }

    @Test
    void check_addBook() {
        //adding New Book
        Book book = new Book("isbn4", "title4", "author4", 2.3f);
        ResponseEntity<?> responseEntity = bookService.addBook(book);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(1, bookRepository.findById(book.getIsbn()).get().getQuantity());
        //adding existing book
        book = bookList.get(1);
        responseEntity = bookService.addBook(book);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(book.getQuantity() + 1, bookRepository.findById(book.getIsbn()).get().getQuantity());
    }

    @Test
    void check_buyBook() {
        //Bought book with quantity >1
        ResponseEntity<?> responseEntity = bookService.buyBook("isbn1");
        ResponseEntity<?> finalResponseEntity = responseEntity;
        assertAll(
                () -> assertEquals(3, bookRepository.findById("isbn1").get().getQuantity()),
                () -> assertEquals(HttpStatus.ACCEPTED, finalResponseEntity.getStatusCode())
        );

        //Buying non existing book
        responseEntity = bookService.buyBook("invalidIsbn");
        assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
        //Buying book with quantity == 1
        responseEntity = bookService.buyBook("isbn3");

        ResponseEntity finalResponseEntity1 = responseEntity;
        assertAll("Response:",
                () -> assertEquals(1, bookRepository.findById("isbn3").get().getQuantity()),
                () -> assertEquals(HttpStatus.ACCEPTED, finalResponseEntity1.getStatusCode())
        );
    }

    @Test
    void check_getMediaCoverage() {
        //depends on the data present in the TypiCode api
        ResponseEntity<?> responseEntity = bookService.getMediaCoverage("isbn2");
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        //depends on the data present in the TypiCode api
        responseEntity = bookService.getMediaCoverage("isbn1");
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());

        responseEntity = bookService.getMediaCoverage("dewd");
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

}
