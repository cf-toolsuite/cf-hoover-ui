package io.pivotal.cfapp.ui;

import java.time.Duration;

import javax.annotation.PostConstruct;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.material.Material;

import org.springframework.beans.factory.annotation.Autowired;

import io.pivotal.cfapp.client.HooverClient;


@Route(value = "")
@Theme(Material.class)
@PWA(name = "Hoover UI, Vaadin Flow with Spring", shortName = "Hoover UI")
public class CfHooverDashboard extends VerticalLayout {

    private static final long serialVersionUID = 1L;

    private final HooverClient client;

    @Autowired
    public CfHooverDashboard(
        HooverClient client) {
        this.client = client;
    }

    @PostConstruct
    protected void init() {
        // TODO property-drive this title thru externalized configuration
        client.getSummary().delayElement(Duration.ofMillis(500)).subscribe(s -> {
            H2 title = new H2("Hoover Dashboard");
            HorizontalLayout firstRow = new HorizontalLayout();
            VerticalLayout statLayout = new VerticalLayout();
            H3 stat = new H3(String.valueOf(s.getApplicationCounts().getTotalApplications()));
            Label statLabel = new Label("Applications");
            statLayout.add(stat, statLabel);
            firstRow.add(statLayout);
            add(title, firstRow);
            setSizeFull();
        });
        
    }
}