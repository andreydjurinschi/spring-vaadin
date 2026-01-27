package org.cedacri.batch.vaadintutorial.views.common;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.cedacri.batch.vaadintutorial.core.models.entity.Role;
import org.cedacri.batch.vaadintutorial.core.models.service.AuthService;
import org.cedacri.batch.vaadintutorial.views.admin.AdminHomeView;
import org.cedacri.batch.vaadintutorial.views.main_tmpl.MainView;

@Route(value = "home", layout = MainView.class)
@PageTitle("home")
public class HomeView extends HorizontalLayout {

    private final AuthService authService;
    private Div content;
    HomeView(AuthService authService) {
        this.authService = authService;
        Role role =  authService.getCurrentRole();
        setSizeFull();
        setSpacing(true);
        setPadding(true);
        content = new Div();
        renderHomeBlockBasedOnRole(role);
        add(content);
    }

    private void renderHomeBlockBasedOnRole(Role role){

        switch (authService.getCurrentRole()) {
            case role.ADMIN -> {
                content.add(new AdminHomeView(authService));
            }
            case Role.CREATOR ->  {
                content.add(new H1("Hello Creator"));
            }
            case Role.VISITOR ->   {
                content.add(new H1("Hello Visitor"));
            }
            case null -> {
                content.add(new H1("Hello Anonymous"));
            }
            default -> {
                Notification.show("Invalid role");
            }
        }
    }
}
