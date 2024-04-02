package org.cftoolsuite.cfapp.ui.view;

import static org.cftoolsuite.cfapp.ui.view.SnapshotApplicationSummaryView.NAV;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.appreciated.apexcharts.ApexCharts;
import com.github.appreciated.apexcharts.ApexChartsBuilder;
import com.github.appreciated.apexcharts.config.builder.ChartBuilder;
import com.github.appreciated.apexcharts.config.builder.LegendBuilder;
import com.github.appreciated.apexcharts.config.builder.ResponsiveBuilder;
import com.github.appreciated.apexcharts.config.chart.Type;
import com.github.appreciated.apexcharts.config.legend.Position;
import com.github.appreciated.apexcharts.config.responsive.builder.OptionsBuilder;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import org.cftoolsuite.cfapp.domain.ApplicationCounts;
import org.cftoolsuite.cfapp.domain.BuildpackCount;
import org.cftoolsuite.cfapp.domain.DockerImageCount;
import org.cftoolsuite.cfapp.domain.StackCount;
import org.cftoolsuite.cfapp.domain.VelocityCount;
import org.cftoolsuite.cfapp.repository.MetricCache;
import org.cftoolsuite.cfapp.ui.MainLayout;
import org.cftoolsuite.cfapp.ui.MetricFormatter;
import org.cftoolsuite.cfapp.ui.component.GridTile;
import org.cftoolsuite.cfapp.ui.component.Tile;


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
        Tile memoryUsed = new Tile("Memory Used (in Gb)", formatter.format(counts.getTotalMemoryUsed()));
        Tile diskUsed = new Tile("Disk Used (in Gb)", formatter.format(counts.getTotalDiskUsed()));
        GridTile<BuildpackCount> byBuildpack = new GridTile<>("By Buildpack", BuildpackCount.class, BuildpackCount.listOf(counts.getByBuildpack()), new String[] { "buildpack", "count" });
        ApexCharts buildpackChart = createDonutChart(counts.getByBuildpack());
        VerticalLayout buildpackLayout = new VerticalLayout();
        buildpackLayout.add(buildpackChart, byBuildpack);
        GridTile<StackCount> byStack = new GridTile<>("By Stack", StackCount.class, StackCount.listOf(counts.getByStack()), new String[] { "stack", "count" });
        ApexCharts stackChart = createDonutChart(counts.getByStack());
        VerticalLayout stackLayout = new VerticalLayout();
        stackLayout.add(stackChart, byStack);
        GridTile<DockerImageCount> byDockerImage = new GridTile<>("By Docker Image", DockerImageCount.class, DockerImageCount.listOf(counts.getByDockerImage()), new String[] { "image", "count" });
        GridTile<VelocityCount> velocity = new GridTile<>("Velocity", VelocityCount.class, VelocityCount.listOf(counts.getVelocity()), new String[] { "timeframe", "count" });
        ApexCharts velocityChart = createDonutChart(counts.getVelocity());
        VerticalLayout velocityLayout = new VerticalLayout();
        velocityLayout.add(velocityChart, velocity);
        firstRow.add(applications, runningAIs, stoppedAIs, crashedAIs, totalAIs);
        secondRow.add(memoryUsed, diskUsed);
        thirdRow.add(buildpackLayout, stackLayout, byDockerImage, velocityLayout);
        firstRow.setWidthFull(); firstRow.setHeight("200px");
        secondRow.setWidth("1280px"); secondRow.setHeight("200px");
        thirdRow.setWidthFull(); thirdRow.setHeight("600px");
        add(title, firstRow, secondRow, thirdRow);
        setSizeFull();
    }

    private ApexCharts createDonutChart(Map<String, Long> items) {
        ApexChartsBuilder chartBuilder = new ApexChartsBuilder();
        chartBuilder.withChart(ChartBuilder.get().withType(Type.DONUT).build())
                .withLegend(LegendBuilder.get()
                        .withPosition(Position.RIGHT)
                        .build())
                .withResponsive(ResponsiveBuilder.get()
                        .withBreakpoint(480.0)
                        .withOptions(OptionsBuilder.get()
                                .withLegend(LegendBuilder.get()
                                        .withPosition(Position.BOTTOM)
                                        .build())
                                .build())
                        .build());
        ApexCharts chart = chartBuilder.build();
        updateChartData(chart, items);
        chart.setHeight("120px");
        return chart;
    }

    private void updateChartData(ApexCharts chart, Map<String, Long> items) {
        long total = items.values().stream().mapToLong(Long::longValue).sum();
        List<String> labels = new ArrayList<>();
        List<Number> series = new ArrayList<>();
        items.forEach((k, v) -> {
            labels.add(k);
            series.add(100 * v / (double) total);
        });
        chart.updateSeries(series.toArray(new Double[0]));
        chart.setLabels(labels.toArray(new String[0]));
    }
}