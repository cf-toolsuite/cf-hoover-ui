package io.pivotal.cfapp.ui.view;

import static io.pivotal.cfapp.ui.view.TaskUsageReportView.NAV;

import java.text.NumberFormat;

import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.NumberRenderer;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.router.Route;

import org.springframework.beans.factory.annotation.Autowired;

import io.pivotal.cfapp.domain.accounting.task.TaskUsageMonthly;
import io.pivotal.cfapp.repository.MetricCache;
import io.pivotal.cfapp.ui.MainLayout;
import io.pivotal.cfapp.ui.component.GridTile;


@Route(value = NAV, layout = MainLayout.class)
public class TaskUsageReportView extends VerticalLayout {

    private static final long serialVersionUID = 1L;
    public static final String NAV = "accounting/tasks";

    @Autowired
    public TaskUsageReportView(
        MetricCache cache) {
        // TODO Resource bundle for title and tile labels
        H2 title = new H2("Accounting » Tasks");
        HorizontalLayout firstRow = new HorizontalLayout();
        GridTile<TaskUsageMonthly> tile = new GridTile<>("Tasks » Monthly", buildGrid(), cache.getTaskUsage().getMonthlyReports());
        firstRow.add(tile);
        firstRow.setWidth("1280px"); firstRow.setHeight("800px");
        add(title, firstRow);
        setSizeFull();
    }

    private Grid<TaskUsageMonthly> buildGrid() {
        NumberFormat formatter = NumberFormat.getInstance();
        formatter.setMaximumFractionDigits(2);
        Grid<TaskUsageMonthly> grid = new Grid<>(TaskUsageMonthly.class, false);
        grid.addColumn(LitRenderer.<TaskUsageMonthly> of("${item.year}").withProperty("year", TaskUsageMonthly::getYear)).setHeader("Year").setTextAlign(ColumnTextAlign.CENTER).setResizable(true);
        grid.addColumn(new NumberRenderer<>(TaskUsageMonthly::getMonth, formatter)).setHeader("Month").setTextAlign(ColumnTextAlign.CENTER).setResizable(true);
        grid.addColumn(new NumberRenderer<>(TaskUsageMonthly::getTotalTaskRuns, formatter)).setHeader("Total Task Runs").setTextAlign(ColumnTextAlign.END).setResizable(true);
        grid.addColumn(new NumberRenderer<>(TaskUsageMonthly::getMaximumConcurrentTasks, formatter)).setHeader("Maximum Concurrent Tasks").setTextAlign(ColumnTextAlign.END).setResizable(true);
        grid.addColumn(new NumberRenderer<>(TaskUsageMonthly::getTaskHours, formatter)).setHeader("Task Hours").setTextAlign(ColumnTextAlign.END).setResizable(true);
        return grid;
    }

}