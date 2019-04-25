package io.pivotal.cfapp.ui;

import javax.annotation.PostConstruct;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.material.Material;

import org.springframework.beans.factory.annotation.Autowired;

import io.pivotal.cfapp.client.SnapshotClient;
import io.pivotal.cfapp.client.UsageClient;


@Route(value = "")
@Theme(Material.class)
@PWA(name = "Hoover UI, Vaadin Flow with Spring", shortName = "Hoover UI")
public class CfHooverDashboard extends VerticalLayout {

    private static final long serialVersionUID = 1L;

    private final SnapshotClient snapshotClient;
    private final UsageClient usageClient;

    @Autowired
    public CfHooverDashboard(
        SnapshotClient snapshotClient,
        UsageClient usageClient) {
        this.snapshotClient = snapshotClient;
        this.usageClient = usageClient;
    }

    @PostConstruct
    protected void init() {
        // TODO property-drive this title thru externalized configuration
        snapshotClient.getSummary().subscribe(s -> {
            H2 title = new H2("Hoover Dashboard");
            HorizontalLayout firstRow = new HorizontalLayout();
            VerticalLayout statLayout = new VerticalLayout();
            H3 stat = new H3(String.valueOf(s.getApplicationCounts().getTotalApplications()));
            Label statLabel = new Label("Applications");
            statLayout.add(stat, statLabel);
            firstRow.add(statLayout);
            add(title, firstRow);
            setSizeFull();
        });
        
    }
}