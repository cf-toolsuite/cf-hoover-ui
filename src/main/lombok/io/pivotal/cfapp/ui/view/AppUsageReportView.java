package io.pivotal.cfapp.ui.view;

import static io.pivotal.cfapp.ui.view.AppUsageReportView.NAV;

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

import io.pivotal.cfapp.domain.accounting.application.AppUsageMonthly;
import io.pivotal.cfapp.repository.MetricCache;
import io.pivotal.cfapp.ui.MainLayout;
import io.pivotal.cfapp.ui.component.GridTile;


@Route(value = NAV, layout = MainLayout.class)
public class AppUsageReportView extends VerticalLayout {

    private static final long serialVersionUID = 1L;
    public static final String NAV = "accounting/applications";

    @Autowired
    public AppUsageReportView(
        MetricCache cache) {
        // TODO Resource bundle for title and tile labels
        H2 title = new H2("Accounting » Applications");
        HorizontalLayout firstRow = new HorizontalLayout();
        GridTile<AppUsageMonthly> tile = new GridTile<>("Application Instances » Monthly", buildGrid(), cache.getAppUsage().getMonthlyReports());
        firstRow.add(tile);
        firstRow.setWidth("1280px"); firstRow.setHeight("800px");
        add(title, firstRow);
        setSizeFull();
    }

    private Grid<AppUsageMonthly> buildGrid() {
        NumberFormat formatter = NumberFormat.getInstance();
        formatter.setMaximumFractionDigits(2);
        Grid<AppUsageMonthly> grid = new Grid<>(AppUsageMonthly.class, false);
        grid.addColumn(LitRenderer.<AppUsageMonthly> of("${item.year}").withProperty("year", AppUsageMonthly::getYear)).setHeader("Year").setTextAlign(ColumnTextAlign.CENTER).setResizable(true);
        grid.addColumn(new NumberRenderer<>(AppUsageMonthly::getMonth, formatter)).setHeader("Month").setTextAlign(ColumnTextAlign.CENTER).setResizable(true);
        grid.addColumn(new NumberRenderer<>(AppUsageMonthly::getAverageAppInstances, formatter)).setHeader("Average Application Instances").setTextAlign(ColumnTextAlign.END).setResizable(true);
        grid.addColumn(new NumberRenderer<>(AppUsageMonthly::getMaximumAppInstances, formatter)).setHeader("Maximum Application Instances").setTextAlign(ColumnTextAlign.END).setResizable(true);
        grid.addColumn(new NumberRenderer<>(AppUsageMonthly::getAppInstanceHours, formatter)).setHeader("Application Instance Hours").setTextAlign(ColumnTextAlign.END).setResizable(true);
        return grid;
    }

}