package org.cedacri.batch.vaadintutorial.views.common;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.Lumo;
import jakarta.security.auth.message.AuthException;
import org.cedacri.batch.vaadintutorial.core.models.service.AuthService;


@Route(value = "login")
@PageTitle("LOGIN")
@AnonymousAllowed
@StyleSheet(Lumo.STYLESHEET)
/*@CssImport("./mytheme/style.css")*/
public class LoginView extends VerticalLayout {

    public LoginView(AuthService authService) {

        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        TextField username = new TextField("Username");
        PasswordField password = new PasswordField("Password");

        Button loginButton = new Button("Sign in", e -> {
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
        form.setWidth("320px");

        Div wrapper = new Div(form);
        wrapper.getStyle().set("display", "flex");
        wrapper.getStyle().set("justify-content", "center");
        wrapper.getStyle().set("align-items", "center");
        wrapper.setSizeFull();

        add(wrapper);
    }
}


