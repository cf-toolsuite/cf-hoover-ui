package io.pivotal.cfapp.ui.view;

import static io.pivotal.cfapp.ui.view.ServiceUsageReportView.NAV;

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

import io.pivotal.cfapp.domain.accounting.service.NormalizedServiceMonthlyUsage;
import io.pivotal.cfapp.repository.MetricCache;
import io.pivotal.cfapp.ui.MainLayout;
import io.pivotal.cfapp.ui.component.GridTile;


@Route(value = NAV, layout = MainLayout.class)
public class ServiceUsageReportView extends VerticalLayout {

    private static final long serialVersionUID = 1L;
    public static final String NAV = "accounting/services";

    @Autowired
    public ServiceUsageReportView(
        MetricCache cache) {
        // TODO Resource bundle for title and tile labels
        H2 title = new H2("Accounting » Services");
        HorizontalLayout firstRow = new HorizontalLayout();
        GridTile<NormalizedServiceMonthlyUsage> tile = new GridTile<>("Services » Monthly", buildGrid(), NormalizedServiceMonthlyUsage.listOf(cache.getServiceUsage()));
        firstRow.add(tile);
        firstRow.setWidth("1280px"); firstRow.setHeight("800px");
        add(title, firstRow);
        setSizeFull();
    }

    private Grid<NormalizedServiceMonthlyUsage> buildGrid() {
        NumberFormat formatter = NumberFormat.getInstance();
        formatter.setMaximumFractionDigits(2);
        Grid<NormalizedServiceMonthlyUsage> grid = new Grid<>(NormalizedServiceMonthlyUsage.class, false);
        grid.addColumn(LitRenderer.<NormalizedServiceMonthlyUsage> of("${item.year}").withProperty("year", NormalizedServiceMonthlyUsage::getYear)).setHeader("Year").setTextAlign(ColumnTextAlign.CENTER).setResizable(true);
        grid.addColumn(new NumberRenderer<>(NormalizedServiceMonthlyUsage::getMonth, formatter)).setHeader("Month").setTextAlign(ColumnTextAlign.CENTER).setResizable(true);
        grid.addColumn(LitRenderer.<NormalizedServiceMonthlyUsage> of("${item.serviceName}").withProperty("serviceName", NormalizedServiceMonthlyUsage::getServiceName)).setHeader("Service Name").setResizable(true);
        grid.addColumn(LitRenderer.<NormalizedServiceMonthlyUsage> of("${item.serviceGuid}").withProperty("serviceGuid", NormalizedServiceMonthlyUsage::getServiceGuid)).setHeader("Service Guid").setResizable(true);
        grid.addColumn(new NumberRenderer<>(NormalizedServiceMonthlyUsage::getAverageInstances, formatter)).setHeader("Average Instances").setTextAlign(ColumnTextAlign.END).setResizable(true);
        grid.addColumn(new NumberRenderer<>(NormalizedServiceMonthlyUsage::getMaximumInstances, formatter)).setHeader("Maximum Instances").setTextAlign(ColumnTextAlign.END).setResizable(true);
        grid.addColumn(new NumberRenderer<>(NormalizedServiceMonthlyUsage::getDurationInHours, formatter)).setHeader("Duration In Hours").setTextAlign(ColumnTextAlign.END).setResizable(true);
        return grid;
    }
}