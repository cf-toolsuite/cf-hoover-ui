package io.pivotal.cfapp.ui;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.material.Material;

import org.springframework.beans.factory.annotation.Autowired;

import io.pivotal.cfapp.domain.accounting.application.AppUsageMonthly;
import io.pivotal.cfapp.repository.MetricCache;


@Route(value = "accounting/applications")
@Theme(Material.class)
public class AppUsageReportView extends VerticalLayout {

    private static final long serialVersionUID = 1L;

    @Autowired
    public AppUsageReportView(
        MetricCache cache) {
        // TODO Resource bundle for title and tile labels
        H2 title = new H2("Accounting » Applications");
        HorizontalLayout firstRow = new HorizontalLayout();
        GridTile<AppUsageMonthly> tile = new GridTile<>("Application Instances » Monthly", AppUsageMonthly.class, cache.getAppUsage().getMonthlyReports(), new String[] { "year", "month", "averageAppInstances", "maximumAppInstances", "appInstanceHours" });
        firstRow.add(tile);
        firstRow.setWidth("1280px"); firstRow.setHeight("800px");
        add(title, firstRow);
        setSizeFull();
    }

}