package org.cedacri.batch.vaadintutorial.views.common;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.Lumo;
import jakarta.security.auth.message.AuthException;
import org.cedacri.batch.vaadintutorial.core.models.dto.RegisterForm;
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

        Button registerButton = new Button("Register as visitor", e -> {
            openRegisterDialog(authService);
        });
        FormLayout form = new FormLayout(username, password, loginButton, registerButton);

        form.setWidth("320px");
        Div wrapper = new Div(form);
        wrapper.getStyle().set("display", "flex");
        wrapper.getStyle().set("justify-content", "center");
        wrapper.getStyle().set("align-items", "center");
        wrapper.setSizeFull();

        add(wrapper);
    }

    private void openRegisterDialog(AuthService authService) {
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Register");

        TextField login = new TextField("Username");
        TextField fullName = new TextField("Full name");
        TextField email = new TextField("Email");

        PasswordField password = new PasswordField("Password");
        PasswordField repeatPassword = new PasswordField("Repeat password");

        login.setRequiredIndicatorVisible(true);
        email.setRequiredIndicatorVisible(true);
        password.setRequiredIndicatorVisible(true);
        repeatPassword.setRequiredIndicatorVisible(true);

        Binder<RegisterForm> binder = new Binder<>(RegisterForm.class);
        RegisterForm formBean = new RegisterForm();

        binder.forField(login)
                .asRequired("Username is required")
                .withValidator(v -> v.length() >= 3, "Minimum 3 characters")
                .bind(RegisterForm::getLogin, RegisterForm::setLogin);

        binder.forField(fullName)
                .asRequired("Full name is required")
                .bind(RegisterForm::getFullName, RegisterForm::setFullName);

        binder.forField(email)
                .asRequired("Email is required")
                .withValidator(new EmailValidator("Invalid email"))
                .bind(RegisterForm::getEmail, RegisterForm::setEmail);

        binder.forField(password)
                .asRequired("Password is required")
                .withValidator(p -> p.length() >= 6, "Minimum 6 characters")
                .bind(RegisterForm::getPassword, RegisterForm::setPassword);

        binder.forField(repeatPassword)
                .asRequired("Repeat password")
                .withValidator(p -> p.equals(password.getValue()), "Passwords do not match")
                .bind(RegisterForm::getRepeatPassword, RegisterForm::setRepeatPassword);

        Button register = new Button("Register");
        register.setEnabled(false);

        binder.addStatusChangeListener(event ->
                register.setEnabled(binder.isValid())
        );

        register.addClickListener(e -> {
            if (binder.writeBeanIfValid(formBean)) {
                try {
                    authService.register(
                            formBean.getLogin().trim(),
                            formBean.getEmail().trim(),
                            formBean.getFullName().trim(),
                            formBean.getPassword()
                    );

                    authService.authenticate(
                            formBean.getLogin(),
                            formBean.getPassword()
                    );

                    dialog.close();
                    UI.getCurrent().navigate("info");

                } catch (Exception ex) {
                    Notification.show(ex.getMessage(), 3000, Notification.Position.MIDDLE);
                }
            }
        });

        Button cancel = new Button("Cancel", e -> dialog.close());

        FormLayout formLayout = new FormLayout(
                login,
                fullName,
                email,
                password,
                repeatPassword
        );

        dialog.add(formLayout);
        dialog.getFooter().add(register, cancel);
        dialog.setWidth("420px");
        dialog.setModal(true);
        dialog.open();
    }

}


