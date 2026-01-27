package org.cedacri.batch.vaadintutorial.views.admin.user_contract;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import org.cedacri.batch.vaadintutorial.core.models.entity.Role;
import org.cedacri.batch.vaadintutorial.core.models.entity.User;
import org.cedacri.batch.vaadintutorial.core.models.service.user.UserService;
import org.cedacri.batch.vaadintutorial.views.common.HomeView;
import org.cedacri.batch.vaadintutorial.views.main_tmpl.MainView;

import java.util.List;

@Route(value = "user", layout = MainView.class)
public class UserView extends Div implements UserViewContract {

    private final Grid<User> usersTable = new Grid<>(User.class, false);
    private final UserPresenter presenter;

    private Div infoContent = new Div();
    private Div createContent = new Div();

    private final Binder<User> binder = new Binder<>(User.class);

    private TextField fullNameField = new TextField("full name");
    private TextField loginField = new TextField("login");
    private EmailField emailField = new  EmailField("email");
    private PasswordField passwordField = new   PasswordField("password");
    private Select<Role> roleSelector = new Select<>("role");
    private Button saveButton;

    private Button createButton;
    private Button exit;

    public UserView(UserService userService) {
        presenter = new UserPresenter(this, userService);
        renderGrid();
        configureBinder();
        createButton = new Button("Create User", e -> createUser());
        add(createButton, infoContent, usersTable);
        presenter.onInit();
    }

    @Override
    public void showUsers(List<User> users) {
        usersTable.setItems(users);
    }

    @Override
    public void showUser(User user) {

        setStylesOnInfoButtonClicked();

        exit = new Button("Exit", e -> closeOnInfoExitClick());
        exit.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        infoContent.removeAll();

        TextField fullName = new TextField("Full name");
        fullName.setValue(user.getFullName());
        fullName.setReadOnly(true);

        TextField login = new TextField("Login");
        login.setValue(user.getLogin());
        login.setReadOnly(true);

        EmailField email = new EmailField("Email");
        email.setValue(user.getEmail());
        email.setReadOnly(true);

        TextField role = new TextField("Role");
        role.setValue(user.getRole() != null ? user.getRole().toString() : "");
        role.setReadOnly(true);

        infoContent.add(exit, fullName, login, email, role);
    }

    @Override
    public void createUser() {
        createContent.removeAll();
        createContent.getStyle()
                .set("min-width", "360px")
                .set("max-width", "420px")
                .set("border", "1px solid var(--lumo-contrast-20pct)")
                .set("border-radius", "8px")
                .set("padding", "12px");

        exit = new  Button("Exit", e -> closeOnInfoExitClick());

        roleSelector.setItems(Role.values());


        saveButton = new Button("Create", e -> {
            User newUser = new User();
            if (binder.writeBeanIfValid(newUser)) {
                presenter.onCreate(newUser);
            }
        });
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        createContent.add(exit, fullNameField, emailField, loginField,  passwordField, roleSelector, saveButton);
        add(createContent);
    }

    @Override
    public void updateTableAfterCreating(List<User> users) {
        binder.readBean(new User());
        usersTable.setItems(users);
    }

    @Override
    public void showError(String message) {
        Notification.show(message, 3000, Notification.Position.MIDDLE);
        navigateHome();
    }

    @Override
    public void navigateHome() {
        UI.getCurrent().navigate(HomeView.class);
    }

    private void renderGrid() {
        usersTable.addColumn(User::getId).setHeader("ID");
        usersTable.addColumn(User::getFullName).setHeader("Full name");
        usersTable.addColumn(User::getLogin).setHeader("Login");
        usersTable.addColumn(User::getEmail).setHeader("Email");
        usersTable.addColumn(User::getRole).setHeader("Role");

        usersTable.addComponentColumn(user -> {
            Button info = new Button("Info", e -> presenter.onInfo(user.getId()));
            Button delete = new Button("Delete"/*, e -> presenter.onDelete(user)*/);
            Button update = new Button("Update");
            Div actions = new Div(info, update, delete);
            actions.getStyle().set("display", "flex").set("gap", "5px");
            return actions;
        }).setHeader("Actions");
    }

    private void setStylesOnInfoButtonClicked() {
        getStyle().set("display", "flex").set("gap", "16px");
        infoContent.getStyle()
                .set("min-width", "360px")
                .set("max-width", "420px")
                .set("border", "1px solid var(--lumo-contrast-20pct)")
                .set("border-radius", "8px")
                .set("padding", "12px");
    }

    private void closeOnInfoExitClick(){
        infoContent.removeAll();
        createContent.removeAll();
        infoContent.getStyle().clear();
    }

    private void configureBinder() {
        binder.forField(fullNameField)
                .asRequired("Full name required")
                .bind(User::getFullName, User::setFullName);

        binder.forField(loginField)
                .asRequired("Login required")
                .bind(User::getLogin, User::setLogin);

        binder.forField(emailField)
                .asRequired("Email required")
                .bind(User::getEmail, User::setEmail);

        binder.forField(passwordField)
                .asRequired("Password required")
                .bind(User::getPasswordHash, User::setPasswordHash);

        binder.forField(roleSelector)
                .asRequired("Role required")
                .bind(User::getRole, User::setRole);
    }

}
