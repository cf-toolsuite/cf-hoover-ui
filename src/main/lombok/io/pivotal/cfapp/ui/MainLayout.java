package io.pivotal.cfapp.ui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.details.DetailsVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.RouterLink;

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


public class MainLayout extends AppLayout {

    private static final long serialVersionUID = 1L;

    public MainLayout() {
    	Tab homeTab = createTab(VaadinIcon.HOME.create(), "Home", HomeView.class);
    	
    	Accordion accordion = new Accordion();
    	
    	Tabs accountingTabs = createTabs();
    	Tab aurTab = createTab(VaadinIcon.TABLE.create(),"Application", AppUsageReportView.class);
    	Tab surTab = createTab(VaadinIcon.TABLE.create(), "Service", ServiceUsageReportView.class);
    	Tab spurTab = createTab(VaadinIcon.TABLE.create(), "Service Plan", ServicePlanUsageReportView.class);
    	Tab turTab = createTab(VaadinIcon.TABLE.create(), "Task", TaskUsageReportView.class);
    	accountingTabs.add(aurTab, surTab, spurTab, turTab);
    	accordion.add("Accounting", accountingTabs).addThemeVariants(DetailsVariant.REVERSE);
    	
    	Tabs snapshotDetailTabs = createTabs();
    	Tab sadTab = createTab(VaadinIcon.TABLE.create(), "Application", SnapshotApplicationDetailView.class);
    	Tab sidTab = createTab(VaadinIcon.TABLE.create(), "Service Instance", SnapshotServiceInstanceDetailView.class);
    	Tab suTab = createTab(VaadinIcon.USERS.create(), "Users", UsersView.class);
    	snapshotDetailTabs.add(sadTab, sidTab, suTab);
    	accordion.add("Snapshot Detail", snapshotDetailTabs).addThemeVariants(DetailsVariant.REVERSE);

    	Tabs snapshotSummaryTabs = createTabs();
    	Tab ssdTab = createTab(VaadinIcon.PIE_CHART.create(),"Demographics", DemographicsView.class);
    	Tab ssaTab = createTab(VaadinIcon.DASHBOARD.create(),"Application", SnapshotApplicationSummaryView.class);
    	Tab sssiTab = createTab(VaadinIcon.DASHBOARD.create(),"Service Instance", SnapshotServiceInstanceSummaryView.class);
    	snapshotSummaryTabs.add(ssdTab, ssaTab, sssiTab);
    	accordion.add("Snapshot Summary", snapshotSummaryTabs).addThemeVariants(DetailsVariant.REVERSE);
    	
    	addToNavbar(true, homeTab, new DrawerToggle());
    	addToDrawer(accordion);
    }
    
    private Tabs createTabs() {
    	Tabs menu = new Tabs();
    	menu.setWidthFull();
    	menu.setOrientation(Tabs.Orientation.VERTICAL);
    	menu.setFlexGrowForEnclosedTabs(1);
    	return menu;
    }
    
    private Tab createTab(Icon icon, String label, Class<? extends Component> layout) {
    	RouterLink link = new RouterLink(label, layout);
    	Tab tab = new Tab();
    	tab.add(icon, link);
    	return tab;
    }
 
}