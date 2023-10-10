package io.pivotal.cfapp.domain;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BuildpackCount {

    private String buildpack;
    private Long count;

    public static List<BuildpackCount> listOf(Map<String, Long> metrics) {
        return metrics.entrySet().stream()
            .map(e -> new BuildpackCount(e.getKey(), e.getValue()))
            .sorted(Comparator.comparing(BuildpackCount::getBuildpack))
            .collect(Collectors.toList());
    }
}