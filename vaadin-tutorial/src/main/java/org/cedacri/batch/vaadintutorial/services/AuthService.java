package org.cedacri.batch.vaadintutorial.services;

import com.vaadin.flow.server.VaadinSession;
import com.vaadin.frontendtools.internal.commons.codec.digest.DigestUtils;
import jakarta.security.auth.message.AuthException;
import org.cedacri.batch.vaadintutorial.core.models.User;
import org.cedacri.batch.vaadintutorial.repositoties.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void authenticate(String login, String password) throws AuthException {

        System.out.println("LOGIN = [" + login + "]");
        System.out.println("PASSWORD = [" + password + "]");

        User user = userRepository.findByLogin(login);

        System.out.println("USER FROM DB = " + user);

        if (user == null || !user.checkPassword(password)) {
            throw new AuthException("Invalid login or password");
        }

        VaadinSession.getCurrent().setAttribute(User.class, user);
    }


    public static User getCurrentUser() {
        return VaadinSession.getCurrent().getAttribute(User.class);
    }

    public static boolean isAuthenticated() {
        return getCurrentUser() != null;
    }

    public static void logout() {
        VaadinSession.getCurrent().close();
    }
}
