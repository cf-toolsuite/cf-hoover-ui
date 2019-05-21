package io.pivotal.cfapp.ui;

import java.text.NumberFormat;

import org.springframework.stereotype.Component;

@Component
public class MetricFormatter {

    private final NumberFormat formatter;

    public MetricFormatter() {
        formatter = NumberFormat.getNumberInstance();
        formatter.setMaximumFractionDigits(2);
    }

    public String format(Long metric) {
        return metric != null ? formatter.format(metric): "0";
    }

    public String format(Integer metric) {
        return metric != null ? formatter.format(metric): "0";
    }

    public String format(Double metric) {
        return metric != null ? formatter.format(metric): "0.0";
    }
}
