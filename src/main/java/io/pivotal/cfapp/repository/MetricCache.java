package io.pivotal.cfapp.repository;

import org.springframework.stereotype.Component;

import io.pivotal.cfapp.domain.Demographics;
import io.pivotal.cfapp.domain.SnapshotDetail;
import io.pivotal.cfapp.domain.SnapshotSummary;
import io.pivotal.cfapp.domain.accounting.application.AppUsageReport;
import io.pivotal.cfapp.domain.accounting.service.ServiceUsageReport;
import io.pivotal.cfapp.domain.accounting.task.TaskUsageReport;
import lombok.Data;

@Data
@Component
public class MetricCache {

    private SnapshotDetail snapshotDetail;
    private SnapshotSummary snapshotSummary;
    private AppUsageReport appUsage;
    private ServiceUsageReport serviceUsage;
    private TaskUsageReport taskUsage;
    private Demographics demographics;

}
