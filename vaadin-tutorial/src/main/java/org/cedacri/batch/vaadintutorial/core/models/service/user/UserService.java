package org.cedacri.batch.vaadintutorial.core.models.service.user;

import org.cedacri.batch.vaadintutorial.core.models.entity.User;
import org.cedacri.batch.vaadintutorial.core.models.repo.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {
    List<User> getAll();
    User getById(Long id);
    void save(User user);
}
