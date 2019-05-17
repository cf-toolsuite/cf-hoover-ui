package io.pivotal.cfapp.ui.view;

import static io.pivotal.cfapp.ui.view.DemographicsView.NAV;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import org.springframework.beans.factory.annotation.Autowired;

import io.pivotal.cfapp.domain.Demographics;
import io.pivotal.cfapp.repository.MetricCache;
import io.pivotal.cfapp.ui.MainLayout;
import io.pivotal.cfapp.ui.MetricFormatter;
import io.pivotal.cfapp.ui.component.Tile;


@Route(value = NAV, layout = MainLayout.class)
public class DemographicsView extends VerticalLayout {

    private static final long serialVersionUID = 1L;
    public static final String NAV = "snapshot/demographics";

    @Autowired
    public DemographicsView(
        MetricCache cache,
        MetricFormatter formatter) {
        // TODO Resource bundle for title and tile labels
        H2 title = new H2("Snapshot » Demographics");
        HorizontalLayout firstRow = new HorizontalLayout();
        Demographics counts = cache.getDemographics();
        Tile organizations = new Tile("Organizations", formatter.format(counts.getOrganizations()));
        Tile spaces = new Tile("Spaces", formatter.format(counts.getSpaces()));
        Tile userAccounts = new Tile("User Accounts", formatter.format(counts.getUserAccounts()));
        Tile serviceAccounts = new Tile("Service Accounts", formatter.format(counts.getServiceAccounts()));
        firstRow.add(organizations, spaces, userAccounts, serviceAccounts);
        firstRow.setWidthFull(); firstRow.setHeight("200px");
        add(title, firstRow);
        setSizeFull();
    }

}