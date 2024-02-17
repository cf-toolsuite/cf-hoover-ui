package io.pivotal.cfapp.ui.view;

import static io.pivotal.cfapp.ui.view.SpringApplicationReportDependencyFrequencyView.NAV;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.appreciated.apexcharts.ApexCharts;
import com.github.appreciated.apexcharts.ApexChartsBuilder;
import com.github.appreciated.apexcharts.config.builder.ChartBuilder;
import com.github.appreciated.apexcharts.config.builder.LegendBuilder;
import com.github.appreciated.apexcharts.config.builder.ResponsiveBuilder;
import com.github.appreciated.apexcharts.config.chart.Type;
import com.github.appreciated.apexcharts.config.legend.Position;
import com.github.appreciated.apexcharts.config.responsive.builder.OptionsBuilder;
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
        HorizontalLayout secondRow = new HorizontalLayout();

        Map<String, Integer> dependencyFrequencies = cache.getSpringApplicationReport().getDependencyFrequency();

        // Calculate total frequency
        int totalFrequency = dependencyFrequencies.values().stream().mapToInt(Integer::intValue).sum();

        // Prepare data for the chart
        List<String> labels = new ArrayList<>();
        List<Number> series = new ArrayList<>();
        dependencyFrequencies.forEach((dependency, frequency) -> {
            labels.add(dependency);
            series.add(100 * frequency / (double) totalFrequency); // Calculate percentage
        });

        ApexChartsBuilder chartBuilder = new ApexChartsBuilder();
        chartBuilder.withChart(ChartBuilder.get().withType(Type.DONUT).build())
                .withLegend(LegendBuilder.get()
                        .withPosition(Position.RIGHT)
                        .build())
                .withLabels(labels.toArray(new String[0]))
                .withSeries(series.toArray(new Double[0]))
                .withResponsive(ResponsiveBuilder.get()
                        .withBreakpoint(480.0)
                        .withOptions(OptionsBuilder.get()
                                .withLegend(LegendBuilder.get()
                                        .withPosition(Position.BOTTOM)
                                        .build())
                                .build())
                        .build());
        ApexCharts chart = chartBuilder.build();
        chart.setHeight("400px");
        firstRow.add(chart);

        GridTile<DependencyFrequency> tile =
            new GridTile<>(
                "Frequencies",
                buildGrid(
                    dependencyFrequencies
                            .entrySet()
                                .stream()
                                .map(entry -> new DependencyFrequency(entry.getKey(), entry.getValue()))
                                .collect(Collectors.toList())));
        secondRow.add(tile);

        // Set the size of the rows relative to the parent layout
        firstRow.setHeight("30%");
        secondRow.setHeight("70%");

        add(title, firstRow, secondRow);

        // Ensure the rows stretch to full width
        firstRow.setWidthFull();
        secondRow.setWidthFull();

        setSizeFull();
    }

    private Grid<DependencyFrequency> buildGrid(Collection<DependencyFrequency> items) {
        NumberFormat formatter = NumberFormat.getInstance();
        formatter.setMaximumFractionDigits(2);

        Grid<DependencyFrequency> grid = new Grid<>(DependencyFrequency.class, false);
        ListDataProvider<DependencyFrequency> dataProvider = new ListDataProvider<>(items);
        grid.setItems(dataProvider);

        Column<DependencyFrequency> dependencyColumn = grid.addColumn(LitRenderer.<DependencyFrequency> of("${item.dependency}").withProperty("dependency", DependencyFrequency::getDependency)).setHeader("Dependency").setTextAlign(ColumnTextAlign.START);
        Column<DependencyFrequency> frequencyColumn = grid.addColumn(LitRenderer.<DependencyFrequency> of("${item.frequency}").withProperty("frequency", DependencyFrequency::getFrequency)).setHeader("Frequency").setTextAlign(ColumnTextAlign.END);

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