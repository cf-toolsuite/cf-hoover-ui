package io.pivotal.cfapp.ui.view;

import static io.pivotal.cfapp.ui.view.AppUsageReportView.NAV;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
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
        GridTile<AppUsageMonthly> tile = new GridTile<>("Application Instances » Monthly", AppUsageMonthly.class, cache.getAppUsage().getMonthlyReports(), new String[] { "year", "month", "averageAppInstances", "maximumAppInstances", "appInstanceHours" });
        firstRow.add(tile);
        firstRow.setWidth("1280px"); firstRow.setHeight("800px");
        add(title, firstRow);
        setSizeFull();
    }

}