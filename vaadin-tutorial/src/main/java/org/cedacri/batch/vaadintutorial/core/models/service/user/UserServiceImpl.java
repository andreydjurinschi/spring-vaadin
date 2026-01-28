package org.cedacri.batch.vaadintutorial.core.models.service.user;

import com.vaadin.copilot.shaded.commons.lang3.StringUtils;
import org.cedacri.batch.vaadintutorial.core.models.entity.User;
import org.cedacri.batch.vaadintutorial.core.models.repo.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
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

    @Override
    public void updateUser(Long id, User updatedUser) {
        User user = getUser(id);
        if(StringUtils.isNotBlank(updatedUser.getLogin())) {
            user.setLogin(updatedUser.getLogin());
        }
        if(StringUtils.isNotBlank(updatedUser.getEmail())){
            user.setEmail(updatedUser.getEmail());
        }
        if(StringUtils.isNotBlank(updatedUser.getFullName())){
            user.setFullName(updatedUser.getFullName());
        }
        if(updatedUser.getRole() !=null){
            user.setRole(updatedUser.getRole());
        }
    }

    @Override
    public void deleteUser(Long id) {
        User user = getUser(id);
        userRepository.delete(user);
    }

    private User getUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NoSuchElementException(
                "User with id " + id + " does not exist"));
        return user;
    }
}
