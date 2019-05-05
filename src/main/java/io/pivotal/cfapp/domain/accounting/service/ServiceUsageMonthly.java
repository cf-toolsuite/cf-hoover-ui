package io.pivotal.cfapp.domain.accounting.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@Data
@JsonPropertyOrder({"month", "year", "duration_in_hours", "average_instances", "maximum_instances"})
public class ServiceUsageMonthly {

    @JsonProperty("month")
    private Integer month;

    @JsonProperty("year")
    private Integer year;

    @JsonProperty("duration_in_hours")
    private Double durationInHours;

    @JsonProperty("average_instances")
    private Integer averageInstances;

    @JsonProperty("maximum_instances")
    private Integer maximumInstances;

    @JsonIgnore
    public boolean combine(ServiceUsageMonthly usage) {
        boolean combined = false;
        if (usage.getYear().equals(year) && usage.getMonth().equals(month)) {
            this.durationInHours += usage.getDurationInHours();
            this.averageInstances += usage.getAverageInstances();
            this.maximumInstances += usage.getMaximumInstances();
            combined = true;
        }
        return combined;
    }

}
