package io.pivotal.cfapp.ui;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import org.springframework.beans.factory.annotation.Autowired;

import io.pivotal.cfapp.domain.AppDetail;
import io.pivotal.cfapp.repository.MetricCache;

@Route(value = "snapshot/detail/ai", layout = MainView.class)
public class SnapshotApplicationDetailView extends VerticalLayout {

    private static final long serialVersionUID = 1L;

    @Autowired
    public SnapshotApplicationDetailView(
        MetricCache cache) {
        // TODO Resource bundle for title and tile labels
        H2 title = new H2("Snapshot » Detail » AI");
        HorizontalLayout firstRow = new HorizontalLayout();
        GridTile<AppDetail> tile = new GridTile<>("Applications", AppDetail.class, cache.getSnapshotDetail().getApplications(), new String[] { "foundation", "organization", "space", "appId", "appName", "buildpack", "image", "stack", "runningInstances", "totalInstances", "memoryUsage", "diskUsage", "urls", "lastPushed", "lastEvent", "lastEventActor", "lastEventTime", "requestedState" });
        firstRow.add(tile);
        add(title, firstRow);
        firstRow.setSizeFull();
        setSizeFull();
    }

}