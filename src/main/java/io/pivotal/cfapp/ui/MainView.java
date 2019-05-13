package io.pivotal.cfapp.ui;

import com.github.appreciated.app.layout.behaviour.Behaviour;
import com.github.appreciated.app.layout.builder.AppLayoutBuilder;
import com.github.appreciated.app.layout.component.menu.left.builder.LeftAppMenuBuilder;
import com.github.appreciated.app.layout.component.menu.left.builder.LeftSubMenuBuilder;
import com.github.appreciated.app.layout.component.menu.left.items.LeftNavigationItem;
import com.github.appreciated.app.layout.router.AppLayoutRouterLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.material.Material;

@Push
@Viewport("width=device-width, minimum-scale=1.0, initial-scale=1.0, user-scalable=yes")
@Theme(Material.class)
public class MainView extends AppLayoutRouterLayout {

    private static final long serialVersionUID = 1L;

    public MainView() {
        Component appMenu = LeftAppMenuBuilder
                .get()
                .add(LeftSubMenuBuilder
                        .get("Accounting", VaadinIcon.DOLLAR.create())
                        .add(new LeftNavigationItem("Application",
                                        VaadinIcon.TABLE.create(),
                                        AppUsageReportView.class))
                        .add(new LeftNavigationItem("Service",
                                        VaadinIcon.TABLE.create(),
                                        ServiceUsageReportView.class))
                        .add(new LeftNavigationItem("Servce Plan",
                                        VaadinIcon.TABLE.create(),
                                        ServicePlanUsageReportView.class))
                        .add(new LeftNavigationItem("Task",
                                        VaadinIcon.TABLE.create(),
                                        TaskUsageReportView.class))
                        .build())
                .add(LeftSubMenuBuilder
                        .get("Snapshot", VaadinIcon.DASHBOARD.create())
                        .add(LeftSubMenuBuilder
                                .get("Detail", VaadinIcon.TABLE.create())
                                .add(new LeftNavigationItem("Application",
                                        VaadinIcon.TABLE.create(),
                                        SnapshotApplicationDetailView.class))
                                .add(new LeftNavigationItem("Service Instance",
                                        VaadinIcon.TABLE.create(),
                                        SnapshotServiceInstanceDetailView.class))
                                .build())
                        .add(LeftSubMenuBuilder
                                .get("Summary", VaadinIcon.DASHBOARD.create())
                                .add(new LeftNavigationItem("Application",
                                        VaadinIcon.DASHBOARD.create(),
                                        SnapshotApplicationSummaryView.class))
                                .add(new LeftNavigationItem("Service Instance",
                                        VaadinIcon.DASHBOARD.create(),
                                        SnapshotServiceSummaryView.class))
                                .build())
                        .build())
                .build();

        init(AppLayoutBuilder
                .get(Behaviour.LEFT_HYBRID)
                .withAppMenu(appMenu)
                .build());
    }

}