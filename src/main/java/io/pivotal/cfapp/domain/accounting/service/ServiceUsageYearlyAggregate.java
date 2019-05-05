package io.pivotal.cfapp.domain.accounting.service;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@Data
@JsonPropertyOrder({"service_name", "service_guid", "year", "duration_in_hours", "maximum_instances", "average_instances", "plans"})
public class ServiceUsageYearlyAggregate {

    @JsonProperty("service_name")
    private String serviceName;

    @JsonProperty("service_guid")
    private String serviceGuid;

    @JsonProperty("year")
    private Integer year;

    @JsonProperty("duration_in_hours")
    private Double durationInHours;

    @JsonProperty("maximum_instances")
    private Integer maximumInstances;

    @JsonProperty("average_instances")
    private Integer averageInstances;

    @JsonProperty("plans")
    private List<ServicePlanUsageYearly> plans;

    @JsonIgnore
    public boolean combine(ServiceUsageYearlyAggregate usage) {
        boolean combined = false;
        if (usage.getYear().equals(year) && usage.getServiceName().equals(serviceName)) {
            this.averageInstances += usage.getAverageInstances();
            this.maximumInstances += usage.getMaximumInstances();
            this.durationInHours += usage.getDurationInHours();
            for (ServicePlanUsageYearly pu: usage.getPlans()) {
                for (ServicePlanUsageYearly spu: plans) {
                    if(!spu.combine(pu)) {
                        plans.add(pu);
                    }
                }
            }
            if (!usage.getServiceGuid().contains(this.serviceGuid)) {
                String newGuid = String.join(",", this.serviceGuid, usage.getServiceGuid());
                this.serviceGuid = newGuid;
            }
            combined = true;
        }
        return combined;
    }
}
