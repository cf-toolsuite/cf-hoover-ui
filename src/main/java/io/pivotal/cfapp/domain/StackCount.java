package io.pivotal.cfapp.domain;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StackCount {

    private String stack;
    private Long count;

    public static List<StackCount> listOf(Map<String, Long> metrics) {
        return metrics.entrySet().stream()
            .map(e -> new StackCount(e.getKey(), e.getValue()))
            .sorted(Comparator.comparing(StackCount::getStack))
            .collect(Collectors.toList());
    }
}