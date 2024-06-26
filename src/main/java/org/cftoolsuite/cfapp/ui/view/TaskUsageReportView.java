package org.cftoolsuite.cfapp.ui.view;

import static org.cftoolsuite.cfapp.ui.view.TaskUsageReportView.NAV;

import java.text.NumberFormat;

import org.cftoolsuite.cfapp.domain.accounting.task.TaskUsageMonthly;
import org.cftoolsuite.cfapp.repository.MetricCache;
import org.cftoolsuite.cfapp.ui.MainLayout;
import org.cftoolsuite.cfapp.ui.component.GridTile;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.renderer.NumberRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;


@Route(value = NAV, layout = MainLayout.class)
@PageTitle("cf-hoover-ui » Accounting » Tasks")
@AnonymousAllowed
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