package com.example.amt_demo.service;

import com.example.amt_demo.model.UserRepository;
import com.example.amt_demo.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

@Component
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {
    private final WebApplicationContext applicationContext;
    private UserRepository userRepository;

    public CustomUserDetailsServiceImpl(WebApplicationContext applicationContext, UserRepository userRepository) {
        this.applicationContext = applicationContext;
        this.userRepository = userRepository;

    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new CustomUserDetails(user);
    }

    @Override
    public UserDetails getUserById(int userId) {
        Optional<User> user = userRepository.findById(userId);
        return new CustomUserDetails(user.get());
    }
}