package com.netent.bookstore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
class Book {
    @Id
    @EqualsAndHashCode.Include
    @Column(columnDefinition = "varchar(100)")
    String isbn;
    String title, author;
    float price;
    int quantity;

    Book(String isbn) {
        this.isbn = isbn;
    }
}
