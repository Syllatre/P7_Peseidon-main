package com.poseidon.webapp;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class PoseidonWebAppApplication {

    private static final Logger logger = LogManager.getLogger(PoseidonWebAppApplication.class);

    public static void main(String[] args) {
        logger.info("Initializing Poseidon webapp");
        SpringApplication.run(PoseidonWebAppApplication.class, args);
    }
}
