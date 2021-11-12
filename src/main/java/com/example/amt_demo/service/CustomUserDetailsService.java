package com.example.amt_demo.service;

import com.example.amt_demo.config.SpringSecurityConfig;
import com.example.amt_demo.exception.EmailNotFoundException;
import com.example.amt_demo.model.UserRepository;
import com.example.amt_demo.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final WebApplicationContext applicationContext;
    private UserRepository userRepository;

    public CustomUserDetailsService(WebApplicationContext applicationContext, UserRepository userRepository) {
        this.applicationContext = applicationContext;
        this.userRepository = userRepository;

    }

    @Override
    public UserDetails loadUserByUsername(String email){
        User user = userRepository.findByEmail(email);
        if (user == null) {
            //TODO: Il faut catch ça...
            throw new EmailNotFoundException(email);
        }
        return new CustomUserDetails(user);
    }
}