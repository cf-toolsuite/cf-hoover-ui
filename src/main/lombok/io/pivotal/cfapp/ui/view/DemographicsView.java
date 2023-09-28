package io.pivotal.cfapp.ui.view;

import static io.pivotal.cfapp.ui.view.DemographicsView.NAV;

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

import io.pivotal.cfapp.domain.Demographic;
import io.pivotal.cfapp.domain.Demographics;
import io.pivotal.cfapp.repository.MetricCache;
import io.pivotal.cfapp.ui.MainLayout;
import io.pivotal.cfapp.ui.MetricFormatter;
import io.pivotal.cfapp.ui.component.GridTile;
import io.pivotal.cfapp.ui.component.Tile;


@Route(value = NAV, layout = MainLayout.class)
public class DemographicsView extends VerticalLayout {

    private static final long serialVersionUID = 1L;
    public static final String NAV = "snapshot/demographics";

    @Autowired
    public DemographicsView(
        MetricCache cache,
        MetricFormatter formatter) {
        // TODO Resource bundle for title and tile labels
        H2 title = new H2("Snapshot » Demographics");
        HorizontalLayout firstRow = new HorizontalLayout();
        HorizontalLayout secondRow = new HorizontalLayout();
        Demographics d = cache.getDemographics();
        Tile foundations = new Tile("Foundations", formatter.format(d.getFoundations()));
        Tile userAccounts = new Tile("Unique User Accounts", formatter.format(d.getUserAccounts()));
        Tile serviceAccounts = new Tile("Unique Service Accounts", formatter.format(d.getServiceAccounts()));
        GridTile<Demographic> tile = new GridTile<>("By foundation", buildGrid(), d.getDemographics());

        firstRow.add(foundations, userAccounts, serviceAccounts);
        firstRow.setWidth("1280px"); firstRow.setHeight("200px");
        secondRow.add(tile);
        secondRow.setWidth("1280px"); secondRow.setHeight("600px");
        add(title, firstRow, secondRow);
        setSizeFull();
    }

    private Grid<Demographic> buildGrid() {
        NumberFormat formatter = NumberFormat.getInstance();
        formatter.setMaximumFractionDigits(2);
        Grid<Demographic> grid = new Grid<>(Demographic.class, false);
        grid.addColumn(LitRenderer.<Demographic> of("${item.foundation}").withProperty("foundation", Demographic::getFoundation)).setHeader("Foundation").setTextAlign(ColumnTextAlign.CENTER).setResizable(true);
        grid.addColumn(new NumberRenderer<>(Demographic::getOrganizations, formatter)).setHeader("Organizations").setTextAlign(ColumnTextAlign.CENTER).setResizable(true);
        grid.addColumn(new NumberRenderer<>(Demographic::getSpaces, formatter)).setHeader("Spaces").setTextAlign(ColumnTextAlign.END).setResizable(true);
        grid.addColumn(new NumberRenderer<>(Demographic::getUserAccounts, formatter)).setHeader("User Accounts").setTextAlign(ColumnTextAlign.END).setResizable(true);
        grid.addColumn(new NumberRenderer<>(Demographic::getServiceAccounts, formatter)).setHeader("Service Accounts").setTextAlign(ColumnTextAlign.END).setResizable(true);
        return grid;
    }
}