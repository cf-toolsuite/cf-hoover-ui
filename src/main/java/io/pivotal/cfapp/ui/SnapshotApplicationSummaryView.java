package io.pivotal.cfapp.ui;

import java.text.NumberFormat;

import javax.annotation.PostConstruct;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.material.Material;

import org.springframework.beans.factory.annotation.Autowired;

import io.pivotal.cfapp.client.HooverClient;


@Route(value = "snapshot/summary/application")
@Theme(Material.class)
@PWA(name = "Snapshot Application Summary View", shortName = "SASV")
public class SnapshotApplicationSummaryView extends VerticalLayout {

    private static final long serialVersionUID = 1L;

    private final HooverClient client;
    private final MetricFormatter formatter;

    @Autowired
    public SnapshotApplicationSummaryView(
        HooverClient client,
        MetricFormatter formatter) {
        this.client = client;
        this.formatter = formatter;
    }

    @PostConstruct
    protected void init() {
        // TODO property-drive this title thru externalized configuration
        H2 title = new H2("Snapshot » Summary » Application");
        HorizontalLayout firstRow = new HorizontalLayout();
        HorizontalLayout secondRow = new HorizontalLayout();
        Tile applications = new Tile("Applications");
        Tile runningAIs = new Tile("Running Application Instances");
        Tile stoppedAIs = new Tile("Stopped Application Instances");
        Tile crashedAIs = new Tile("Crashed Application Instances");
        Tile totalAIs = new Tile("Total Application Instances");
        Tile memoryUsed = new Tile("Memory Used (in Gb)");
        Tile diskUsed = new Tile("Disk Used (in Gb)");
        firstRow.add(applications, runningAIs, stoppedAIs, totalAIs);
        secondRow.add(memoryUsed, diskUsed);
        setSizeFull();  
        Button button = new Button("Refresh", event -> {
            client.getSummary()
                .subscribe(
                    summary -> getUI().ifPresent(ui -> {
                        ui.access(() -> {
                            applications.getStat().setText(formatter.format(summary.getApplicationCounts().getTotalApplications()));
                            runningAIs.getStat().setText(formatter.format(summary.getApplicationCounts().getTotalRunningApplicationInstances()));
                            stoppedAIs.getStat().setText(formatter.format(summary.getApplicationCounts().getTotalStoppedApplicationInstances()));
                            crashedAIs.getStat().setText(formatter.format(summary.getApplicationCounts().getTotalCrashedApplicationInstances()));
                            totalAIs.getStat().setText(formatter.format(summary.getApplicationCounts().getTotalApplicationInstances()));
                            memoryUsed.getStat().setText(formatter.format(summary.getApplicationCounts().getTotalMemoryUsed()));
                            diskUsed.getStat().setText(formatter.format(summary.getApplicationCounts().getTotalDiskUsed()));
                        });
                    })
                );
        });
        add(title, firstRow, secondRow, button);
    }

}