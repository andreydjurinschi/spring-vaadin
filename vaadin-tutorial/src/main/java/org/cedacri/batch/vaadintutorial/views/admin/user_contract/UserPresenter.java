package org.cedacri.batch.vaadintutorial.views.admin.user_contract;

import org.cedacri.batch.vaadintutorial.core.models.entity.User;
import org.cedacri.batch.vaadintutorial.core.models.service.AuthService;
import org.cedacri.batch.vaadintutorial.core.models.service.user.UserService;

public class UserPresenter {

    private final UserViewContract userViewContract;
    private final UserService userService;

    public UserPresenter(UserViewContract userViewContract, UserService userService) {
        this.userViewContract = userViewContract;
        this.userService = userService;
    }

    public void onInit() {
        if (AuthService.checkUserIsAdmin(AuthService.getCurrentRole())) {
            userViewContract.showUsers(userService.getAll());
        } else {
            showErrorAndNavigateHome();
        }
    }

    public void onInfo(Long id) {
        if (AuthService.checkUserIsAdmin(AuthService.getCurrentRole())) {
            User user = userService.getById(id);
            userViewContract.showUser(user);
        } else {
            showErrorAndNavigateHome();
        }
    }

    public void onCreate(User user) {
        if (AuthService.checkUserIsAdmin(AuthService.getCurrentRole())) {
            userService.save(user);
            userViewContract.showUser(user);
            userViewContract.updateTableAfterCreating(userService.getAll());
        } else {
            showErrorAndNavigateHome();
        }
    }

    private void showErrorAndNavigateHome() {
        userViewContract.showError("You are not an admin");
        userViewContract.navigateHome();
    }

}
