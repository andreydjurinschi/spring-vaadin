package org.cedacri.batch.vaadintutorial.core.utils;

import org.cedacri.batch.vaadintutorial.core.models.Role;
import org.cedacri.batch.vaadintutorial.core.models.User;
import org.cedacri.batch.vaadintutorial.repositoties.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;

    public DataInitializer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) {
        if (userRepository.findByLogin("admin") == null) {
            User user = new User(
                    "Admin User",
                    "admin@test.com",
                    "admin",
                    "admin123",
                    Role.ADMIN
            );
            userRepository.save(user);
        }
    }
}

