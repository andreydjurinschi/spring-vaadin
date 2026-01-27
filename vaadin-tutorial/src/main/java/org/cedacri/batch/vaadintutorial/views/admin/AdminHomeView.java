package org.cedacri.batch.vaadintutorial.views.admin;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import org.cedacri.batch.vaadintutorial.core.models.entity.User;
import org.cedacri.batch.vaadintutorial.core.models.service.AuthService;

public class AdminHomeView extends HorizontalLayout {
    private final AuthService authService;

    public AdminHomeView(AuthService authService) {
        this.authService = authService;
        H2 greeting = pageGreeting();
        add(greeting);
        add(pageDescription());
        setAlignSelf(Alignment.END, this);
        setAlignItems(Alignment.CENTER);
    }

    private String getUserLogin(){
        User user = authService.getCurrentUser();
        return user.getLogin();
    }

    private H2 pageGreeting(){
        H2 h2 = new H2("Hi: " + getUserLogin());
        h2.getElement().getStyle().set("margin-down", "5px");
        return h2;
    }

    private TextArea pageDescription(){
        TextArea textArea = new TextArea();
        textArea.setMinRows(4);
        textArea.setValue("Данное приложение было создано для размещения, прочтения, управлением постами внутри одной системы " +
                "при помощи Vaadin и Spring Boot. Ваш пользоватеь является администратором и имеет полный контроль над всей системой.");
        textArea.getElement().getStyle().set("font-size", "20px");
        textArea.setMaxRows(5);
        return textArea;
    }
}
