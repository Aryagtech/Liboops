package com.example.library_management.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User extends Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String role = "USER"; // default role

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<BorrowRequest> borrowRequests;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public List<BorrowRequest> getBorrowRequests() { return borrowRequests; }
    public void setBorrowRequests(List<BorrowRequest> borrowRequests) { this.borrowRequests = borrowRequests; }
}
