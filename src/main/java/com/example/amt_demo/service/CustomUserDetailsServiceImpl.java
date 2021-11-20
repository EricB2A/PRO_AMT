/**
 * @team AMT - Silkyroad
 * @author Bousbaa Eric, Fusi Noah, Goujgali Ilias, Maillefer Dalia, Teofanovic Stefan
 * @file CustomUserDetailsServiceImpl.java
 *
 * @brief
 */

package com.example.amt_demo.service;

import com.example.amt_demo.model.UserRepository;
import com.example.amt_demo.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {

    private UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    /**
     *
     * @param userRepository
     */
    public CustomUserDetailsServiceImpl( UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     *
     * @param username
     * @return
     */
    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new CustomUserDetails(user);
    }

    /**
     *
     * @param userId
     * @return
     */
    @Override
    public UserDetails getUserById(int userId) {
        Optional<User> user = userRepository.findById(userId);
        return new CustomUserDetails(user.get());
    }
}