package io.pivotal.cfapp.domain;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DockerImageCount {

    private String image;
    private Long count;

    public static List<DockerImageCount> listOf(Map<String, Long> metrics) {
        return metrics.entrySet().stream()
            .map(e -> new DockerImageCount(e.getKey(), e.getValue()))
            .sorted(Comparator.comparing(DockerImageCount::getImage))
            .collect(Collectors.toList());
    }
}