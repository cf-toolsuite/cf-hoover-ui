package io.pivotal.cfapp.ui;

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


@Route(value = "snapshot/summary/si", layout = MainView.class)
public class SnapshotServiceSummaryView extends VerticalLayout {

    private static final long serialVersionUID = 1L;

    @Autowired
    public SnapshotServiceSummaryView(
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