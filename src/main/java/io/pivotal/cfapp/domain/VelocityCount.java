package io.pivotal.cfapp.domain;

import java.util.Comparator;
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
        return metrics.entrySet().stream()
            .map(e -> new VelocityCount(e.getKey(), e.getValue()))
            .sorted(Comparator.comparing(VelocityCount::getTimeframe))
            .collect(Collectors.toList());
    }
}