package com.netent.bookstore;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BookController.class)
@Slf4j
@ActiveProfiles("test")
public class BookControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookService bookService;

    @Test
    void check_addBook() throws Exception {
        Book book = new Book("isbn1", "test", "author", 3.2f);
        mockMvc.perform(post("/books/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isOk());

        book = new Book("", "refer", "author", 3.2f);
        mockMvc.perform(post("/books/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isBadRequest());

        book = new Book("isbn1", "", "author", 3.2f);
        mockMvc.perform(post("/books/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isBadRequest());

        book = new Book("isbn1", "wede", "", 3.2f);
        mockMvc.perform(post("/books/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isBadRequest());

        book = new Book("isbn1", "wede", "", null);
        mockMvc.perform(post("/books/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isBadRequest());

        //test for response matching & verification
    }

    @Test
    void check_getMediaCoverage() throws Exception {
        String isbn = "greg", url = "/books/{isbn}/getMediaCoverage";
        mockMvc.perform(get(url, isbn)).andExpect(status().isOk());
    }

}
