package org.cedacri.batch.vaadintutorial.views.admin.user_contract.components;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import org.cedacri.batch.vaadintutorial.core.models.entity.Role;
import org.cedacri.batch.vaadintutorial.core.models.entity.User;

public class UserForm extends Div {

    private final TextField fullName = new TextField("Full name");
    private final TextField login = new TextField("Login");
    private final EmailField email = new EmailField("Email");
    private final PasswordField password = new PasswordField("Password");
    private final Select<Role> role = new Select<>("Role");

    private final Binder<User> binder = new Binder<>(User.class);

    public UserForm(boolean withPassword) {
        role.setItems(Role.values());

        binder.forField(fullName)
                .asRequired("Full name required")
                .bind(User::getFullName, User::setFullName);

        binder.forField(login)
                .asRequired("Login required")
                .bind(User::getLogin, User::setLogin);

        binder.forField(email)
                .asRequired("Email required")
                .bind(User::getEmail, User::setEmail);

        binder.forField(role)
                .asRequired("Role required")
                .bind(User::getRole, User::setRole);

        if (!withPassword) {
            password.setVisible(false);
        }

        add(fullName, login, email, password, role);
    }

    public void setUser(User user) {
        binder.readBean(user);
    }

    public boolean write(User user) {
        return binder.writeBeanIfValid(user);
    }

    public String getPassword() {
        return password.getValue();
    }
}


