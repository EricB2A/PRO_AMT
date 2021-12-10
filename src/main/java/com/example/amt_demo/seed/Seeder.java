/**
 * @team AMT - Silkyroad
 * @author Bousbaa Eric, Fusi Noah, Goujgali Ilias, Maillefer Dalia, Teofanovic Stefan
 * @file Seeder.java
 *
 * @brief Class creating basic datas for User
 */

package com.example.amt_demo.seed;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class Seeder implements CommandLineRunner {

    final private Logger logger;
    final private int adminId;
    final private String adminUsername;

    /**
     *
     * @param adminId
     * @param adminUsername
     */
    @Autowired
    public Seeder(
                  @Value("${com.example.amt_demo.config.admin.id}") String adminId,
                  @Value("${com.example.amt_demo.config.admin.username}") String adminUsername) {
        this.adminId = Integer.parseInt(adminId);
        this.adminUsername = adminUsername;
        logger = LoggerFactory.getLogger(Seeder.class);
    }

    /**
     *
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
        createAdmin();
    }

    /**
     *
     */
    public void createAdmin() {
        logger.debug("Init data...");

        logger.debug("Init data done ! ...");
    }
}