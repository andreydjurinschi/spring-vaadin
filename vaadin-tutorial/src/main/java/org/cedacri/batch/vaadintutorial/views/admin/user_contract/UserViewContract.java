package org.cedacri.batch.vaadintutorial.views.admin.user_contract;

import org.cedacri.batch.vaadintutorial.core.models.entity.User;

import java.util.List;

public interface UserViewContract {
    void showUsers(List<User> users);
    void showUser(User user);

    void createUser();

    void deleteUser(Long id, User user);

    void updateUser(Long id, User user);

    void updateTableAfterChanging(List<User> users);

    void showError(String message);
    void navigateHome();
}
