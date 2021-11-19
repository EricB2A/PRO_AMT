package com.example.amt_demo.service;

import com.example.amt_demo.model.UserRepository;
import com.example.amt_demo.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

@Component
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {
    private UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    public CustomUserDetailsServiceImpl( UserRepository userRepository) {
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