package org.cedacri.batch.vaadintutorial.views.home;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route
public class MainView extends VerticalLayout {
    public MainView() {
        add(new Button("Click Me!", e -> {
            Notification.show("sl;adasl;d,as;l");
        }));
    }
}
