package org.cftoolsuite.cfapp.ui.view;

import static org.cftoolsuite.cfapp.ui.view.SpringApplicationReportDependencyFrequencyView.NAV;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.cftoolsuite.cfapp.repository.MetricCache;
import org.cftoolsuite.cfapp.ui.MainLayout;
import org.cftoolsuite.cfapp.ui.component.GridTile;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.appreciated.apexcharts.ApexCharts;
import com.github.appreciated.apexcharts.ApexChartsBuilder;
import com.github.appreciated.apexcharts.config.builder.ChartBuilder;
import com.github.appreciated.apexcharts.config.builder.LegendBuilder;
import com.github.appreciated.apexcharts.config.builder.ResponsiveBuilder;
import com.github.appreciated.apexcharts.config.chart.Type;
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
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route(value = NAV, layout = MainLayout.class)
@PageTitle("cf-hoover-ui » Spring Application Report » Dependency Frequency")
@AnonymousAllowed
public class SpringApplicationReportDependencyFrequencyView extends VerticalLayout {

    private static final long serialVersionUID = 1L;
    public static final String NAV = "snapshot/summary/ai/spring";

    private final ApexCharts chart;

    @Autowired
    public SpringApplicationReportDependencyFrequencyView(
        MetricCache cache) {
        // TODO Resource bundle for title and tile labels
        H2 title = new H2("Spring Application Report » Dependency Frequency");

        HorizontalLayout firstRow = new HorizontalLayout();
        HorizontalLayout secondRow = new HorizontalLayout();

        Map<String, Integer> dependencyFrequencies = cache.getSpringApplicationReport().getDependencyFrequency();
        Collection<DependencyFrequency> items =
            dependencyFrequencies
                .entrySet()
                    .stream()
                    .map(entry -> new DependencyFrequency(entry.getKey(), entry.getValue()))
                    .collect(Collectors.toList());

        ApexChartsBuilder chartBuilder = new ApexChartsBuilder();
        chartBuilder.withChart(ChartBuilder.get().withType(Type.DONUT).build())
            .withLegend(LegendBuilder.get().withShow(false).build())
            .withResponsive(ResponsiveBuilder.get()
                .withBreakpoint(480.0)
                .withOptions(OptionsBuilder.get()
                    .withLegend(LegendBuilder.get()
                        .withShow(false)
                        .build())
                    .build())
                .build());
        chart = chartBuilder.build();
        updateChartData(chart, items);
        chart.setHeight("320px");
        firstRow.add(chart);

        GridTile<DependencyFrequency> tile =
            new GridTile<>(
                "Frequencies",
                buildGrid(items)
            );
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
            event -> {
                dataProvider.addFilter(
                    f -> StringUtils.containsIgnoreCase(f.getDependency(), dependencyField.getValue())
                );
                Collection<DependencyFrequency> filteredItems =
                    dataProvider
                        .getItems()
                        .stream()
                        .filter(f -> StringUtils.containsIgnoreCase(f.getDependency(), dependencyField.getValue()))
                        .collect(Collectors.toList());
                updateChartData(chart, filteredItems);
            }
        );
        dependencyField.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(dependencyColumn).setComponent(dependencyField);
        dependencyField.setSizeFull();
        dependencyField.setPlaceholder("Filter");

        TextField frequencyField = new TextField();
        frequencyField.addValueChangeListener(
            event -> {
                dataProvider.addFilter(
                    f -> StringUtils.contains(String.valueOf(f.getFrequency()), frequencyField.getValue())
                );
                Collection<DependencyFrequency> filteredItems =
                    dataProvider
                        .getItems()
                        .stream()
                        .filter(f -> StringUtils.contains(String.valueOf(f.getFrequency()), frequencyField.getValue()))
                        .collect(Collectors.toList());
                updateChartData(chart, filteredItems);
            }
        );
        frequencyField.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(frequencyColumn).setComponent(frequencyField);
        frequencyField.setSizeFull();
        frequencyField.setPlaceholder("Filter");

        // @see https://github.com/vaadin/vaadin-grid-flow/issues/234
        for (Column<DependencyFrequency> column : grid.getColumns())
	        column.getElement().getParent().callJsFunction("setAttribute", "resizable", true);

        return grid;
    }

    private void updateChartData(ApexCharts chart, Collection<DependencyFrequency> items) {
        int totalFrequency = items.stream().mapToInt(DependencyFrequency::getFrequency).sum();
        List<String> labels = new ArrayList<>();
        List<Number> series = new ArrayList<>();
        for (DependencyFrequency item : items) {
            labels.add(item.getDependency());
            series.add(100 * item.getFrequency() / (double) totalFrequency);
        }
        chart.updateSeries(series.toArray(new Double[0]));
        chart.setLabels(labels.toArray(new String[0]));
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