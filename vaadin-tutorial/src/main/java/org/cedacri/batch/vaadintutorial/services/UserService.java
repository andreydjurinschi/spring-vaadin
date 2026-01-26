package org.cedacri.batch.vaadintutorial.services;

import org.cedacri.batch.vaadintutorial.repositoties.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

}
