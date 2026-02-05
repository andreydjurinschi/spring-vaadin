package org.cedacri.batch.vaadintutorial.core.models.service.user;

import org.cedacri.batch.vaadintutorial.core.models.entity.Post;
import org.cedacri.batch.vaadintutorial.core.models.entity.User;
import org.cedacri.batch.vaadintutorial.core.models.repo.PostRepository;
import org.cedacri.batch.vaadintutorial.core.models.repo.UserRepository;
import org.cedacri.batch.vaadintutorial.core.models.validator.UserValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    public UserServiceImpl(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
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
        UserValidator.validate(user);
        userRepository.save(user);
    }

    @Override
    public void updateUser(Long id, User updatedUser) {
        User user = getUser(id);
        UserValidator.validate(updatedUser);

        user.setLogin(updatedUser.getLogin());
        user.setEmail(updatedUser.getEmail());
        user.setFullName(updatedUser.getFullName());
        user.setRole(updatedUser.getRole());
    }

    @Override
    public void deleteUser(Long id) {
        User user = getUser(id);
        List<Post> posts = postRepository.findAll();
        for (Post post : posts) {
            post.getLikedBy().remove(user);
        }
        userRepository.delete(user);
    }

    private User getUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NoSuchElementException(
                "User with id " + id + " does not exist"));
        return user;
    }


}
