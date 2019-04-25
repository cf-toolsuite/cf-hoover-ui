package io.pivotal.cfapp.client;

import feign.Headers;
import feign.RequestLine;
import io.pivotal.cfapp.domain.accounting.application.AppUsageReport;
import io.pivotal.cfapp.domain.accounting.service.ServiceUsageReport;
import io.pivotal.cfapp.domain.accounting.task.TaskUsageReport;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

@ReactiveFeignClient(name = "https://cf-hoover")
@Headers({ "Accept: application/json" })
public interface UsageClient {

    @RequestLine("GET /accounting/tasks")
    public Mono<TaskUsageReport> getTaskReport();

    @RequestLine("GET /accounting/applications")
    public Mono<AppUsageReport> getApplicationReport();

    @RequestLine("GET /accounting/services")
    public Mono<ServiceUsageReport> getServiceReport();

}