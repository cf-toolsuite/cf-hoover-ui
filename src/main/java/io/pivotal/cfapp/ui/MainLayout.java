package io.pivotal.cfapp.ui;

import com.vaadin.flow.component.applayout.AbstractAppRouterLayout;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.AppLayoutMenu;
import com.vaadin.flow.component.applayout.AppLayoutMenuItem;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.material.Material;

import io.pivotal.cfapp.ui.view.AppUsageReportView;
import io.pivotal.cfapp.ui.view.DemographicsView;
import io.pivotal.cfapp.ui.view.HomeView;
import io.pivotal.cfapp.ui.view.ServicePlanUsageReportView;
import io.pivotal.cfapp.ui.view.ServiceUsageReportView;
import io.pivotal.cfapp.ui.view.SnapshotApplicationDetailView;
import io.pivotal.cfapp.ui.view.SnapshotApplicationSummaryView;
import io.pivotal.cfapp.ui.view.SnapshotServiceInstanceDetailView;
import io.pivotal.cfapp.ui.view.SnapshotServiceInstanceSummaryView;
import io.pivotal.cfapp.ui.view.TaskUsageReportView;
import io.pivotal.cfapp.ui.view.UsersView;

@Theme(Material.class)
public class MainLayout extends AbstractAppRouterLayout {

    private static final long serialVersionUID = 1L;

    @Override
    protected void configure(AppLayout appLayout, AppLayoutMenu appLayoutMenu) {
        appLayoutMenu
            .addMenuItems(
                new AppLayoutMenuItem(VaadinIcon.HOME.create(), "Home", HomeView.NAV),
                new AppLayoutMenuItem(VaadinIcon.TABLE.create(), "Accounting » Application", AppUsageReportView.NAV),
                new AppLayoutMenuItem(VaadinIcon.TABLE.create(), "Accounting » Service", ServiceUsageReportView.NAV),
                new AppLayoutMenuItem(VaadinIcon.TABLE.create(), "Accounting » Service Plan", ServicePlanUsageReportView.NAV),
                new AppLayoutMenuItem(VaadinIcon.TABLE.create(), "Accounting » Task", TaskUsageReportView.NAV),
                new AppLayoutMenuItem(VaadinIcon.TABLE.create(), "Snapshot Detail » Application", SnapshotApplicationDetailView.NAV),
                new AppLayoutMenuItem(VaadinIcon.TABLE.create(), "Snapshot Detail » Service Instance", SnapshotServiceInstanceDetailView.NAV),
                new AppLayoutMenuItem(VaadinIcon.TABLE.create(), "Snapshot Detail » Users", UsersView.NAV),
                new AppLayoutMenuItem(VaadinIcon.DASHBOARD.create(), "Snapshot Summary » Demographics", DemographicsView.NAV),
                new AppLayoutMenuItem(VaadinIcon.DASHBOARD.create(), "Snapshot Summary » Application", SnapshotApplicationSummaryView.NAV),
                new AppLayoutMenuItem(VaadinIcon.DASHBOARD.create(), "Snapshot Summary » Service Instance", SnapshotServiceInstanceSummaryView.NAV)
            );
    }

}