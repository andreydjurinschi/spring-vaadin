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
        if (isAdmin()) {
            userViewContract.showUsers(userService.getAll());
        } else {
            showError();
        }
    }
    public void onInfo(Long id) {
        if (isAdmin()) {
            User user = userService.getById(id);
            userViewContract.showUser(user);
        } else {
            showError();
        }
    }

    public void onCreate(User user) {
        if (isAdmin()) {
            userService.save(user);
            userViewContract.showUser(user);
            userViewContract.updateTableAfterChanging(userService.getAll());
        } else {
            showError();
        }
    }

    public void onUpdateRequest(Long id, User user){
        if(isAdmin()){
            userViewContract.updateUser(id, user);
        } else {
            showError();
        }
    }

    public void onUpdateSave(Long id, User user){
        if(isAdmin()){
            userService.updateUser(id, user);
            userViewContract.updateTableAfterChanging(userService.getAll());
        } else {
            showError();
        }
    }

    public void onDeleteRequest(Long id){
        if(isAdmin()){
            User user = userService.getById(id);
            userViewContract.deleteUser(id, user);
        }else{
            showError();
        }
    }

    public void onDeleteExecuted(Long id){
        if(isAdmin()){
            userService.deleteUser(id);
            userViewContract.updateTableAfterChanging(userService.getAll());
        }else{
            showError();
        }
    }
    private void showError() {
        userViewContract.showError("You are not an admin");
    }
    private boolean isAdmin() {
        return AuthService.checkUserIsAdmin(AuthService.getCurrentRole());
    }

}
