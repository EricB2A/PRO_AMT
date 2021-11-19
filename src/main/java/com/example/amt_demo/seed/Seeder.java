package com.example.amt_demo.seed;
import com.example.amt_demo.model.RoleRepository;
import com.example.amt_demo.model.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


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
        // eg:
        //loadMyData();
    }
    // eg:
    /*public void loadMyData(){ ... insert thing into the db}*/

}