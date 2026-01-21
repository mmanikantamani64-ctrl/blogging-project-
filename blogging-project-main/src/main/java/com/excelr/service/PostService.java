package com.excelr.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.excelr.entity.Post;
import com.excelr.entity.User;
import com.excelr.repository.PostRepository;

@Service
public class PostService {

    private final PostRepository repo;

    public PostService(PostRepository repo) {
        this.repo = repo;
    }

    // Save / Update
    public Post savePost(Post post) {
        return repo.save(post);
    }

    // Public posts (home page)
    public List<Post> getAllPosts() {
        return repo.findAll();
    }

    // Get post by id
    public Post getPostById(Long id) {
        return repo.findById(id).orElse(null);
    }

    // Delete
    public void deletePost(Long id) {
        repo.deleteById(id);
    }

    // 🔥 USER-WISE POSTS (dashboard)
    public List<Post> getPostsByUser(User user) {
        return repo.findByUser(user);
    }

    // 🔍 USER-WISE SEARCH (dashboard)
    public List<Post> searchPosts(User user, String keyword) {
        return repo.findByUserAndTitleContainingIgnoreCase(user, keyword);
    }

    // 🔍 PUBLIC SEARCH (home page)
    public List<Post> searchAllPosts(String keyword) {
        return repo.findByTitleContainingIgnoreCase(keyword);
    }
}
