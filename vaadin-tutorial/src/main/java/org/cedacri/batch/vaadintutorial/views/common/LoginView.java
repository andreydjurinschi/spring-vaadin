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
import org.cedacri.batch.vaadintutorial.services.AuthService;

@Route(value = "login", autoLayout = false)
@PageTitle("LOGIN")
@AnonymousAllowed
public class LoginView extends Div {

    public LoginView(AuthService authService) {
        setId("login-view");
        var username = new TextField("Username");
        var password = new PasswordField("Password");
        Button btn = new Button("СРУСЛУК");
        btn.addClickListener(e -> {
            Notification.show("CLICK WORKS");
            System.out.println("CLICK WORKS");
        });
        add(btn);
        add(new H1("djaskjdnasjkdnjkasndk"),
                username,
                password,
                new Button("asjdmnkasjdnkasjdn", e -> {
                    try {
                        authService.authenticate(
                                username.getValue().trim(),
                                password.getValue().trim()
                        );
                        UI.getCurrent().navigate("home");
                    } catch (AuthException ex) {
                        Notification.show(ex.getMessage());
                    }
                })

        );
    }
}
