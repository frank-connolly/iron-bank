package com.gaeltech.ironbank;

import org.slf4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

@SpringBootApplication
public class IronBankApplication {

    private final Logger log = org.slf4j.LoggerFactory.getLogger(IronBankApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(IronBankApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(WorkoutRepository repository, DataSource dataSource) {
        return args -> {
            // Initialize database with src/main/resources/data.sql
            Resource resource = new ClassPathResource("data.sql");
            ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(resource);
            databasePopulator.execute(dataSource);
            log.info("Workout table exists with record count: {}", repository.count());
        };
    }
}
