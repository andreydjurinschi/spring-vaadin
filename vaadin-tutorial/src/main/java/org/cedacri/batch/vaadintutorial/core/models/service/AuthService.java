package org.cedacri.batch.vaadintutorial.core.models.service;

import com.vaadin.flow.server.VaadinSession;
import jakarta.security.auth.message.AuthException;
import org.cedacri.batch.vaadintutorial.core.models.entity.Role;
import org.cedacri.batch.vaadintutorial.core.models.entity.User;
import org.cedacri.batch.vaadintutorial.core.models.repo.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void authenticate(String login, String password) throws AuthException {

        User user = userRepository.findByLogin(login);

        if (user == null || !user.checkPassword(password)) {
            throw new AuthException("Invalid login or password");
        }

        VaadinSession.getCurrent().setAttribute(User.class, user);
    }

    public void register(String login, String email, String fullName, String password){
        User user = userRepository.findByLogin(login);
        if(user != null){
            throw new IllegalStateException("this user already exists");
        }

        user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setEmail(email);
        user.setFullName(fullName);
        user.setRole(Role.VISITOR);
        userRepository.save(user);
    }


    public static User getCurrentUser() {
        return VaadinSession.getCurrent().getAttribute(User.class);
    }

    public static Role  getCurrentRole() {
        User currentUser = getCurrentUser();
        if(currentUser == null) {
            return null;
        }
        return currentUser.getRole();
    }

    public static boolean checkUserIsAdmin(Role role){
        if(role == Role.ADMIN){
            return true;
        }
        return false;
    }
    public static boolean checkUserIsCreator(Role role){
        if(role == Role.CREATOR){
            return true;
        }
        return false;
    }

    public static boolean isAuthenticated() {
        return getCurrentUser() != null;
    }

    public static void logout() {
        VaadinSession.getCurrent().close();
    }
}
