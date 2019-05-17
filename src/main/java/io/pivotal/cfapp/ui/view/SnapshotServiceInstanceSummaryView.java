package io.pivotal.cfapp.ui.view;

import static io.pivotal.cfapp.ui.view.SnapshotServiceInstanceSummaryView.NAV;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import org.springframework.beans.factory.annotation.Autowired;

import io.pivotal.cfapp.domain.ServiceCount;
import io.pivotal.cfapp.domain.ServiceInstanceCounts;
import io.pivotal.cfapp.domain.ServicePlanCount;
import io.pivotal.cfapp.domain.VelocityCount;
import io.pivotal.cfapp.repository.MetricCache;
import io.pivotal.cfapp.ui.MainLayout;
import io.pivotal.cfapp.ui.MetricFormatter;
import io.pivotal.cfapp.ui.component.GridTile;
import io.pivotal.cfapp.ui.component.Tile;


@Route(value = NAV, layout = MainLayout.class)
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