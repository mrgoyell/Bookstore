package com.netent.bookstore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
class Book {
    @Id
    @EqualsAndHashCode.Include
    @Column(columnDefinition = "varchar(100)")
    @NotBlank(message = "ISBN is mandatory")
    private String isbn;

    @NotBlank(message = "Title is mandatory")
    private String title;

    @NotBlank(message = "Author is mandatory")
    private String author;

    @NotNull(message = "No price entered, we don't sell for free :)")
    private Float price;
    private int quantity = 1;

    Book(String isbn) {
        this.isbn = isbn;
    }

    public Book(String isbn, String title, String author, Float price) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.price = price;
    }
}
