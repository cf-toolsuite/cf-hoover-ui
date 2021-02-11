package io.pivotal.cfapp.ui;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabVariant;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.RouterLink;
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
public class MainLayout extends AppLayout {

    private static final long serialVersionUID = 1L;

    public MainLayout() {
    	Tabs menu = new Tabs();
    	menu.setWidthFull();
    	menu.setOrientation(Tabs.Orientation.HORIZONTAL);
    	
    	RouterLink homeLink = new RouterLink(HomeView.NAV, HomeView.class);
    	homeLink.add(VaadinIcon.HOME.create());
    	homeLink.add("Home");
    	Tab homeTab = new Tab();
    	homeTab.add(homeLink);
    	homeTab.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);
    	
    	RouterLink aurLink = new RouterLink(AppUsageReportView.NAV, AppUsageReportView.class);
    	aurLink.add(VaadinIcon.TABLE.create());
    	aurLink.add("Accounting » Application");
    	Tab aurTab = new Tab();
    	aurTab.add(aurLink);
    	aurTab.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP); 
    	
    	RouterLink surLink = new RouterLink(ServiceUsageReportView.NAV, ServiceUsageReportView.class);
    	surLink.add(VaadinIcon.TABLE.create());
    	surLink.add("Accounting » Service");
    	Tab surTab = new Tab();
    	surTab.add(surLink);
    	surTab.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);
    	
    	RouterLink spurLink = new RouterLink(ServicePlanUsageReportView.NAV, ServicePlanUsageReportView.class);
    	spurLink.add(VaadinIcon.TABLE.create());
    	spurLink.add("Accounting » Service Plan");
    	Tab spurTab = new Tab();
    	spurTab.add(spurLink);
    	spurTab.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);
    	
    	RouterLink turLink = new RouterLink(TaskUsageReportView.NAV, TaskUsageReportView.class);
    	turLink.add(VaadinIcon.TABLE.create());
    	turLink.add("Accounting » Task");
    	Tab turTab = new Tab();
    	turTab.add(turLink);
    	turTab.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);
    	
    	RouterLink sadLink = new RouterLink(SnapshotApplicationDetailView.NAV, SnapshotApplicationDetailView.class);
    	sadLink.add(VaadinIcon.TABLE.create());
    	sadLink.add("Snapshot Detail » Application");
    	Tab sadTab = new Tab();
    	sadTab.add(sadLink);
    	sadTab.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);
    	
    	RouterLink sidLink = new RouterLink(SnapshotServiceInstanceDetailView.NAV, SnapshotServiceInstanceDetailView.class);
    	sidLink.add(VaadinIcon.TABLE.create());
    	sidLink.add("Snapshot Detail » Service Instance");
    	Tab sidTab = new Tab();
    	sidTab.add(sidLink);
    	sidTab.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);
    	
    	RouterLink suLink = new RouterLink(UsersView.NAV, UsersView.class);
    	suLink.add(VaadinIcon.TABLE.create());
    	suLink.add("Snapshot Detail » Users");
    	Tab suTab = new Tab();
    	suTab.add(suLink);
    	suTab.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);
    	
    	RouterLink ssdLink = new RouterLink(DemographicsView.NAV, DemographicsView.class);
    	ssdLink.add(VaadinIcon.TABLE.create());
    	ssdLink.add("Snapshot Summary » Demographics");
    	Tab ssdTab = new Tab();
    	ssdTab.add(ssdLink);
    	ssdTab.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);
    	
    	RouterLink ssaLink = new RouterLink(SnapshotApplicationSummaryView.NAV, SnapshotApplicationSummaryView.class);
    	ssaLink.add(VaadinIcon.TABLE.create());
    	ssaLink.add("Snapshot Summary » Application");
    	Tab ssaTab = new Tab();
    	ssaTab.add(ssaLink);
    	ssaTab.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);
    	
    	RouterLink sssiLink = new RouterLink(SnapshotServiceInstanceSummaryView.NAV, SnapshotServiceInstanceSummaryView.class);
    	sssiLink.add(VaadinIcon.TABLE.create());
    	sssiLink.add("Snapshot Summary » Service Instance");
    	Tab sssiTab = new Tab();
    	sssiTab.add(sssiLink);
    	sssiTab.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);
    	
    	menu.add(homeTab);
    	menu.add(aurTab);
    	menu.add(surTab);
    	menu.add(spurTab);
    	menu.add(turTab);
    	menu.add(sadTab);
    	menu.add(sidTab);
    	menu.add(suTab);
    	menu.add(ssdTab);
    	menu.add(ssaTab);
    	menu.add(sssiTab);
    	
    	addToNavbar(true, menu);
    	addToNavbar(true, new DrawerToggle());
    }
 
}