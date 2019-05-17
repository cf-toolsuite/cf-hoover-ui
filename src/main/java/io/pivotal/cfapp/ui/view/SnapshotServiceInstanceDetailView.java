package io.pivotal.cfapp.ui.view;

import static io.pivotal.cfapp.ui.view.SnapshotServiceInstanceDetailView.NAV;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import org.springframework.beans.factory.annotation.Autowired;

import io.pivotal.cfapp.domain.ServiceInstanceDetail;
import io.pivotal.cfapp.repository.MetricCache;
import io.pivotal.cfapp.ui.MainLayout;
import io.pivotal.cfapp.ui.component.GridTile;

@Route(value = NAV, layout = MainLayout.class)
public class SnapshotServiceInstanceDetailView extends VerticalLayout {

    private static final long serialVersionUID = 1L;
    public static final String NAV = "snapshot/detail/si";

    @Autowired
    public SnapshotServiceInstanceDetailView(
        MetricCache cache) {
        // TODO Resource bundle for title and tile labels
        H2 title = new H2("Snapshot » Detail » SI");
        HorizontalLayout firstRow = new HorizontalLayout();
        GridTile<ServiceInstanceDetail> tile = new GridTile<>("Service Instances", ServiceInstanceDetail.class, cache.getSnapshotDetail().getServiceInstances(), new String[] { "foundation", "organization", "space", "serviceInstanceId", "name", "service", "description", "plan", "type", "applications", "lastOperation", "lastUpdated", "dashboardUrl", "requestedState" });
        firstRow.add(tile);
        firstRow.setSizeFull();
        add(title, firstRow);
        setSizeFull();
    }

}