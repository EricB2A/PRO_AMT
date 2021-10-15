package com.example.amt_demo.seed;

import com.example.amt_demo.model.Role;
import com.example.amt_demo.model.RoleRepository;
import com.example.amt_demo.model.User;
import com.example.amt_demo.model.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;

@Component
public class Seeder implements CommandLineRunner {

    final private UserRepository userRepository;
    final private RoleRepository roleRepository;
    final private Logger logger;

    @Autowired
    public Seeder(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        logger = LoggerFactory.getLogger(Seeder.class);
    }

    @Override
    public void run(String... args) throws Exception {
        loadUserData();
    }

    private void loadUserData() {
        logger.debug("Init data...");
        if (userRepository.count() == 0 && roleRepository.count() == 0 ) {
            logger.debug("Init user data...");
            Role adminRole = new Role();
            adminRole.setName("admin");
            Role userRole = new Role();
            userRole.setName("user");
            roleRepository.saveAll(List.of(adminRole,userRole));

            User admin = new User();
            admin.setUsername("root");
            admin.setEmail("admin@silkyroad.ch");
            admin.setFirstname("admin");
            admin.setLastname("admin");
            admin.setPassword(new BCryptPasswordEncoder().encode("password"));
            admin.setRoles(new HashSet<Role>(List.of(adminRole)));
            userRepository.save(admin);

            logger.debug("User data ready");
        }
    }
}