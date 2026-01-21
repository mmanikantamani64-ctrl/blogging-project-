package com.excelr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excelr.entity.User;
import com.excelr.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // 🔹 REGISTER
    public User registerUser(User user) {
        return userRepository.save(user);
    }

    // 🔹 LOGIN
    public User loginUser(String email, String password) {
        User user = userRepository.findByEmail(email);

        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    // 🔹 FIND BY EMAIL (used for forgot password)
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
