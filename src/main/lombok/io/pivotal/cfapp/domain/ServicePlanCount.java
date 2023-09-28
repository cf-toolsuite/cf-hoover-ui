package io.pivotal.cfapp.domain;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ServicePlanCount {

    private String servicePlan;
    private Long count;

    public static List<ServicePlanCount> listOf(Map<String, Long> metrics) {
        return metrics.entrySet().stream()
            .map(e -> new ServicePlanCount(e.getKey(), e.getValue()))
            .sorted(Comparator.comparing(ServicePlanCount::getServicePlan))
            .collect(Collectors.toList());
    }
}