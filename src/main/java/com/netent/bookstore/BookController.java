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

    @PostMapping("/")
    ResponseEntity<?> addBook(@Valid @RequestBody Book book) {
        log.info("in add book");
        return bookService.addBook(book);
    }

    @GetMapping("/getMediaCoverage/{isbn}")
    ResponseEntity<?> findByMediaCoverage(@PathVariable String isbn) {
        return bookService.findByMediaCoverage(isbn);
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