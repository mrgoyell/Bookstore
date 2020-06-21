package com.netent.bookstore;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class BookService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    RestTemplate restTemplate;

    @Value("${media-coverage-api}")
    String mediaCoverageUrl;


    public ResponseEntity<?> findByMediaCoverage(String isbn) {
        ResponseEntity<?> responseEntity;
        Book book = bookRepository.findById(isbn).orElse(null);
        ArrayNode arrayNode;
        if (book == null)
            return ResponseEntity.notFound().build();
        try {
            responseEntity = restTemplate.getForEntity(mediaCoverageUrl, ArrayNode.class);
            arrayNode = (ArrayNode) responseEntity.getBody();
            if (arrayNode == null)
                throw new NullPointerException();
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
        List<String> result = new ArrayList<>();
        for (JsonNode node : arrayNode) {
            String title = node.get("title").asText(), body = node.get("body").asText();
            if (title.contains(book.getTitle()) || body.contains(book.getTitle()))
                result.add(title);
        }
        if (result.size() == 0) {
            log.info("in no content");
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok().body(result);
    }

    public ResponseEntity<?> addBook(Book book) {
        Book finalBook = bookRepository.findById(book.getIsbn()).orElse(null);
        ResponseEntity<?> responseEntity;
        if (finalBook != null) {
            finalBook.setQuantity(finalBook.getQuantity() + 1);
            responseEntity = ResponseEntity.ok(finalBook);
        } else {
            finalBook = book;
            responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(finalBook);
        }
        bookRepository.save(finalBook);
        return responseEntity;
    }

    public ResponseEntity<?> buyBook(String isbn) {
        Book book = bookRepository.findById(isbn).orElse(null);
        if (book == null)
            return ResponseEntity.notFound().build();
        book.setQuantity(book.getQuantity() - 1);
        bookRepository.save(book);
        if (book.getQuantity() == 0)
            addBook(book); //in non monolithic setup to be done via rest?
        return ResponseEntity.accepted().body("Purchased Successfully");
    }
}
