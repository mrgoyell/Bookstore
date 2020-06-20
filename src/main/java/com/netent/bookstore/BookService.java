package com.netent.bookstore;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
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
        if (book == null)
            return ResponseEntity.notFound().build();
        try {
            responseEntity = restTemplate.getForEntity(mediaCoverageUrl, ArrayNode.class);
        } catch (RestClientException e) {
            return ResponseEntity.noContent().build();
        }
        List<String> result = new ArrayList<>();
        ArrayNode arrayNode = (ArrayNode) responseEntity.getBody();
        for (JsonNode node : arrayNode) {
            String title = node.get("title").asText(), body = node.get("body").asText();
            if (title.contains(book.getTitle()) || body.contains(book.getTitle()))
                result.add(title);
        }
        if (result.size() == 0)
            return ResponseEntity.notFound().build();

        return ResponseEntity.accepted().body(result);
    }
}
