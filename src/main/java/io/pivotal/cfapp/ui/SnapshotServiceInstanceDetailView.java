package io.pivotal.cfapp.ui;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.material.Material;

import org.springframework.beans.factory.annotation.Autowired;

import io.pivotal.cfapp.domain.ServiceInstanceDetail;
import io.pivotal.cfapp.repository.MetricCache;


@Route(value = "snapshot/detail/si")
@Theme(Material.class)
public class SnapshotServiceInstanceDetailView extends VerticalLayout {

    private static final long serialVersionUID = 1L;

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