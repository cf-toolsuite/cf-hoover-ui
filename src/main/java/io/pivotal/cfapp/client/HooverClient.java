package io.pivotal.cfapp.client;

import org.springframework.web.bind.annotation.GetMapping;

import feign.Headers;
import io.pivotal.cfapp.domain.SnapshotDetail;
import io.pivotal.cfapp.domain.SnapshotSummary;
import io.pivotal.cfapp.domain.accounting.application.AppUsageReport;
import io.pivotal.cfapp.domain.accounting.service.ServiceUsageReport;
import io.pivotal.cfapp.domain.accounting.task.TaskUsageReport;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

@ReactiveFeignClient(name = "cf-hoover", fallbackFactory = HooverClientFallbackFactory.class)
@Headers({ "Accept: application/json" })
public interface HooverClient {

	@GetMapping("snapshot/detail")
	public Mono<SnapshotDetail> getDetail();

	@GetMapping("snapshot/summary")
    public Mono<SnapshotSummary> getSummary();
    
    @GetMapping("accounting/tasks")
    public Mono<TaskUsageReport> getTaskReport();

    @GetMapping("accounting/applications")
    public Mono<AppUsageReport> getApplicationReport();

    @GetMapping("accounting/services")
    public Mono<ServiceUsageReport> getServiceReport();

}
