package org.cedacri.batch.vaadintutorial.views.common;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.cedacri.batch.vaadintutorial.core.models.entity.Role;
import org.cedacri.batch.vaadintutorial.core.models.service.AuthService;
import org.cedacri.batch.vaadintutorial.views.main_tmpl.MainView;

@Route(value = "home", layout = MainView.class)
@PageTitle("home")
public class HomeView extends HorizontalLayout {

    private final Div content;
    HomeView() {
        setSizeFull();
        setSpacing(true);
        setPadding(true);
        content = new Div();
        renderHomeBlockBasedOnRole();
        add(content);
    }

    private void renderHomeBlockBasedOnRole(){

        switch (AuthService.getCurrentRole()) {
            case Role.CREATOR, Role.VISITOR, Role.ADMIN ->  {
                content.add(new H1("Hello " + currentUserFullName()));
            }
            case null -> {
                content.add(new H1("Hello Anonymous"));
            }
            default -> {
                Notification.show("Invalid role");
            }
        }
    }

    private String currentUserFullName(){
        return  AuthService.getCurrentUser().getFullName();
    }
}
