package com.excelr.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.excelr.entity.Post;
import com.excelr.entity.User;

public interface PostRepository extends JpaRepository<Post, Long> {

    // user-wise posts
    List<Post> findByUser(User user);

    // user-wise search
    List<Post> findByUserAndTitleContainingIgnoreCase(User user, String keyword);

    // public search (home page)
    List<Post> findByTitleContainingIgnoreCase(String keyword);
}
