package org.cedacri.batch.vaadintutorial.views.common;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.Lumo;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.security.auth.message.AuthException;
import org.cedacri.batch.vaadintutorial.core.models.service.AuthService;
import org.cedacri.batch.vaadintutorial.views.main_tmpl.MainView;



@Route(value = "login", layout = MainView.class)
@PageTitle("LOGIN")
@AnonymousAllowed
@StyleSheet(Lumo.STYLESHEET)
/*@CssImport("./mytheme/style.css")*/
public class LoginView extends Div {

    public LoginView(AuthService authService) {

        setSizeFull();

        var username = new TextField("Username");
        var password = new PasswordField("Password");

        FormLayout form = renderLayout(authService, username, password);

        add(form);

        addClassNames(
                LumoUtility.Display.FLEX,
                LumoUtility.JustifyContent.CENTER,
                LumoUtility.AlignItems.CENTER
        );
    }

    private static FormLayout renderLayout(AuthService authService, TextField username, PasswordField password) {
        var loginButton = new Button("Sign in", e -> {
            try {
                authService.authenticate(
                        username.getValue().trim(),
                        password.getValue().trim()
                );
                UI.getCurrent().navigate("info");
            } catch (AuthException ex) {
                Notification.show(ex.getMessage());
            }
        });
        FormLayout form = new FormLayout(username, password, loginButton);
        form.setMaxWidth("320px");
        return form;
    }
}

