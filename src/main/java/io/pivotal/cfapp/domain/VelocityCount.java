package io.pivotal.cfapp.domain;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VelocityCount {

    private String timeframe;
    private Long count;

    public static List<VelocityCount> listOf(Map<String, Long> metrics) {
        Map<String, Long> ordinalMetrics = Timeframe.sort(metrics);
        return ordinalMetrics.entrySet().stream()
            .map(e -> new VelocityCount(e.getKey(), e.getValue()))
            .collect(Collectors.toList());
    }
}