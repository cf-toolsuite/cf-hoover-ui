package io.pivotal.cfapp.domain;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ServiceCount {

    private String service;
    private Long count;

    public static List<ServiceCount> listOf(Map<String, Long> metrics) {
        return metrics.entrySet().stream()
            .map(e -> new ServiceCount(e.getKey(), e.getValue()))
            .sorted(Comparator.comparing(ServiceCount::getService))
            .collect(Collectors.toList());
    }
}