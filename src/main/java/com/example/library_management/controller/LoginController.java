package com.example.library_management.controller;

import com.example.library_management.model.User;
import com.example.library_management.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    // Show login page
    @GetMapping("/")
    public String loginPage() {
        return "login"; // login.html
    }

    // Handle login form submission
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {

        User user = userService.login(username, password);
        if (user != null) {
            session.setAttribute("user", user);
            if (user.getRole().equals("ADMIN")) {
                return "redirect:/admin";
            } else {
                return "redirect:/user";
            }
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }
    }

    // Logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
