package io.pivotal.cfapp.ui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabVariant;
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
    	Tabs menu = new Tabs();
    	menu.setWidthFull();
    	menu.setOrientation(Tabs.Orientation.HORIZONTAL);
    	
    	Tab homeTab = createTab(VaadinIcon.HOME.create(), "Home", HomeView.class);
    	Tab aurTab = createTab(VaadinIcon.TABLE.create(),"Accounting » Application", AppUsageReportView.class);
    	Tab surTab = createTab(VaadinIcon.TABLE.create(), "Accounting » Service", ServiceUsageReportView.class);
    	Tab spurTab = createTab(VaadinIcon.TABLE.create(), "Accounting » Service Plan", ServicePlanUsageReportView.class);
    	Tab turTab = createTab(VaadinIcon.TABLE.create(), "Accounting » Task", TaskUsageReportView.class);
    	Tab sadTab = createTab(VaadinIcon.TABLE.create(), "Snapshot Detail » Application", SnapshotApplicationDetailView.class);
    	Tab sidTab = createTab(VaadinIcon.TABLE.create(), "Snapshot Detail » Service Instance", SnapshotServiceInstanceDetailView.class);
    	Tab suTab = createTab(VaadinIcon.USERS.create(), "Snapshot Detail » Users", UsersView.class);
    	Tab ssdTab = createTab(VaadinIcon.PIE_CHART.create(),"Snapshot Summary » Demographics", DemographicsView.class);
    	Tab ssaTab = createTab(VaadinIcon.DASHBOARD.create(),"Snapshot Summary » Application", SnapshotApplicationSummaryView.class);
    	Tab sssiTab = createTab(VaadinIcon.DASHBOARD.create(),"Snapshot Summary » Service Instance", SnapshotServiceInstanceSummaryView.class);
    	
    	menu.add(homeTab, aurTab, surTab, spurTab, turTab, sadTab, sidTab, suTab, ssdTab, ssaTab, sssiTab);
    	
    	addToNavbar(true, menu);
    }
    
    private Tab createTab(Icon icon, String label, Class<? extends Component> layout) {
    	RouterLink link = new RouterLink(label, layout);
    	link.add(icon);
    	Tab tab = new Tab();
    	tab.add(link);
    	tab.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);
    	return tab;
    }
 
}