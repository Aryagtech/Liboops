package com.example.library_management.controller;

import com.example.library_management.model.User;
import com.example.library_management.service.BookService;
import com.example.library_management.service.BorrowRequestService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final BookService bookService;
    private final BorrowRequestService borrowRequestService;

    public AdminController(BookService bookService,
                           BorrowRequestService borrowRequestService) {
        this.bookService = bookService;
        this.borrowRequestService = borrowRequestService;
    }

    @GetMapping
    public String adminDashboard(HttpSession session, Model model,
                                 @RequestParam(required = false) String error) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"ADMIN".equals(user.getRole())) {
            return "redirect:/";
        }

        model.addAttribute("books", bookService.getAllBooks());
        model.addAttribute("requests", borrowRequestService.getAllRequests());
        if (error != null) {
            model.addAttribute("error", error);
        }

        return "admin";
    }

    @PostMapping("/add")
    public String addBook(@RequestParam String title,
                          @RequestParam String author) {
        bookService.addBook(new com.example.library_management.model.Book(title, author));
        return "redirect:/admin";
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        try {
            bookService.deleteBook(id);
        } catch (RuntimeException e) {
            return "redirect:/admin?error=" + e.getMessage();
        }
        return "redirect:/admin";
    }

    @GetMapping("/approve/{id}")
    public String approveRequest(@PathVariable Long id) {
        borrowRequestService.approveRequest(id);
        return "redirect:/admin";
    }

    @GetMapping("/reject/{id}")
    public String rejectRequest(@PathVariable Long id) {
        borrowRequestService.rejectRequest(id);
        return "redirect:/admin";
    }

    @GetMapping("/mark-returned/{id}")
    public String markAsReturned(@PathVariable Long id) {
        // Call the admin version of markAsReturned
        borrowRequestService.markAsReturned(id);
        return "redirect:/admin";
    }

    @GetMapping("/set-available/{id}")
    public String setBookAvailable(@PathVariable Long id) {
        bookService.returnBook(id);
        return "redirect:/admin";
    }
}
