package io.pivotal.cfapp.ui.view;

import static io.pivotal.cfapp.ui.view.ServicePlanUsageReportView.NAV;

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

import io.pivotal.cfapp.domain.accounting.service.NormalizedServicePlanMonthlyUsage;
import io.pivotal.cfapp.repository.MetricCache;
import io.pivotal.cfapp.ui.MainLayout;
import io.pivotal.cfapp.ui.component.GridTile;


@Route(value = NAV, layout = MainLayout.class)
public class ServicePlanUsageReportView extends VerticalLayout {

    private static final long serialVersionUID = 1L;
    public static final String NAV = "accounting/service/plans";

    @Autowired
    public ServicePlanUsageReportView(
        MetricCache cache) {
        // TODO Resource bundle for title and tile labels
        H2 title = new H2("Accounting » Service » Plans");
        HorizontalLayout firstRow = new HorizontalLayout();
        GridTile<NormalizedServicePlanMonthlyUsage> tile = new GridTile<>("Service » Plans » Monthly", buildGrid(), NormalizedServicePlanMonthlyUsage.listOf(cache.getServiceUsage()));
        firstRow.add(tile);
        firstRow.setSizeFull();
        add(title, firstRow);
        setSizeFull();
    }

    private Grid<NormalizedServicePlanMonthlyUsage> buildGrid() {
        NumberFormat formatter = NumberFormat.getInstance();
        formatter.setMaximumFractionDigits(2);
        Grid<NormalizedServicePlanMonthlyUsage> grid = new Grid<>(NormalizedServicePlanMonthlyUsage.class, false);
        grid.addColumn(LitRenderer.<NormalizedServicePlanMonthlyUsage> of("${item.year}").withProperty("year", NormalizedServicePlanMonthlyUsage::getYear)).setHeader("Year").setTextAlign(ColumnTextAlign.CENTER).setResizable(true);
        grid.addColumn(new NumberRenderer<>(NormalizedServicePlanMonthlyUsage::getMonth, formatter)).setHeader("Month").setTextAlign(ColumnTextAlign.CENTER).setResizable(true);
        grid.addColumn(LitRenderer.<NormalizedServicePlanMonthlyUsage> of("${item.serviceName}").withProperty("serviceName", NormalizedServicePlanMonthlyUsage::getServiceName)).setHeader("Service Name").setResizable(true);
        grid.addColumn(LitRenderer.<NormalizedServicePlanMonthlyUsage> of("${item.serviceGuid}").withProperty("serviceGuid", NormalizedServicePlanMonthlyUsage::getServiceGuid)).setHeader("Service Guid").setResizable(true);
        grid.addColumn(LitRenderer.<NormalizedServicePlanMonthlyUsage> of("${item.servicePlanName}").withProperty("servicePlanName", NormalizedServicePlanMonthlyUsage::getServicePlanName)).setHeader("Service Plan Name").setResizable(true);
        grid.addColumn(LitRenderer.<NormalizedServicePlanMonthlyUsage> of("${item.servicePlanGuid}").withProperty("servicePlanGuid", NormalizedServicePlanMonthlyUsage::getServicePlanGuid)).setHeader("Service Plan Guid").setResizable(true);
        grid.addColumn(new NumberRenderer<>(NormalizedServicePlanMonthlyUsage::getAverageInstances, formatter)).setHeader("Average Instances").setTextAlign(ColumnTextAlign.END).setResizable(true);
        grid.addColumn(new NumberRenderer<>(NormalizedServicePlanMonthlyUsage::getMaximumInstances, formatter)).setHeader("Maximum Instances").setTextAlign(ColumnTextAlign.END).setResizable(true);
        grid.addColumn(new NumberRenderer<>(NormalizedServicePlanMonthlyUsage::getDurationInHours, formatter)).setHeader("Duration In Hours").setTextAlign(ColumnTextAlign.END).setResizable(true);
        return grid;
    }

}