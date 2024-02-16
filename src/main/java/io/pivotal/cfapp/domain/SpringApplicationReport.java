package io.pivotal.cfapp.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;

@Builder
@Getter
@JsonPropertyOrder({"details", "dependency-frequency"})
public class SpringApplicationReport {

    @Default
    @JsonProperty("details")
    List<JavaAppDetail> details = new ArrayList<>();

    @Default
    @JsonProperty("dependency-frequency")
    Map<String, Integer> dependencyFrequency = new HashMap<>();

    @JsonCreator
    public SpringApplicationReport(
        @JsonProperty("details") List<JavaAppDetail> details,
        @JsonProperty("dependency-frequency") Map<String, Integer> dependencyFrequency
    ) {
        this.details = details;
        this.dependencyFrequency = dependencyFrequency;
    }
}
