package io.pivotal.cfapp.ui.view;

import static io.pivotal.cfapp.ui.view.SnapshotServiceInstanceDetailView.NAV;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.LocalDateTimeRenderer;
import com.vaadin.flow.data.renderer.TemplateRenderer;
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
        GridTile<ServiceInstanceDetail> tile = new GridTile<>("Service Instances", buildGrid(), cache.getSnapshotDetail().getServiceInstances());
        firstRow.add(tile);
        firstRow.setSizeFull();
        add(title, firstRow);
        setSizeFull();
    }

    public Grid<ServiceInstanceDetail> buildGrid() {
        Grid<ServiceInstanceDetail> grid = new Grid<>(ServiceInstanceDetail.class, false);
        grid.addColumn(TemplateRenderer.<ServiceInstanceDetail> of("[[item.foundation]]").withProperty("foundation", ServiceInstanceDetail::getFoundation)).setHeader("Foundation").setTextAlign(ColumnTextAlign.CENTER).setResizable(true);
        grid.addColumn(TemplateRenderer.<ServiceInstanceDetail> of("[[item.organization]]").withProperty("organization", ServiceInstanceDetail::getOrganization)).setHeader("Organization").setResizable(true);
        grid.addColumn(TemplateRenderer.<ServiceInstanceDetail> of("[[item.space]]").withProperty("space", ServiceInstanceDetail::getSpace)).setHeader("Space").setResizable(true);
        grid.addColumn(TemplateRenderer.<ServiceInstanceDetail> of("[[item.serviceInstanceId]]").withProperty("serviceInstanceId", ServiceInstanceDetail::getServiceInstanceId)).setHeader("Service Instance Id").setResizable(true);
        grid.addColumn(TemplateRenderer.<ServiceInstanceDetail> of("[[item.name]]").withProperty("name", ServiceInstanceDetail::getName)).setHeader("Name").setResizable(true);
        grid.addColumn(TemplateRenderer.<ServiceInstanceDetail> of("[[item.service]]").withProperty("service", ServiceInstanceDetail::getService)).setHeader("Service").setResizable(true);
        grid.addColumn(TemplateRenderer.<ServiceInstanceDetail> of("[[item.description]]").withProperty("description", ServiceInstanceDetail::getDescription)).setHeader("Description").setResizable(true);
        grid.addColumn(TemplateRenderer.<ServiceInstanceDetail> of("[[item.plan]]").withProperty("plan", ServiceInstanceDetail::getPlan)).setHeader("Plan").setResizable(true);
        grid.addColumn(TemplateRenderer.<ServiceInstanceDetail> of("[[item.applications]]").withProperty("applications", ServiceInstanceDetail::getApplicationsAsCsv)).setHeader("Bound Applications").setResizable(true);
        grid.addColumn(TemplateRenderer.<ServiceInstanceDetail> of("[[item.lastOperation]]").withProperty("lastOperation", ServiceInstanceDetail::getLastOperation)).setHeader("Last Operation").setResizable(true);
        grid.addColumn(new LocalDateTimeRenderer<ServiceInstanceDetail>(ServiceInstanceDetail::getLastUpdated, DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG, FormatStyle.SHORT))).setHeader("Last Updated").setTextAlign(ColumnTextAlign.END).setResizable(true);
        grid.addColumn(TemplateRenderer.<ServiceInstanceDetail> of("[[item.dashboardUrl]]").withProperty("dashboardUrl", ServiceInstanceDetail::getDashboardUrl)).setHeader("Dashboard URL").setResizable(true);
        grid.addColumn(TemplateRenderer.<ServiceInstanceDetail> of("[[item.requestedState]]").withProperty("requestedState", ServiceInstanceDetail::getRequestedState)).setHeader("Requested State").setTextAlign(ColumnTextAlign.CENTER).setResizable(true);
        return grid;
    }
}