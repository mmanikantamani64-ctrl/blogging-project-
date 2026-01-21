package com.excelr.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.excelr.entity.Post;
import com.excelr.entity.User;
import com.excelr.service.PostService;

import jakarta.servlet.http.HttpSession;

@Controller
public class PostController {

    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    // 🟢 Create Post Page
    @GetMapping("/posts/new")
    public String newPost(Model model, HttpSession session) {

        User loggedUser = (User) session.getAttribute("loggedUser");
        if (loggedUser == null) {
            return "redirect:/login";
        }

        model.addAttribute("post", new Post());
        return "create_post";
    }

    // 🟢 Save Post
    @PostMapping("/posts")
    public String savePost(@ModelAttribute Post post, HttpSession session) {

        User loggedUser = (User) session.getAttribute("loggedUser");
        if (loggedUser == null) {
            return "redirect:/login";
        }

        post.setUser(loggedUser);   // 🔥 user-wise mapping
        service.savePost(post);

        return "redirect:/dashboard";
    }

    // 🟢 Dashboard (user-wise + search)
    @GetMapping("/dashboard")
    public String dashboard(
            @RequestParam(required = false) String keyword,
            Model model,
            HttpSession session) {

        User loggedUser = (User) session.getAttribute("loggedUser");
        if (loggedUser == null) {
            return "redirect:/login";
        }

        List<Post> posts;

        if (keyword != null && !keyword.isEmpty()) {
            posts = service.searchPosts(loggedUser, keyword);
        } else {
            posts = service.getPostsByUser(loggedUser);
        }

        model.addAttribute("posts", posts);
        model.addAttribute("user", loggedUser);

        return "dashboard";
    }

    // 🟢 Edit Post Page
    @GetMapping("/posts/edit/{id}")
    public String editPost(@PathVariable Long id, Model model, HttpSession session) {

        User loggedUser = (User) session.getAttribute("loggedUser");
        if (loggedUser == null) {
            return "redirect:/login";
        }

        Post post = service.getPostById(id);

        // 🔐 security: only own post
        if (!post.getUser().getId().equals(loggedUser.getId())) {
            return "redirect:/dashboard";
        }

        model.addAttribute("post", post);
        return "edit_post";
    }

    // 🟢 Update Post
    @PostMapping("/posts/update")
    public String updatePost(@ModelAttribute Post post, HttpSession session) {

        User loggedUser = (User) session.getAttribute("loggedUser");
        if (loggedUser == null) {
            return "redirect:/login";
        }

        post.setUser(loggedUser);
        service.savePost(post);

        return "redirect:/dashboard";
    }

    // 🟢 Delete Post
    @GetMapping("/posts/delete/{id}")
    public String deletePost(@PathVariable Long id, HttpSession session) {

        User loggedUser = (User) session.getAttribute("loggedUser");
        if (loggedUser == null) {
            return "redirect:/login";
        }

        Post post = service.getPostById(id);

        // 🔐 security: only own post
        if (post.getUser().getId().equals(loggedUser.getId())) {
            service.deletePost(id);
        }

        return "redirect:/dashboard";
    }
}
