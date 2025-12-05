package com.example.library_management.model;

import jakarta.persistence.*;

@Entity
@Table(name = "borrow_requests")
public class BorrowRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status = "PENDING";

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;  // link to user

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;  // link to book

    public BorrowRequest() {}

    // Constructor to create a request
    public BorrowRequest(User user, Book book) {
        this.user = user;
        this.book = book;
        this.status = "PENDING";
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Book getBook() { return book; }
    public void setBook(Book book) { this.book = book; }
}
