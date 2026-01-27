package org.cedacri.batch.vaadintutorial.core.models.service.user;

import org.cedacri.batch.vaadintutorial.core.models.entity.User;
import org.cedacri.batch.vaadintutorial.core.models.repo.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NoSuchElementException(
                "User with id " + id + " does not exist"));
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }
}
