package io.pivotal.cfapp.ui.view;

import static io.pivotal.cfapp.ui.view.ServiceUsageReportView.NAV;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
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
        GridTile<NormalizedServiceMonthlyUsage> tile = new GridTile<>("Services » Monthly", NormalizedServiceMonthlyUsage.class, NormalizedServiceMonthlyUsage.listOf(cache.getServiceUsage()), new String[] { "year", "month", "serviceName", "serviceGuid", "averageInstances", "maximumInstances", "durationInHours" });
        firstRow.add(tile);
        firstRow.setWidth("1280px"); firstRow.setHeight("800px");
        add(title, firstRow);
        setSizeFull();
    }

}