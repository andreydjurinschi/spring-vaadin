package org.cedacri.batch.vaadintutorial.views.main_tmpl;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import org.cedacri.batch.vaadintutorial.core.models.entity.Role;
import org.cedacri.batch.vaadintutorial.core.models.service.AuthService;
import org.cedacri.batch.vaadintutorial.views.admin.user_contract.UserView;
import org.cedacri.batch.vaadintutorial.views.common.HomeView;
import org.cedacri.batch.vaadintutorial.views.common.InfoView;
import org.cedacri.batch.vaadintutorial.views.common.LoginView;


@Route("")
public class MainView extends AppLayout implements AfterNavigationObserver {
    private static final String APP_NAME = "POST APP";
    private VerticalLayout menu;

    public MainView(AuthService authService) {
        menuInit();
    }

    private void menuInit(){
        DrawerToggle drawerToggle = new DrawerToggle();
        H1 appName = new H1(APP_NAME);
        appName.getStyle().set("font-family", "Times New Roman").set("font-size", "25px").set("margin", "0");
        addToNavbar(drawerToggle, appName);

        menu = new VerticalLayout();
        menu.setPadding(true);
        menu.setSpacing(true);
        menu.add(
                new RouterLink("HOME", HomeView.class),
                new RouterLink("Info", InfoView.class),
                new RouterLink("HoMe", HomeView.class) // fixme
        );
        addToDrawer(menu);
    }
    private void updateAuthButton() {

        menu.getChildren()
                .filter(c -> c.getId().isPresent() && c.getId().get().equals("auth-action"))
                .forEach(menu::remove);

        Component authComponent;
        if (AuthService.isAuthenticated()) {
            Button logout = new Button("Logout", e -> {
                AuthService.logout();
                getUI().ifPresent(ui -> ui.navigate(LoginView.class));
            });
            logout.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            logout.setId("auth-action");
            authComponent = logout;
        } else {
            RouterLink login = new RouterLink("Login", LoginView.class);
            login.setId("auth-action");
            authComponent = login;
        }

        menu.getChildren()
                .filter(c -> c.getId().isPresent() && c.getId().get().equals("role-nav"))
                .forEach(menu::remove);

        if (AuthService.isAuthenticated()) {
            Role role = AuthService.getCurrentRole();
            RouterLink roleNav = generateNavBasedOnUserRole(role);
            roleNav.setId("role-nav");
            menu.addComponentAsFirst(roleNav);
        }

        menu.add(authComponent);
    }


    @Override
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
        updateAuthButton();
    }

    private RouterLink generateNavBasedOnUserRole(Role role){

        switch (role){
            case ADMIN -> {
                return new RouterLink("Users", UserView.class); // fixme: to add user admin panel
            }
            case CREATOR -> {
                return new RouterLink("Home", HomeView.class); // fixme: to add creator admin panel
            }
            case null, default -> {
                return new RouterLink();
            }
        }
    }
}

