package org.cedacri.batch.vaadintutorial.views.common;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.cedacri.batch.vaadintutorial.views.main.MainView;

@Route(value = "home", layout = MainView.class)
@PageTitle("home")
public class HomeView extends HorizontalLayout {

    private TextField username;
    HomeView() {
        setId("homeView");
        username = new TextField("USERNAME");
        add(username);
        setVerticalComponentAlignment(Alignment.CENTER, username);
    }
}
