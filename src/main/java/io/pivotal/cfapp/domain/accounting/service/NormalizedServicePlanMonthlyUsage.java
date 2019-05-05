package io.pivotal.cfapp.domain.accounting.service;

import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class NormalizedServicePlanMonthlyUsage {

    private Integer year;
    private Integer month;
    private String serviceName;
    private String serviceGuid;
    private String servicePlanName;
    private String servicePlanGuid;
    private Double durationInHours;
    private Integer averageInstances;
    private Integer maximumInstances;

    public static List<NormalizedServicePlanMonthlyUsage> listOf(ServiceUsageReport report) {
        List<NormalizedServicePlanMonthlyUsage> result = new ArrayList<>();
        List<ServiceUsageMonthlyAggregate> monthlyAggregates = report.getMonthlyServiceReports();
        for (ServiceUsageMonthlyAggregate suma: monthlyAggregates) {
            String serviceName = suma.getServiceName();
            String serviceGuid = suma.getServiceGuid();
            List<ServicePlanUsageMonthly> monthlyServicePlanUsages = suma.getPlans();
            for (ServicePlanUsageMonthly spum: monthlyServicePlanUsages) {
                String servicePlanName = spum.getServicePlanName();
                String servicePlanGuid = spum.getServicePlanGuid();
                List<ServiceUsageMonthly> monthlyServicePlanUsageMetrics = spum.getUsages();
                for (ServiceUsageMonthly metrics: monthlyServicePlanUsageMetrics) {
                    result.add(
                        NormalizedServicePlanMonthlyUsage.builder()
                            .year(metrics.getYear())
                            .month(metrics.getMonth())
                            .serviceGuid(serviceGuid)
                            .serviceName(serviceName)
                            .servicePlanGuid(servicePlanGuid)
                            .servicePlanName(servicePlanName)
                            .averageInstances(metrics.getAverageInstances())
                            .maximumInstances(metrics.getMaximumInstances())
                            .durationInHours(metrics.getDurationInHours())
                            .build()
                    );
                }
            }
        }
        return result;
    }
}