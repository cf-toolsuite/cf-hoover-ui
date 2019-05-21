package io.pivotal.cfapp.ui.view;

import static io.pivotal.cfapp.ui.view.SnapshotApplicationDetailView.NAV;

import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.LocalDateTimeRenderer;
import com.vaadin.flow.data.renderer.NumberRenderer;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import com.vaadin.flow.router.Route;

import org.springframework.beans.factory.annotation.Autowired;

import io.pivotal.cfapp.domain.AppDetail;
import io.pivotal.cfapp.repository.MetricCache;
import io.pivotal.cfapp.ui.MainLayout;
import io.pivotal.cfapp.ui.component.GridTile;

@Route(value = NAV, layout = MainLayout.class)
public class SnapshotApplicationDetailView extends VerticalLayout {

    private static final long serialVersionUID = 1L;
    public static final String NAV = "snapshot/detail/ai";

    @Autowired
    public SnapshotApplicationDetailView(
        MetricCache cache) {
        // TODO Resource bundle for title and tile labels
        H2 title = new H2("Snapshot » Detail » AI");
        HorizontalLayout firstRow = new HorizontalLayout();
        GridTile<AppDetail> tile = new GridTile<>("Applications", buildGrid(), cache.getSnapshotDetail().getApplications());
        firstRow.add(tile);
        add(title, firstRow);
        firstRow.setSizeFull();
        setSizeFull();
    }

    private Grid<AppDetail> buildGrid() {
        NumberFormat formatter = NumberFormat.getInstance();
        formatter.setMaximumFractionDigits(2);
        Grid<AppDetail> grid = new Grid<>(AppDetail.class, false);
        grid.addColumn(TemplateRenderer.<AppDetail> of("[[item.foundation]]").withProperty("foundation", AppDetail::getFoundation)).setHeader("Foundation").setTextAlign(ColumnTextAlign.CENTER).setResizable(true);
        grid.addColumn(TemplateRenderer.<AppDetail> of("[[item.organization]]").withProperty("organization", AppDetail::getOrganization)).setHeader("Organization").setResizable(true);
        grid.addColumn(TemplateRenderer.<AppDetail> of("[[item.space]]").withProperty("space", AppDetail::getSpace)).setHeader("Space").setResizable(true);
        grid.addColumn(TemplateRenderer.<AppDetail> of("[[item.appId]]").withProperty("appId", AppDetail::getAppId)).setHeader("Application Id").setResizable(true);
        grid.addColumn(TemplateRenderer.<AppDetail> of("[[item.appName]]").withProperty("appName", AppDetail::getAppName)).setHeader("Application Name").setResizable(true);
        grid.addColumn(TemplateRenderer.<AppDetail> of("[[item.buildpack]]").withProperty("buildpack", AppDetail::getBuildpack)).setHeader("Buildpack").setResizable(true);
        grid.addColumn(TemplateRenderer.<AppDetail> of("[[item.image]]").withProperty("image", AppDetail::getImage)).setHeader("Docker Image").setResizable(true);
        grid.addColumn(TemplateRenderer.<AppDetail> of("[[item.stack]]").withProperty("stack", AppDetail::getStack)).setHeader("Stack").setResizable(true);
        grid.addColumn(new NumberRenderer<>(AppDetail::getRunningInstances, formatter)).setHeader("Running Instances").setTextAlign(ColumnTextAlign.END).setResizable(true);
        grid.addColumn(new NumberRenderer<>(AppDetail::getTotalInstances, formatter)).setHeader("Total Instances").setTextAlign(ColumnTextAlign.END).setResizable(true);
        grid.addColumn(new NumberRenderer<>(AppDetail::getMemoryUsageInGb, formatter)).setHeader("Memory Usage (in Gb)").setTextAlign(ColumnTextAlign.END).setResizable(true);
        grid.addColumn(new NumberRenderer<>(AppDetail::getDiskUsageInGb, formatter)).setHeader("Disk Usage (in Gb)").setTextAlign(ColumnTextAlign.END).setResizable(true);
        grid.addColumn(TemplateRenderer.<AppDetail> of("[[item.urls]]").withProperty("urls", AppDetail::getUrlsAsCsv)).setHeader("Routes").setResizable(true);
        grid.addColumn(new LocalDateTimeRenderer<AppDetail>(AppDetail::getLastPushed, DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG, FormatStyle.SHORT))).setHeader("Last Pushed").setTextAlign(ColumnTextAlign.END).setResizable(true);
        grid.addColumn(TemplateRenderer.<AppDetail> of("[[item.lastEvent]]").withProperty("lastEvent", AppDetail::getLastEvent)).setHeader("Last Event").setResizable(true);
        grid.addColumn(TemplateRenderer.<AppDetail> of("[[item.lastEventActor]]").withProperty("lastEventActor", AppDetail::getLastEventActor)).setHeader("Last Event Actor").setResizable(true);
        grid.addColumn(new LocalDateTimeRenderer<AppDetail>(AppDetail::getLastEventTime, DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG, FormatStyle.SHORT))).setHeader("Last Event Time").setTextAlign(ColumnTextAlign.END).setResizable(true);
        grid.addColumn(TemplateRenderer.<AppDetail> of("[[item.requestedState]]").withProperty("requestedState", AppDetail::getRequestedState)).setHeader("Requested State").setTextAlign(ColumnTextAlign.CENTER).setResizable(true);
        return grid;
    }

}