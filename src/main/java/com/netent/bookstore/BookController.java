package com.netent.bookstore;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    BookService bookService;

    @PostMapping
    ResponseEntity<?> addBook(@Valid @RequestBody Book book) {
        return bookService.addBook(book);
    }

    @GetMapping
    ResponseEntity<?> findByIsbnOrTitleLikeOrAuthorLike(@RequestParam String search) {
        return bookService.findByIsbnOrTitleLikeOrAuthorLike(search);
    }

    @GetMapping("/{isbn}/getMediaCoverage")
    ResponseEntity<?> findByMediaCoverage(@PathVariable String isbn) {
        return bookService.getMediaCoverage(isbn);
    }

    @GetMapping("/{isbn}/buy")
    ResponseEntity<?> buyBook(@PathVariable String isbn) {
        return bookService.buyBook(isbn);
    }

    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<Exception> handleAllExceptions(RuntimeException ex) {
        return new ResponseEntity<>(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
