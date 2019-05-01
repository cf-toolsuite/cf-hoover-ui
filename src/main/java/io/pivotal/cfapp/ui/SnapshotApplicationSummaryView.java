package io.pivotal.cfapp.ui;

import java.util.List;
import java.util.Map;

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
        HorizontalLayout thirdRow = new HorizontalLayout();
        Tile applications = new Tile("Applications");
        Tile runningAIs = new Tile("Running Application Instances");
        Tile stoppedAIs = new Tile("Stopped Application Instances");
        Tile crashedAIs = new Tile("Crashed Application Instances");
        Tile totalAIs = new Tile("Total Application Instances");
        Tile memoryUsed = new Tile("Memory Used (in Gb)");
        Tile diskUsed = new Tile("Disk Used (in Gb)");
        GridTile<Map<String, Long>> byBuildpack = new GridTile<>("By Buildpack");
        GridTile<Map<String, Long>> byStack = new GridTile<>("By Stack");
        GridTile<Map<String, Long>> velocity = new GridTile<>("Velocity");
        firstRow.add(applications, runningAIs, stoppedAIs, crashedAIs, totalAIs);
        secondRow.add(memoryUsed, diskUsed);
        thirdRow.add(byBuildpack, byStack, velocity);
        setSizeFull();  
        Button button = new Button("Refresh", event -> {
            client.getSummary()
                .subscribe(
                    summary -> getUI().ifPresent(ui -> {
                        ui.access(() -> {
                            refreshTile(formatter.format(summary.getApplicationCounts().getTotalApplications()), applications);
                            refreshTile(formatter.format(summary.getApplicationCounts().getTotalRunningApplicationInstances()), runningAIs);
                            refreshTile(formatter.format(summary.getApplicationCounts().getTotalStoppedApplicationInstances()), stoppedAIs);
                            refreshTile(formatter.format(summary.getApplicationCounts().getTotalCrashedApplicationInstances()), crashedAIs);
                            refreshTile(formatter.format(summary.getApplicationCounts().getTotalApplicationInstances()), totalAIs);
                            refreshTile(formatter.format(summary.getApplicationCounts().getTotalMemoryUsed()), memoryUsed);
                            refreshTile(formatter.format(summary.getApplicationCounts().getTotalDiskUsed()), diskUsed);
                            refreshGridTile(summary.getApplicationCounts().getByBuildpack(), byBuildpack);
                            refreshGridTile(summary.getApplicationCounts().getByStack(), byStack);
                            refreshGridTile(summary.getApplicationCounts().getVelocity(), velocity);
                        });
                    })
                );
        });
        add(title, firstRow, secondRow, thirdRow, button);
    }

    private void refreshTile(String metric, Tile target) {
        target.getStat().setText(metric);
    }

    private void refreshGridTile(Map<String, Long> metrics, GridTile<Map<String, Long>> target) {
        List<Map<String, Long>> list = List.of(metrics);
        list.get(0).keySet().forEach(key -> target.getStat().addColumn(c -> c.get(key)));
        target.getStat().setItems(list);
    }

}