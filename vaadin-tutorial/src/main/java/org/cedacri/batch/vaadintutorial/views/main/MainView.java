package org.cedacri.batch.vaadintutorial.views.main;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouterLink;
import org.cedacri.batch.vaadintutorial.services.AuthService;
import org.cedacri.batch.vaadintutorial.views.common.HomeView;
import org.cedacri.batch.vaadintutorial.views.common.InfoView;

import java.util.Optional;

public class MainView extends AppLayout implements AfterNavigationObserver, BeforeEnterObserver {

    private final Tabs menu;
    private H1 viewTitle;

    public MainView() {
        setPrimarySection(Section.NAVBAR);
        addToNavbar(true, createHeader());
        menu = createMenu();
        addToNavbar(menu);
    }


    private Component createHeader() {
        HorizontalLayout header = new HorizontalLayout();
        header.setId("header");
        header.getThemeList().set("dark", true);
        header.setWidthFull();
        header.setAlignItems(FlexComponent.Alignment.CENTER);
        viewTitle = new H1("View Title");
        header.add(viewTitle);
        return header;
    }

    private Tabs createMenu() {
        Tabs menu = new Tabs();
        menu.setId("menu");
        menu.setOrientation(Tabs.Orientation.VERTICAL);
        menu.setWidthFull();
        menu.addThemeVariants(TabsVariant.LUMO_CENTERED);
        menu.add(createMenuItems());
        return menu;
    }

    private Component[] createMenuItems() {
        return new Tab[]{
                createMenuTab("home", HomeView.class), createMenuTab("info", InfoView.class)
        };
    }

    private Tab createMenuTab(String text, Class<? extends Component> component) {
        Tab tab = new Tab();
        tab.add(new RouterLink(text, component));
        ComponentUtil.setData(tab, Class.class, component);
        return tab;
    }

    @Override
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
        getTabForComponent(getContent()).ifPresent(menu::setSelectedTab);
        viewTitle.setText(getCurrentPageTitle());
    }

    private Optional<Tab> getTabForComponent(Component component) {
        return menu.getChildren()
                .filter(tab -> ComponentUtil.getData(tab, Class.class)
                        .equals(component.getClass())).findFirst().map(Tab.class::cast);
    }

    private String getCurrentPageTitle(){
        return getContent().getClass().getAnnotation(PageTitle.class).value();
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if(!AuthService.isAuthenticated()){
            beforeEnterEvent.forwardTo("login");
        }
    }
}

