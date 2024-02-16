package io.pivotal.cfapp.ui.view;

import static io.pivotal.cfapp.ui.view.SnapshotApplicationDetailView.NAV;

import java.text.NumberFormat;
import java.util.Collection;
import java.util.stream.Collectors;

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

import io.pivotal.cfapp.repository.MetricCache;
import io.pivotal.cfapp.ui.MainLayout;
import io.pivotal.cfapp.ui.component.GridTile;

@Route(value = NAV, layout = MainLayout.class)
public class SpringApplicationReportDependencyFrequencyView extends VerticalLayout {

    private static final long serialVersionUID = 1L;
    public static final String NAV = "snapshot/summary/ai/spring";

    @Autowired
    public SpringApplicationReportDependencyFrequencyView(
        MetricCache cache) {
        // TODO Resource bundle for title and tile labels
        H2 title = new H2("Spring Application Report » Dependency Frequency");
        HorizontalLayout firstRow = new HorizontalLayout();
        GridTile<DependencyFrequency> tile =
            new GridTile<>(
                "Frequencies",
                buildGrid(
                    cache
                        .getSpringApplicationReport().getDependencyFrequency()
                            .entrySet()
                                .stream()
                                .map(entry -> new DependencyFrequency(entry.getKey(), entry.getValue()))
                                .collect(Collectors.toList())));
        firstRow.add(tile);
        add(title, firstRow);
        firstRow.setSizeFull();
        setSizeFull();
    }

    private Grid<DependencyFrequency> buildGrid(Collection<DependencyFrequency> items) {
        NumberFormat formatter = NumberFormat.getInstance();
        formatter.setMaximumFractionDigits(2);

        Grid<DependencyFrequency> grid = new Grid<>(DependencyFrequency.class, false);
        ListDataProvider<DependencyFrequency> dataProvider = new ListDataProvider<>(items);
        grid.setItems(dataProvider);

        Column<DependencyFrequency> dependencyColumn = grid.addColumn(LitRenderer.<DependencyFrequency> of("${item.dependency}").withProperty("dependency", DependencyFrequency::getDependency)).setHeader("Dependency").setTextAlign(ColumnTextAlign.CENTER);
        Column<DependencyFrequency> frequencyColumn = grid.addColumn(LitRenderer.<DependencyFrequency> of("${item.frequency}").withProperty("frequency", DependencyFrequency::getFrequency)).setHeader("Frequency");

        HeaderRow filterRow = grid.appendHeaderRow();

        TextField dependencyField = new TextField();
        dependencyField.addValueChangeListener(
            event -> dataProvider.addFilter(
                f -> StringUtils.containsIgnoreCase(f.getDependency(), dependencyField.getValue())));
        dependencyField.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(dependencyColumn).setComponent(dependencyField);
        dependencyField.setSizeFull();
        dependencyField.setPlaceholder("Filter");

        TextField frequencyField = new TextField();
        frequencyField.addValueChangeListener(
            event -> dataProvider.addFilter(
                f -> StringUtils.contains(String.valueOf(f.getFrequency()), frequencyField.getValue())));
        frequencyField.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(frequencyColumn).setComponent(frequencyField);
        frequencyField.setSizeFull();
        frequencyField.setPlaceholder("Filter");

        // @see https://github.com/vaadin/vaadin-grid-flow/issues/234
        for (Column<DependencyFrequency> column : grid.getColumns())
	        column.getElement().getParent().callJsFunction("setAttribute", "resizable", true);

        return grid;
    }

    class DependencyFrequency {

        private String dependency;
        private Integer frequency;

        DependencyFrequency(String dependency, Integer frequency) {
            this.dependency = dependency;
            this.frequency = frequency;
        }

        // Getters and setters
        String getDependency() {
            return dependency;
        }

        void setDependency(String dependency) {
            this.dependency = dependency;
        }

        Integer getFrequency() {
            return frequency;
        }

        void setFrequency(Integer frequency) {
            this.frequency = frequency;
        }
    }

}