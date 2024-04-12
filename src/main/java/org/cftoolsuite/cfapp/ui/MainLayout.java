package org.cftoolsuite.cfapp.ui;

import org.cftoolsuite.cfapp.ui.view.AppUsageReportView;
import org.cftoolsuite.cfapp.ui.view.DemographicsView;
import org.cftoolsuite.cfapp.ui.view.HomeView;
import org.cftoolsuite.cfapp.ui.view.ServicePlanUsageReportView;
import org.cftoolsuite.cfapp.ui.view.ServiceUsageReportView;
import org.cftoolsuite.cfapp.ui.view.SnapshotApplicationDetailView;
import org.cftoolsuite.cfapp.ui.view.SnapshotApplicationSummaryView;
import org.cftoolsuite.cfapp.ui.view.SnapshotServiceInstanceDetailView;
import org.cftoolsuite.cfapp.ui.view.SnapshotServiceInstanceSummaryView;
import org.cftoolsuite.cfapp.ui.view.SpringApplicationReportDependencyFrequencyView;
import org.cftoolsuite.cfapp.ui.view.SpringApplicationReportDetailView;
import org.cftoolsuite.cfapp.ui.view.TaskUsageReportView;
import org.cftoolsuite.cfapp.ui.view.UsersView;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.details.DetailsVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.RouterLink;


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

		Tabs springApplicationReportTabs = createTabs();
		Tab sarDetailTab = createTab(VaadinIcon.TABLE.create(),"Detail", SpringApplicationReportDetailView.class);
		Tab sardvTab = createTab(VaadinIcon.TABLE.create(),"Dependency Frequency", SpringApplicationReportDependencyFrequencyView.class);
		springApplicationReportTabs.add(sarDetailTab, sardvTab);
		accordion.add("Spring Application Insights", springApplicationReportTabs).addThemeVariants((DetailsVariant.REVERSE));

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
		RouterLink link = new RouterLink();
    	link.setRoute(layout);
		Div container = new Div();
		container.getStyle().set("display", "flex");
		container.getStyle().set("justify-content", "flex-start");
		container.getStyle().set("align-items", "center");
		container.getStyle().set("width", "100%"); // Ensure it takes up the full width
		icon.getStyle().set("margin-right", "8px"); // Adjust the space as needed
		container.add(icon, new Span(label));
		link.add(container);
		Tab tab = new Tab();
		tab.add(link);
		return tab;
    }

}