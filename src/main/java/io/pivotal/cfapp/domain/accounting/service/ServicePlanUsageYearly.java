package io.pivotal.cfapp.domain.accounting.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@Data
@JsonPropertyOrder({ "service_plan_name", "service_plan_guid", "year", "duration_in_hours", "maximum_instances", "average_instances"})
public class ServicePlanUsageYearly {

    @JsonProperty("service_plan_name")
    private String servicePlanName;

    @JsonProperty("service_plan_guid")
    private String servicePlanGuid;

    @JsonProperty("year")
    private Integer year;

    @JsonProperty("duration_in_hours")
    private Double durationInHours;

    @JsonProperty("maximum_instances")
    private Integer maximumInstances;

    @JsonProperty("average_instances")
    private Integer averageInstances;

    @JsonIgnore
    public boolean combine(ServicePlanUsageYearly usage) {
        boolean combined = false;
        if (usage.getYear().equals(year) && usage.getServicePlanName().equals(servicePlanName)) {
            this.durationInHours += usage.getDurationInHours();
            this.averageInstances += usage.getAverageInstances();
            this.maximumInstances += usage.getMaximumInstances();
            String newPlanGuid = String.join(",", this.servicePlanGuid, usage.getServicePlanGuid());
            this.servicePlanGuid = newPlanGuid;
            combined = true;
        }
        return combined;
    }

}
