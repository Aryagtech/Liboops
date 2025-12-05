package com.example.library_management.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String author;
    private String isbn;

    private boolean available = true; // default to true

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<BorrowRequest> borrowRequests;

    // Default constructor
    public Book() {
    }

    // Constructor with title and author
    public Book(String title, String author) {
        this.title = title;
        this.author = author;
        this.available = true; // new book is available by default
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }

    public List<BorrowRequest> getBorrowRequests() { return borrowRequests; }
    public void setBorrowRequests(List<BorrowRequest> borrowRequests) { this.borrowRequests = borrowRequests; }
}
