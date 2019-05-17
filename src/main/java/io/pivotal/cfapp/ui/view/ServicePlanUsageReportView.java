package io.pivotal.cfapp.ui.view;

import static io.pivotal.cfapp.ui.view.ServicePlanUsageReportView.NAV;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
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
        GridTile<NormalizedServicePlanMonthlyUsage> tile = new GridTile<>("Service » Plans » Monthly", NormalizedServicePlanMonthlyUsage.class, NormalizedServicePlanMonthlyUsage.listOf(cache.getServiceUsage()), new String[] { "year", "month", "serviceName", "serviceGuid", "servicePlanName", "servicePlanGuid", "averageInstances", "maximumInstances", "durationInHours" });
        firstRow.add(tile);
        firstRow.setSizeFull();
        add(title, firstRow);
        setSizeFull();
    }

}