package io.pivotal.cfapp.ui.view;

import static io.pivotal.cfapp.ui.view.SnapshotApplicationDetailView.NAV;

import java.text.NumberFormat;
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
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.renderer.LocalDateTimeRenderer;
import com.vaadin.flow.data.renderer.NumberRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

import org.apache.commons.lang3.StringUtils;
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
        GridTile<AppDetail> tile = new GridTile<>("Applications", buildGrid(cache.getSnapshotDetail().getApplications()));
        firstRow.add(tile);
        add(title, firstRow);
        firstRow.setSizeFull();
        setSizeFull();
    }

    private Grid<AppDetail> buildGrid(Collection<AppDetail> items) {
        NumberFormat formatter = NumberFormat.getInstance();
        formatter.setMaximumFractionDigits(2);

        Grid<AppDetail> grid = new Grid<>(AppDetail.class, false);
        ListDataProvider<AppDetail> dataProvider = new ListDataProvider<>(items);
        grid.setItems(dataProvider);

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG, FormatStyle.SHORT);

        Column<AppDetail> foundationColumn = grid.addColumn(LitRenderer.<AppDetail> of("${item.foundation}").withProperty("foundation", AppDetail::getFoundation)).setHeader("Foundation").setTextAlign(ColumnTextAlign.CENTER);
        Column<AppDetail> organizationColumn = grid.addColumn(LitRenderer.<AppDetail> of("${item.organization}").withProperty("organization", AppDetail::getOrganization)).setHeader("Organization");
        Column<AppDetail> spaceColumn = grid.addColumn(LitRenderer.<AppDetail> of("${item.space}").withProperty("space", AppDetail::getSpace)).setHeader("Space");
        Column<AppDetail> appIdColumn = grid.addColumn(LitRenderer.<AppDetail> of("${item.appId}").withProperty("appId", AppDetail::getAppId)).setHeader("Application Id");
        Column<AppDetail> appNameColumn = grid.addColumn(LitRenderer.<AppDetail> of("${item.appName}").withProperty("appName", AppDetail::getAppName)).setHeader("Application Name");
        Column<AppDetail> buildpackColumn = grid.addColumn(LitRenderer.<AppDetail> of("${item.buildpack}").withProperty("buildpack", AppDetail::getBuildpack)).setHeader("Buildpack");
        Column<AppDetail> buildpackVersionColumn = grid.addColumn(LitRenderer.<AppDetail> of("${item.buildpackVersion}").withProperty("buildpackVersion", AppDetail::getBuildpackVersion)).setHeader("Buildpack Version");
        Column<AppDetail> imageColumn = grid.addColumn(LitRenderer.<AppDetail> of("${item.image}").withProperty("image", AppDetail::getImage)).setHeader("Docker Image");
        Column<AppDetail> stackColumn = grid.addColumn(LitRenderer.<AppDetail> of("${item.stack}").withProperty("stack", AppDetail::getStack)).setHeader("Stack");
        Column<AppDetail> runningInstancesColumn = grid.addColumn(new NumberRenderer<>(AppDetail::getRunningInstances, formatter)).setHeader("Running Instances").setTextAlign(ColumnTextAlign.END);
        Column<AppDetail> totalInstancesColumn = grid.addColumn(new NumberRenderer<>(AppDetail::getTotalInstances, formatter)).setHeader("Total Instances").setTextAlign(ColumnTextAlign.END);
        Column<AppDetail> memUsedColumn = grid.addColumn(new NumberRenderer<>(AppDetail::getMemoryUsedInMb, formatter)).setHeader("Memory Usage (in Mb)").setTextAlign(ColumnTextAlign.END);
        Column<AppDetail> memQuotaColumn = grid.addColumn(new NumberRenderer<>(AppDetail::getMemoryQuotaInMb, formatter)).setHeader("Memory Quota (in Mb)").setTextAlign(ColumnTextAlign.END);
        Column<AppDetail> diskUsedColumn = grid.addColumn(new NumberRenderer<>(AppDetail::getDiskUsedInMb, formatter)).setHeader("Disk Usage (in Mb)").setTextAlign(ColumnTextAlign.END);
        Column<AppDetail> diskQuotaColumn = grid.addColumn(new NumberRenderer<>(AppDetail::getDiskQuotaInMb, formatter)).setHeader("Disk Quota (in Mb)").setTextAlign(ColumnTextAlign.END);
        Column<AppDetail> urlsColumn = grid.addColumn(LitRenderer.<AppDetail> of("${item.urls}").withProperty("urls", AppDetail::getUrlsAsCsv)).setHeader("Routes");
        Column<AppDetail> lastPushedColumn = grid.addColumn(new LocalDateTimeRenderer<AppDetail>(AppDetail::getLastPushed, () -> dateTimeFormatter)).setHeader("Last Pushed").setTextAlign(ColumnTextAlign.END);
        Column<AppDetail> lastEventColumn = grid.addColumn(LitRenderer.<AppDetail> of("${item.lastEvent}").withProperty("lastEvent", AppDetail::getLastEvent)).setHeader("Last Event");
        Column<AppDetail> lastEventActorColumn = grid.addColumn(LitRenderer.<AppDetail> of("${item.lastEventActor}").withProperty("lastEventActor", AppDetail::getLastEventActor)).setHeader("Last Event Actor");
        Column<AppDetail> lastEventTimeColumn = grid.addColumn(new LocalDateTimeRenderer<AppDetail>(AppDetail::getLastEventTime, () -> dateTimeFormatter)).setHeader("Last Event Time").setTextAlign(ColumnTextAlign.END);
        Column<AppDetail> requestedStateColumn = grid.addColumn(LitRenderer.<AppDetail> of("${item.requestedState}").withProperty("requestedState", AppDetail::getRequestedState)).setHeader("Requested State").setTextAlign(ColumnTextAlign.CENTER);

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

        TextField appIdField = new TextField();
        appIdField.addValueChangeListener(
            event -> dataProvider.addFilter(
                f -> StringUtils.containsIgnoreCase(f.getAppId(), appIdField.getValue())));
        appIdField.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(appIdColumn).setComponent(appIdField);
        appIdField.setSizeFull();
        appIdField.setPlaceholder("Filter");

        TextField appNameField = new TextField();
        appNameField.addValueChangeListener(
            event -> dataProvider.addFilter(
                f -> StringUtils.containsIgnoreCase(f.getAppName(), appNameField.getValue())));
        appNameField.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(appNameColumn).setComponent(appNameField);
        appNameField.setSizeFull();
        appNameField.setPlaceholder("Filter");

        TextField buildpackField = new TextField();
        buildpackField.addValueChangeListener(
            event -> dataProvider.addFilter(
                f -> StringUtils.containsIgnoreCase(f.getBuildpack(), buildpackField.getValue())));
        buildpackField.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(buildpackColumn).setComponent(buildpackField);
        buildpackField.setSizeFull();
        buildpackField.setPlaceholder("Filter");

        TextField buildpackVersionField = new TextField();
        buildpackVersionField.addValueChangeListener(
            event -> dataProvider.addFilter(
                f -> StringUtils.containsIgnoreCase(f.getBuildpackVersion(), buildpackVersionField.getValue())));
        buildpackVersionField.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(buildpackVersionColumn).setComponent(buildpackVersionField);
        buildpackVersionField.setSizeFull();
        buildpackVersionField.setPlaceholder("Filter");

        TextField imageField = new TextField();
        imageField.addValueChangeListener(
            event -> dataProvider.addFilter(
                f -> StringUtils.containsIgnoreCase(f.getImage(), imageField.getValue())));
        imageField.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(imageColumn).setComponent(imageField);
        imageField.setSizeFull();
        imageField.setPlaceholder("Filter");

        TextField stackField = new TextField();
        stackField.addValueChangeListener(
            event -> dataProvider.addFilter(
                f -> StringUtils.containsIgnoreCase(f.getStack(), stackField.getValue())));
        stackField.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(stackColumn).setComponent(stackField);
        stackField.setSizeFull();
        stackField.setPlaceholder("Filter");

        TextField runningInstancesField = new TextField();
        runningInstancesField.addValueChangeListener(
            event -> dataProvider.addFilter(
                f -> StringUtils.contains(String.valueOf(f.getRunningInstances()), runningInstancesField.getValue())));
        runningInstancesField.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(runningInstancesColumn).setComponent(runningInstancesField);
        runningInstancesField.setSizeFull();
        runningInstancesField.setPlaceholder("Filter");

        TextField totalInstancesField = new TextField();
        totalInstancesField.addValueChangeListener(
            event -> dataProvider.addFilter(
                f -> StringUtils.contains(String.valueOf(f.getTotalInstances()), totalInstancesField.getValue())));
        totalInstancesField.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(totalInstancesColumn).setComponent(totalInstancesField);
        totalInstancesField.setSizeFull();
        totalInstancesField.setPlaceholder("Filter");

        TextField memUsedField = new TextField();
        memUsedField.addValueChangeListener(
            event -> dataProvider.addFilter(
                f -> StringUtils.contains(String.valueOf(f.getMemoryUsed()), memUsedField.getValue())));
        memUsedField.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(memUsedColumn).setComponent(memUsedField);
        memUsedField.setSizeFull();
        memUsedField.setPlaceholder("Filter");

        TextField diskUsedField = new TextField();
        diskUsedField.addValueChangeListener(
            event -> dataProvider.addFilter(
                f -> StringUtils.contains(String.valueOf(f.getDiskUsed()), diskUsedField.getValue())));
        diskUsedField.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(diskUsedColumn).setComponent(diskUsedField);
        diskUsedField.setSizeFull();
        diskUsedField.setPlaceholder("Filter");

        TextField memQuotaField = new TextField();
        memQuotaField.addValueChangeListener(
            event -> dataProvider.addFilter(
                f -> StringUtils.contains(String.valueOf(f.getMemoryQuota()), memQuotaField.getValue())));
        memQuotaField.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(memQuotaColumn).setComponent(memQuotaField);
        memQuotaField.setSizeFull();
        memQuotaField.setPlaceholder("Filter");

        TextField diskQuotaField = new TextField();
        diskQuotaField.addValueChangeListener(
            event -> dataProvider.addFilter(
                f -> StringUtils.contains(String.valueOf(f.getDiskQuota()), diskQuotaField.getValue())));
        diskQuotaField.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(diskQuotaColumn).setComponent(diskQuotaField);
        diskQuotaField.setSizeFull();
        diskQuotaField.setPlaceholder("Filter");

        TextField urlsField = new TextField();
        urlsField.addValueChangeListener(
            event -> dataProvider.addFilter(
                f -> StringUtils.containsIgnoreCase(f.getUrlsAsCsv(), urlsField.getValue())));
        urlsField.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(urlsColumn).setComponent(urlsField);
        urlsField.setSizeFull();
        urlsField.setPlaceholder("Filter");

        VerticalLayout lastPushedField = getLastPushedPicker(dataProvider);
        filterRow.getCell(lastPushedColumn).setComponent(lastPushedField);
        lastPushedField.setSizeFull();

        TextField lastEventField = new TextField();
        lastEventField.addValueChangeListener(
            event -> dataProvider.addFilter(
                f -> StringUtils.containsIgnoreCase(f.getLastEvent(), lastEventField.getValue())));
        lastEventField.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(lastEventColumn).setComponent(lastEventField);
        lastEventField.setSizeFull();
        lastEventField.setPlaceholder("Filter");

        TextField lastEventActorField = new TextField();
        lastEventActorField.addValueChangeListener(
            event -> dataProvider.addFilter(
                f -> StringUtils.containsIgnoreCase(f.getLastEventActor(), lastEventActorField.getValue())));
        lastEventActorField.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(lastEventActorColumn).setComponent(lastEventActorField);
        lastEventActorField.setSizeFull();
        lastEventActorField.setPlaceholder("Filter");

        VerticalLayout lastEventTimeField = getLastPushedPicker(dataProvider);
        filterRow.getCell(lastEventTimeColumn).setComponent(lastEventTimeField);
        lastEventTimeField.setSizeFull();

        TextField requestedStateField = new TextField();
        requestedStateField.addValueChangeListener(
            event -> dataProvider.addFilter(
                f -> StringUtils.containsIgnoreCase(f.getRequestedState(), requestedStateField.getValue())));
        requestedStateField.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(requestedStateColumn).setComponent(requestedStateField);
        requestedStateField.setSizeFull();
        requestedStateField.setPlaceholder("Filter");

        // @see https://github.com/vaadin/vaadin-grid-flow/issues/234
        for (Column<AppDetail> column : grid.getColumns())
	        column.getElement().getParent().callJsFunction("setAttribute", "resizable", true);

        return grid;
    }

    public VerticalLayout getLastPushedPicker(ListDataProvider<AppDetail> dataProvider) {
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
                        f -> f.getLastPushed() == null ? false : (f.getLastPushed().toLocalDate().isAfter(selectedDate) &&
                            f.getLastPushed().toLocalDate().isBefore(endDate)));
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
                        f -> f.getLastPushed() == null ? false : (f.getLastPushed().toLocalDate().isBefore(selectedDate) &&
                            f.getLastPushed().toLocalDate().isAfter(startDate)));
                }
            } else {
                startDatePicker.setMax(null);
            }
        });

        VerticalLayout layout = new VerticalLayout();
        layout.add(startDatePicker, endDatePicker);
        return layout;
    }

    public VerticalLayout getLastEventTimePicker(ListDataProvider<AppDetail> dataProvider) {
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
                        f -> f.getLastEventTime() == null ? false : (f.getLastEventTime().toLocalDate().isAfter(selectedDate) &&
                            f.getLastEventTime().toLocalDate().isBefore(endDate)));
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
                        f -> f.getLastEventTime() == null ? false : (f.getLastEventTime().toLocalDate().isBefore(selectedDate) &&
                            f.getLastEventTime().toLocalDate().isAfter(startDate)));
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