package com.excelr.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.excelr.entity.User;
import com.excelr.service.UserService;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    // REGISTER PAGE
    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    // REGISTER SUBMIT
    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user) {
        userService.registerUser(user);
        return "redirect:/login";
    }

    // LOGIN PAGE
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    // LOGIN SUBMIT
    @PostMapping("/login")
    public String loginUser(
            @RequestParam String email,
            @RequestParam String password,
            HttpSession session) {

        User user = userService.loginUser(email, password);

        if (user != null) {
            session.setAttribute("loggedUser", user);
            return "redirect:/dashboard";
        }

        return "login"; // login failed
    }

    // ✅ DASHBOARD PAGE (SEPARATE METHOD)
     
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();   // 🔥 session clear
        return "redirect:/login";
    }
    @GetMapping("/forgot-password")
    public String forgotPasswordPage() {
        return "forgot_password";
    }

    @PostMapping("/forgot-password")
    public String resetPassword(
            @RequestParam String email,
            @RequestParam String newPassword,
            Model model) {

        User user = userService.findByEmail(email);

        if (user == null) {
            model.addAttribute("error", "Email not found");
            return "forgot_password";
        }

        user.setPassword(newPassword); // later you can encrypt it
        userService.registerUser(user);

        return "redirect:/login";
    }


}
