package io.pivotal.cfapp.ui.view;

import static io.pivotal.cfapp.ui.view.SnapshotApplicationSummaryView.NAV;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import org.springframework.beans.factory.annotation.Autowired;

import io.pivotal.cfapp.domain.ApplicationCounts;
import io.pivotal.cfapp.domain.BuildpackCount;
import io.pivotal.cfapp.domain.DockerImageCount;
import io.pivotal.cfapp.domain.StackCount;
import io.pivotal.cfapp.domain.VelocityCount;
import io.pivotal.cfapp.repository.MetricCache;
import io.pivotal.cfapp.ui.MainLayout;
import io.pivotal.cfapp.ui.MetricFormatter;
import io.pivotal.cfapp.ui.component.GridTile;
import io.pivotal.cfapp.ui.component.Tile;


@Route(value = NAV, layout = MainLayout.class)
public class SnapshotApplicationSummaryView extends VerticalLayout {

    private static final long serialVersionUID = 1L;
    public static final String NAV = "snapshot/summary/ai";

    @Autowired
    public SnapshotApplicationSummaryView(
        MetricCache cache,
        MetricFormatter formatter) {
        // TODO Resource bundle for title and tile labels
        H2 title = new H2("Snapshot » Summary » AI");
        HorizontalLayout firstRow = new HorizontalLayout();
        HorizontalLayout secondRow = new HorizontalLayout();
        HorizontalLayout thirdRow = new HorizontalLayout();
        ApplicationCounts counts = cache.getSnapshotSummary().getApplicationCounts();
        Tile applications = new Tile("Applications", formatter.format(counts.getTotalApplications()));
        Tile runningAIs = new Tile("Running Application Instances", formatter.format(counts.getTotalRunningApplicationInstances()));
        Tile stoppedAIs = new Tile("Stopped Application Instances", formatter.format(counts.getTotalStoppedApplicationInstances()));
        Tile crashedAIs = new Tile("Crashed Application Instances", formatter.format(counts.getTotalCrashedApplicationInstances()));
        Tile totalAIs = new Tile("Total Application Instances", formatter.format(counts.getTotalApplicationInstances()));
        Tile memoryUsed = new Tile("Memory Used (in Mb)", formatter.format(counts.getTotalMemoryUsed()));
        Tile diskUsed = new Tile("Disk Used (in Mb)", formatter.format(counts.getTotalDiskUsed()));
        GridTile<BuildpackCount> byBuildpack = new GridTile<>("By Buildpack", BuildpackCount.class, BuildpackCount.listOf(counts.getByBuildpack()), new String[] { "buildpack", "count" });
        GridTile<StackCount> byStack = new GridTile<>("By Stack", StackCount.class, StackCount.listOf(counts.getByStack()), new String[] { "stack", "count" });
        GridTile<DockerImageCount> byDockerImage = new GridTile<>("By Docker Image", DockerImageCount.class, DockerImageCount.listOf(counts.getByDockerImage()), new String[] { "image", "count" });
        GridTile<VelocityCount> velocity = new GridTile<>("Velocity", VelocityCount.class, VelocityCount.listOf(counts.getVelocity()), new String[] { "timeframe", "count" });
        firstRow.add(applications, runningAIs, stoppedAIs, crashedAIs, totalAIs);
        secondRow.add(memoryUsed, diskUsed);
        thirdRow.add(byBuildpack, byStack, byDockerImage, velocity);
        firstRow.setWidthFull(); firstRow.setHeight("200px");
        secondRow.setWidth("1280px"); secondRow.setHeight("200px");
        thirdRow.setWidthFull(); thirdRow.setHeight("600px");
        add(title, firstRow, secondRow, thirdRow);
        setSizeFull();
    }

}