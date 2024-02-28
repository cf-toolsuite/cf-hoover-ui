package io.pivotal.cfapp.ui.view;

import static io.pivotal.cfapp.ui.view.SpringApplicationReportDetailView.NAV;

import java.text.NumberFormat;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.Column;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

import io.pivotal.cfapp.domain.JavaAppDetail;
import io.pivotal.cfapp.repository.MetricCache;
import io.pivotal.cfapp.ui.MainLayout;
import io.pivotal.cfapp.ui.component.GridTile;

@Route(value = NAV, layout = MainLayout.class)
public class SpringApplicationReportDetailView extends VerticalLayout {

    private static final long serialVersionUID = 1L;
    public static final String NAV = "snapshot/detail/ai/spring";

    @Autowired
    public SpringApplicationReportDetailView(
        MetricCache cache) {
        // TODO Resource bundle for title and tile labels
        H2 title = new H2("Spring Application Report » Detail");
        HorizontalLayout firstRow = new HorizontalLayout();
        GridTile<JavaAppDetail> tile = new GridTile<>("Applications", buildGrid(cache.getSpringApplicationReport().getDetails()));
        firstRow.add(tile);
        add(title, firstRow);
        firstRow.setSizeFull();
        setSizeFull();
    }

    private Grid<JavaAppDetail> buildGrid(Collection<JavaAppDetail> items) {
        NumberFormat formatter = NumberFormat.getInstance();
        formatter.setMaximumFractionDigits(2);

        Grid<JavaAppDetail> grid = new Grid<>(JavaAppDetail.class, false);
        ListDataProvider<JavaAppDetail> dataProvider = new ListDataProvider<>(items);
        grid.setItems(dataProvider);

        Column<JavaAppDetail> foundationColumn = grid.addColumn(LitRenderer.<JavaAppDetail> of("${item.foundation}").withProperty("foundation", JavaAppDetail::getFoundation)).setHeader("Foundation").setTextAlign(ColumnTextAlign.CENTER);
        Column<JavaAppDetail> organizationColumn = grid.addColumn(LitRenderer.<JavaAppDetail> of("${item.organization}").withProperty("organization", JavaAppDetail::getOrganization)).setHeader("Organization");
        Column<JavaAppDetail> spaceColumn = grid.addColumn(LitRenderer.<JavaAppDetail> of("${item.space}").withProperty("space", JavaAppDetail::getSpace)).setHeader("Space");
        Column<JavaAppDetail> appIdColumn = grid.addColumn(LitRenderer.<JavaAppDetail> of("${item.appId}").withProperty("appId", JavaAppDetail::getAppId)).setHeader("Application Id");
        Column<JavaAppDetail> appNameColumn = grid.addColumn(LitRenderer.<JavaAppDetail> of("${item.appName}").withProperty("appName", JavaAppDetail::getAppName)).setHeader("Application Name");
        Column<JavaAppDetail> dropletIdColumn = grid.addColumn(LitRenderer.<JavaAppDetail> of("${item.dropletId}").withProperty("dropletId", JavaAppDetail::getDropletId)).setHeader("Droplet Id");
        Column<JavaAppDetail> springDependenciesColumn = grid.addColumn(LitRenderer.<JavaAppDetail> of("${item.springDependencies}").withProperty("springDependencies", JavaAppDetail::getSpringDependencies)).setHeader("Spring Dependencies");

        HeaderRow filterRow = grid.appendHeaderRow();

        TextField foundationField = new TextField();
        foundationField.addValueChangeListener(
            event -> dataProvider.addFilter(
                f -> StringUtils.containsIgnoreCase(f.getFoundation(), foundationField.getValue())));
        foundationField.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(foundationColumn).setComponent(foundationField);
        foundationField.setSizeFull();
        foundationField.setPlaceholder("Filter");

        TextField organizationField = new TextField();
        organizationField.addValueChangeListener(
            event -> dataProvider.addFilter(
                f -> StringUtils.containsIgnoreCase(f.getOrganization(), organizationField.getValue())));
        organizationField.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(organizationColumn).setComponent(organizationField);
        organizationField.setSizeFull();
        organizationField.setPlaceholder("Filter");

        TextField spaceField = new TextField();
        spaceField.addValueChangeListener(
            event -> dataProvider.addFilter(
                f -> StringUtils.containsIgnoreCase(f.getSpace(), spaceField.getValue())));
        spaceField.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(spaceColumn).setComponent(spaceField);
        spaceField.setSizeFull();
        spaceField.setPlaceholder("Filter");

        TextField appIdField = new TextField();
        appIdField.addValueChangeListener(
            event -> dataProvider.addFilter(
                f -> StringUtils.containsIgnoreCase(f.getAppId(), appIdField.getValue())));
        appIdField.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(appIdColumn).setComponent(appIdField);
        appIdField.setSizeFull();
        appIdField.setPlaceholder("Filter");

        TextField appNameField = new TextField();
        appNameField.addValueChangeListener(
            event -> dataProvider.addFilter(
                f -> StringUtils.containsIgnoreCase(f.getAppName(), appNameField.getValue())));
        appNameField.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(appNameColumn).setComponent(appNameField);
        appNameField.setSizeFull();
        appNameField.setPlaceholder("Filter");

        TextField dropletIdField = new TextField();
        dropletIdField.addValueChangeListener(
            event -> dataProvider.addFilter(
                f -> StringUtils.containsIgnoreCase(f.getDropletId(), dropletIdField.getValue())));
        dropletIdField.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(dropletIdColumn).setComponent(dropletIdField);
        dropletIdField.setSizeFull();
        dropletIdField.setPlaceholder("Filter");

        TextField springDependenciesField = new TextField();
        springDependenciesField.addValueChangeListener(
            event -> dataProvider.addFilter(
                f -> StringUtils.containsIgnoreCase(f.getSpringDependencies(), springDependenciesField.getValue())));
        springDependenciesField.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(springDependenciesColumn).setComponent(springDependenciesField);
        springDependenciesField.setSizeFull();
        springDependenciesField.setPlaceholder("Filter");

        // @see https://github.com/vaadin/vaadin-grid-flow/issues/234
        for (Column<JavaAppDetail> column : grid.getColumns())
	        column.getElement().getParent().callJsFunction("setAttribute", "resizable", true);

        return grid;
    }

}