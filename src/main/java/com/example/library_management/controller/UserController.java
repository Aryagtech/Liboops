package com.example.library_management.controller;

import com.example.library_management.model.Book;
import com.example.library_management.model.User;
import com.example.library_management.service.BookService;
import com.example.library_management.service.BorrowRequestService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {

    private final BookService bookService;
    private final BorrowRequestService borrowRequestService;

    public UserController(BookService bookService,
                          BorrowRequestService borrowRequestService) {
        this.bookService = bookService;
        this.borrowRequestService = borrowRequestService;
    }

    @GetMapping
    public String userDashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"USER".equals(user.getRole())) {
            return "redirect:/";
        }

        model.addAttribute("books", bookService.getAllBooks());
        return "user";
    }

    @GetMapping("/request/{bookId}")
    public String requestBorrow(@PathVariable Long bookId, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            borrowRequestService.createRequest(user, bookId);
        }
        return "redirect:/user";
    }

    // --- RETURN BOOK FIXED (uses requestId) ---
    @GetMapping("/return/{requestId}")
    public String returnBook(@PathVariable Long requestId, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            borrowRequestService.markAsReturned(requestId, user);
        }
        return "redirect:/user";
    }
}
