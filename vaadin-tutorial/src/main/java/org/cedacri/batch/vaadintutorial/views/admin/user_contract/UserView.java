package org.cedacri.batch.vaadintutorial.views.admin.user_contract;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.cedacri.batch.vaadintutorial.core.models.entity.User;
import org.cedacri.batch.vaadintutorial.core.models.service.AuthService;
import org.cedacri.batch.vaadintutorial.core.models.service.user.UserService;
import org.cedacri.batch.vaadintutorial.views.admin.user_contract.components.UserForm;
import org.cedacri.batch.vaadintutorial.views.common.HomeView;
import org.cedacri.batch.vaadintutorial.views.main_tmpl.MainView;

import java.util.List;

@Route(value = "user", layout = MainView.class)
public class UserView extends Div implements UserViewContract {

    private final Grid<User> usersTable = new Grid<>(User.class, false);
    private final UserPresenter presenter;

    private final Div sidePanel = new Div();
    private final Div content = new Div();


    private final Button createButton = new Button("Create user");

    public UserView(UserService userService) {
        presenter = new UserPresenter(this, userService);

        configureLayout();
        configureGrid();

        presenter.onInit();
    }

    private void configureLayout() {
        setSizeFull();

        content.setSizeFull();
        content.addClassNames(
                LumoUtility.Display.FLEX,
                LumoUtility.Gap.MEDIUM
        );

        usersTable.setWidthFull();
        usersTable.addClassName(LumoUtility.Flex.GROW);

        sidePanel.addClassName(LumoUtility.Flex.SHRINK_NONE);

        content.add(usersTable, sidePanel);

        createButton.addClickListener(e -> showCreateForm());

        add(createButton, content);
    }


    private void styleCard(Div card) {
        card.addClassNames(
                LumoUtility.Padding.MEDIUM,
                LumoUtility.Border.ALL,
                LumoUtility.BorderRadius.MEDIUM,
                LumoUtility.BoxShadow.SMALL
        );
        card.setWidth("380px");
    }

    private void configureGrid() {
        usersTable.addColumn(User::getId).setHeader("ID");
        usersTable.addColumn(User::getFullName).setHeader("Full name");
        usersTable.addColumn(User::getLogin).setHeader("Login");
        usersTable.addColumn(User::getEmail).setHeader("Email");
        usersTable.addColumn(User::getRole).setHeader("Role");

        usersTable.addComponentColumn(user -> {
            Button info = new Button("Info", e -> presenter.onInfo(user.getId()));
            Button update = new Button("Update", e -> presenter.onUpdateRequest(user.getId(), user));
            Button delete = new Button("Delete", e -> {
                presenter.onDeleteRequest(user.getId());
            });

            Div actions = new Div(info);

            if (!AuthService.getCurrentUser().equals(user)) {
                actions.add(update, delete);
            }

            actions.addClassNames(
                    LumoUtility.Display.FLEX,
                    LumoUtility.Gap.XSMALL
            );
            return actions;
        }).setHeader("Actions");
    }

    @Override
    public void showUsers(List<User> users) {
        usersTable.setItems(users);
    }

    @Override
    public void showUser(User user) {
        sidePanel.removeAll();
        styleCard(sidePanel);

        Button exit = new Button("Exit", e -> sidePanel.setVisible(false));
        exit.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        sidePanel.add(
                exit,
                readonly("Full name", user.getFullName()),
                readonly("Login", user.getLogin()),
                readonly("Email", user.getEmail()),
                readonly("Role", user.getRole() != null ? user.getRole().toString() : "")
        );

        sidePanel.setVisible(true);
    }

    @Override
    public void createUser() {
        showCreateForm();
    }

    @Override
    public void deleteUser(Long id, User user) {
        sidePanel.removeAll();
        styleCard(sidePanel);

        Button exit = new Button("Exit", e -> sidePanel.setVisible(false));
        H3 question = new H3("Are you sure you want to delete this User?");
        Paragraph paragraph = new Paragraph(user.getFullName());
        Button delete = new Button("Delete", e -> {
            presenter.onDeleteExecuted(id);
        });
        exit.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        sidePanel.add(exit, question, paragraph, delete);

        sidePanel.setVisible(true);
    }


    @Override
    public void updateTableAfterChanging(List<User> users) {
        usersTable.setItems(users);
        sidePanel.setVisible(false);
    }

    @Override
    public void updateUser(Long id, User user) {
        sidePanel.removeAll();
        styleCard(sidePanel);

        UserForm updateForm = new UserForm(false);
        updateForm.setUser(user);

        Button save = new Button("Update", e -> {
            if (updateForm.write(user)) {
                presenter.onUpdateSave(id, user);
                sidePanel.removeAll();
            } else {
                Notification.show("Please fill all required fields", 3000, Notification.Position.MIDDLE);
            }
        });

        Button exit = new Button("Exit", e -> sidePanel.removeAll());

        sidePanel.add(exit, updateForm, save);
        sidePanel.setVisible(true);
    }

    @Override
    public void showError(String message) {
        Notification.show(message, 3000, Notification.Position.MIDDLE);
    }

    @Override
    public void navigateHome() {
        UI.getCurrent().navigate(HomeView.class);
    }

    private TextField readonly(String label, String value) {
        TextField field = new TextField(label);
        field.setValue(value != null ? value : "");
        field.setReadOnly(true);
        return field;
    }

    private void showCreateForm() {
        sidePanel.removeAll();
        styleCard(sidePanel);

        User newUser = new User();
        UserForm createForm = new UserForm(true);
        createForm.setUser(newUser);

        Button save = new Button("Create", e -> {

            if (createForm.write(newUser)) {
                newUser.setPassword(createForm.getPassword());
                presenter.onCreate(newUser);
            }
        });

        Button exit = new Button("Exit", e -> sidePanel.removeAll());

        sidePanel.add(exit, createForm, save);
        sidePanel.setVisible(true);
    }

}
