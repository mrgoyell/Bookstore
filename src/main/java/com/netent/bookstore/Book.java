package com.netent.bookstore;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
class Book {
    @Id
    @EqualsAndHashCode.Include
    String isbn;
    String title, author;
    float price;
    int quantity;
}
