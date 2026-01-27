package org.cedacri.batch.vaadintutorial.views.common;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.cedacri.batch.vaadintutorial.views.main_tmpl.MainView;

@Route(value = "info", layout = MainView.class)
@PageTitle("info")
public class InfoView extends HorizontalLayout {
    InfoView() {
        H1 h1 = new  H1("Info page");
        setSizeFull();
        setSpacing(true);
        setPadding(true);
        add(h1);
    }
}
