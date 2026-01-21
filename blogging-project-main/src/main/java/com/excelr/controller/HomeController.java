package com.excelr.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.excelr.entity.Post;
import com.excelr.service.PostService;

@Controller
public class HomeController {

    private final PostService service;

    public HomeController(PostService service) {
        this.service = service;
    }

    // 🏠 PUBLIC HOME PAGE
    @GetMapping("/")
    public String home(Model model) {

        List<Post> posts = service.getAllPosts();
        model.addAttribute("posts", posts);

        return "index"; // index.html
    }

    // 🔍 PUBLIC SEARCH
    @GetMapping("/search")
    public String search(
            @RequestParam("query") String query,
            Model model) {

        List<Post> posts = service.searchAllPosts(query);
        model.addAttribute("posts", posts);

        return "index"; // same page
    }
}
