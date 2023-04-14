package io.pivotal.cfapp.ui.view;

import static io.pivotal.cfapp.ui.view.SnapshotServiceInstanceDetailView.NAV;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Collection;

import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.Column;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.LocalDateTimeRenderer;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

import org.apache.commons.lang3.StringUtils;
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
        GridTile<ServiceInstanceDetail> tile = new GridTile<>("Service Instances", buildGrid(cache.getSnapshotDetail().getServiceInstances()));
        firstRow.add(tile);
        firstRow.setSizeFull();
        add(title, firstRow);
        setSizeFull();
    }

    public Grid<ServiceInstanceDetail> buildGrid(Collection<ServiceInstanceDetail> items) {
        Grid<ServiceInstanceDetail> grid = new Grid<>(ServiceInstanceDetail.class, false);
        ListDataProvider<ServiceInstanceDetail> dataProvider = new ListDataProvider<>(items);
        grid.setItems(dataProvider);

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG, FormatStyle.SHORT);

        Column<ServiceInstanceDetail> foundationColumn = grid.addColumn(LitRenderer.<ServiceInstanceDetail> of("${item.foundation}").withProperty("foundation", ServiceInstanceDetail::getFoundation)).setHeader("Foundation").setTextAlign(ColumnTextAlign.CENTER);
        Column<ServiceInstanceDetail> organizationColumn = grid.addColumn(LitRenderer.<ServiceInstanceDetail> of("${item.organization}").withProperty("organization", ServiceInstanceDetail::getOrganization)).setHeader("Organization");
        Column<ServiceInstanceDetail> spaceColumn = grid.addColumn(LitRenderer.<ServiceInstanceDetail> of("${item.space}").withProperty("space", ServiceInstanceDetail::getSpace)).setHeader("Space");
        Column<ServiceInstanceDetail> serviceInstanceIdColumn = grid.addColumn(LitRenderer.<ServiceInstanceDetail> of("${item.serviceInstanceId}").withProperty("serviceInstanceId", ServiceInstanceDetail::getServiceInstanceId)).setHeader("Service Instance Id");
        Column<ServiceInstanceDetail> nameColumn = grid.addColumn(LitRenderer.<ServiceInstanceDetail> of("${item.name}").withProperty("name", ServiceInstanceDetail::getName)).setHeader("Name");
        Column<ServiceInstanceDetail> serviceColumn = grid.addColumn(LitRenderer.<ServiceInstanceDetail> of("${item.service}").withProperty("service", ServiceInstanceDetail::getService)).setHeader("Service");
        Column<ServiceInstanceDetail> descriptionColumn = grid.addColumn(LitRenderer.<ServiceInstanceDetail> of("${item.description}").withProperty("description", ServiceInstanceDetail::getDescription)).setHeader("Description");
        Column<ServiceInstanceDetail> planColumn = grid.addColumn(LitRenderer.<ServiceInstanceDetail> of("${item.plan}").withProperty("plan", ServiceInstanceDetail::getPlan)).setHeader("Plan");
        Column<ServiceInstanceDetail> applicationsColumn = grid.addColumn(LitRenderer.<ServiceInstanceDetail> of("${item.applications}").withProperty("applications", ServiceInstanceDetail::getApplicationsAsCsv)).setHeader("Bound Applications");
        Column<ServiceInstanceDetail> lastOperationColumn = grid.addColumn(LitRenderer.<ServiceInstanceDetail> of("${item.lastOperation}").withProperty("lastOperation", ServiceInstanceDetail::getLastOperation)).setHeader("Last Operation");
        Column<ServiceInstanceDetail> lastUpdatedColumn = grid.addColumn(new LocalDateTimeRenderer<ServiceInstanceDetail>(ServiceInstanceDetail::getLastUpdated, () -> dateTimeFormatter)).setHeader("Last Updated").setTextAlign(ColumnTextAlign.END);
        Column<ServiceInstanceDetail> dashboardUrlColumn = grid.addColumn(LitRenderer.<ServiceInstanceDetail> of("${item.dashboardUrl}").withProperty("dashboardUrl", ServiceInstanceDetail::getDashboardUrl)).setHeader("Dashboard URL");
        Column<ServiceInstanceDetail> requestedStateColumn = grid.addColumn(LitRenderer.<ServiceInstanceDetail> of("${item.requestedState}").withProperty("requestedState", ServiceInstanceDetail::getRequestedState)).setHeader("Requested State").setTextAlign(ColumnTextAlign.CENTER);

        HeaderRow filterRow = grid.appendHeaderRow();

        TextField foundationField = new TextField();
        foundationField.addValueChangeListener(
            event -> dataProvider.addFilter(
                f -> StringUtils.containsIgnoreCase(f.getFoundation(), foundationField.getValue())));
        foundationField.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(foundationColumn).setComponent(foundationField);
        foundationField.setSizeFull();
        foundationField.setPlaceholder("Filter");

        TextField organizationField = new TextField();
        organizationField.addValueChangeListener(
            event -> dataProvider.addFilter(
                f -> StringUtils.containsIgnoreCase(f.getOrganization(), organizationField.getValue())));
        organizationField.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(organizationColumn).setComponent(organizationField);
        organizationField.setSizeFull();
        organizationField.setPlaceholder("Filter");

        TextField spaceField = new TextField();
        spaceField.addValueChangeListener(
            event -> dataProvider.addFilter(
                f -> StringUtils.containsIgnoreCase(f.getSpace(), spaceField.getValue())));
        spaceField.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(spaceColumn).setComponent(spaceField);
        spaceField.setSizeFull();
        spaceField.setPlaceholder("Filter");

        TextField serviceInstanceIdField = new TextField();
        serviceInstanceIdField.addValueChangeListener(
            event -> dataProvider.addFilter(
                f -> StringUtils.containsIgnoreCase(f.getServiceInstanceId(), serviceInstanceIdField.getValue())));
        serviceInstanceIdField.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(serviceInstanceIdColumn).setComponent(serviceInstanceIdField);
        serviceInstanceIdField.setSizeFull();
        serviceInstanceIdField.setPlaceholder("Filter");

        TextField nameField = new TextField();
        nameField.addValueChangeListener(
            event -> dataProvider.addFilter(
                f -> StringUtils.containsIgnoreCase(f.getName(), nameField.getValue())));
        nameField.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(nameColumn).setComponent(nameField);
        nameField.setSizeFull();
        nameField.setPlaceholder("Filter");

        TextField serviceField = new TextField();
        serviceField.addValueChangeListener(
            event -> dataProvider.addFilter(
                f -> StringUtils.containsIgnoreCase(f.getService(), serviceField.getValue())));
        serviceField.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(serviceColumn).setComponent(serviceField);
        serviceField.setSizeFull();
        serviceField.setPlaceholder("Filter");

        TextField descriptionField = new TextField();
        descriptionField.addValueChangeListener(
            event -> dataProvider.addFilter(
                f -> StringUtils.containsIgnoreCase(f.getDescription(), descriptionField.getValue())));
        descriptionField.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(descriptionColumn).setComponent(descriptionField);
        descriptionField.setSizeFull();
        descriptionField.setPlaceholder("Filter");

        TextField planField = new TextField();
        planField.addValueChangeListener(
            event -> dataProvider.addFilter(
                f -> StringUtils.containsIgnoreCase(f.getPlan(), planField.getValue())));
        planField.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(planColumn).setComponent(planField);
        planField.setSizeFull();
        planField.setPlaceholder("Filter");

        TextField applicationsField = new TextField();
        applicationsField.addValueChangeListener(
            event -> dataProvider.addFilter(
                f -> StringUtils.containsIgnoreCase(f.getApplicationsAsCsv(), applicationsField.getValue())));
        applicationsField.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(applicationsColumn).setComponent(applicationsField);
        applicationsField.setSizeFull();
        applicationsField.setPlaceholder("Filter");

        TextField lastOperationField = new TextField();
        lastOperationField.addValueChangeListener(
            event -> dataProvider.addFilter(
                f -> StringUtils.containsIgnoreCase(f.getLastOperation(), lastOperationField.getValue())));
        lastOperationField.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(lastOperationColumn).setComponent(lastOperationField);
        lastOperationField.setSizeFull();
        lastOperationField.setPlaceholder("Filter");

        VerticalLayout lastUpdatedField = getLastUpdatedPicker(dataProvider);
        filterRow.getCell(lastUpdatedColumn).setComponent(lastUpdatedField);
        lastUpdatedField.setSizeFull();

        TextField dashboardUrlField = new TextField();
        dashboardUrlField.addValueChangeListener(
            event -> dataProvider.addFilter(
                f -> StringUtils.containsIgnoreCase(f.getDashboardUrl(), dashboardUrlField.getValue())));
        dashboardUrlField.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(dashboardUrlColumn).setComponent(dashboardUrlField);
        dashboardUrlField.setSizeFull();
        dashboardUrlField.setPlaceholder("Filter");

        TextField requestedStateField = new TextField();
        requestedStateField.addValueChangeListener(
            event -> dataProvider.addFilter(
                f -> StringUtils.containsIgnoreCase(f.getRequestedState(), requestedStateField.getValue())));
        requestedStateField.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(requestedStateColumn).setComponent(requestedStateField);
        requestedStateField.setSizeFull();
        requestedStateField.setPlaceholder("Filter");

        // @see https://github.com/vaadin/vaadin-grid-flow/issues/234
        for (Column<ServiceInstanceDetail> column : grid.getColumns())
            column.getElement().getParent().callJsFunction("setAttribute", "resizable", true);

        return grid;
    }

    public VerticalLayout getLastUpdatedPicker(ListDataProvider<ServiceInstanceDetail> dataProvider) {
        DatePicker startDatePicker = new DatePicker();
        startDatePicker.setPlaceholder("Start");
        startDatePicker.setSizeFull();
        DatePicker endDatePicker = new DatePicker();
        endDatePicker.setPlaceholder("End");
        endDatePicker.setSizeFull();

        startDatePicker.addValueChangeListener(event -> {
            LocalDate selectedDate = event.getValue();
            LocalDate endDate = endDatePicker.getValue();
            if (selectedDate != null) {
                endDatePicker.setMin(selectedDate.plusDays(1));
                if (endDate == null) {
                    endDatePicker.setOpened(true);
                } else {
                    dataProvider.addFilter(
                        f -> f.getLastUpdated() == null ? false : (f.getLastUpdated().toLocalDate().isAfter(selectedDate) &&
                            f.getLastUpdated().toLocalDate().isBefore(endDate)));
                }
            } else {
                endDatePicker.setMin(null);
            }
        });

        endDatePicker.addValueChangeListener(event -> {
            LocalDate selectedDate = event.getValue();
            LocalDate startDate = startDatePicker.getValue();
            if (selectedDate != null) {
                startDatePicker.setMax(selectedDate.minusDays(1));
                if (startDate != null) {
                    dataProvider.addFilter(
                        f -> f.getLastUpdated() == null ? false : (f.getLastUpdated().toLocalDate().isBefore(selectedDate) &&
                            f.getLastUpdated().toLocalDate().isAfter(startDate)));
                }
            } else {
                startDatePicker.setMax(null);
            }
        });

        VerticalLayout layout = new VerticalLayout();
        layout.add(startDatePicker, endDatePicker);
        return layout;
    }
}