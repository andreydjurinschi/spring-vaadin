package org.cedacri.batch.vaadintutorial.views.common;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import jakarta.security.auth.message.AuthException;
import org.cedacri.batch.vaadintutorial.core.models.service.AuthService;
import org.cedacri.batch.vaadintutorial.views.main_tmpl.MainView;

@Route(value = "login", layout = MainView.class)
@PageTitle("LOGIN")
@AnonymousAllowed
public class LoginView extends Div {
    public LoginView(AuthService authService) {
        setId("login-view");
        var username = new TextField("Username");
        var password = new PasswordField("Password");
        add(new H1("Hello!"),
                username,
                password,
                new Button("Sign in", e -> {
                    try {
                        authService.authenticate(
                                username.getValue().trim(),
                                password.getValue().trim()
                        );
                        UI.getCurrent().navigate("info");
                    } catch (AuthException ex) {
                        Notification.show(ex.getMessage());
                    }
                })

        );
    }
}
