package org.cftoolsuite.cfapp.ui.view;

import static org.cftoolsuite.cfapp.ui.view.SnapshotServiceInstanceSummaryView.NAV;

import org.cftoolsuite.cfapp.domain.ServiceCount;
import org.cftoolsuite.cfapp.domain.ServiceInstanceCounts;
import org.cftoolsuite.cfapp.domain.ServicePlanCount;
import org.cftoolsuite.cfapp.domain.VelocityCount;
import org.cftoolsuite.cfapp.repository.MetricCache;
import org.cftoolsuite.cfapp.ui.MainLayout;
import org.cftoolsuite.cfapp.ui.MetricFormatter;
import org.cftoolsuite.cfapp.ui.component.GridTile;
import org.cftoolsuite.cfapp.ui.component.Tile;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;


@Route(value = NAV, layout = MainLayout.class)
@PageTitle("cf-hoover-ui » Snapshot » Summary » SI")
@AnonymousAllowed
public class SnapshotServiceInstanceSummaryView extends VerticalLayout {

    private static final long serialVersionUID = 1L;
    public static final String NAV = "snapshot/summary/si";

    @Autowired
    public SnapshotServiceInstanceSummaryView(
        MetricCache cache,
        MetricFormatter formatter) {
        // TODO Resource bundle for title and tile labels
        H2 title = new H2("Snapshot » Summary » SI");
        HorizontalLayout firstRow = new HorizontalLayout();
        HorizontalLayout secondRow = new HorizontalLayout();
        ServiceInstanceCounts counts = cache.getSnapshotSummary().getServiceInstanceCounts();
        Tile serviceInstances = new Tile("Service Instances", formatter.format(counts.getTotalServiceInstances()));
        GridTile<ServiceCount> byService= new GridTile<>("By Service", ServiceCount.class, ServiceCount.listOf(counts.getByService()), new String[] { "service", "count" });
        GridTile<ServicePlanCount> byServiceAndPlan = new GridTile<>("By Service/Plan", ServicePlanCount.class, ServicePlanCount.listOf(counts.getByServiceAndPlan()), new String[] { "servicePlan", "count" });
        GridTile<VelocityCount> velocity = new GridTile<>("Velocity", VelocityCount.class, VelocityCount.listOf(counts.getVelocity()), new String[] { "timeframe", "count" });
        firstRow.add(serviceInstances);
        secondRow.add(byService, byServiceAndPlan, velocity);
        firstRow.setWidthFull(); firstRow.setHeight("200px");
        secondRow.setWidthFull(); secondRow.setHeight("600px");
        add(title, firstRow, secondRow);
        setSizeFull();
    }

}