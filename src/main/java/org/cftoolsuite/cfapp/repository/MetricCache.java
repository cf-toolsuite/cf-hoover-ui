package org.cftoolsuite.cfapp.repository;

import org.cftoolsuite.cfapp.domain.Demographics;
import org.cftoolsuite.cfapp.domain.SnapshotDetail;
import org.cftoolsuite.cfapp.domain.SnapshotSummary;
import org.cftoolsuite.cfapp.domain.SpringApplicationReport;
import org.cftoolsuite.cfapp.domain.accounting.application.AppUsageReport;
import org.cftoolsuite.cfapp.domain.accounting.service.ServiceUsageReport;
import org.cftoolsuite.cfapp.domain.accounting.task.TaskUsageReport;
import org.springframework.stereotype.Component;

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
    private SpringApplicationReport springApplicationReport;

}
