package io.pivotal.cfapp.client;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.pivotal.cfapp.domain.Demographics;
import io.pivotal.cfapp.domain.SnapshotDetail;
import io.pivotal.cfapp.domain.SnapshotSummary;
import io.pivotal.cfapp.domain.accounting.application.AppUsageReport;
import io.pivotal.cfapp.domain.accounting.service.ServiceUsageReport;
import io.pivotal.cfapp.domain.accounting.task.TaskUsageReport;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class HooverClient {

    private final WebClient client;

    @Autowired
    public HooverClient(WebClient cliene) {
        this.client = client;
    }

    @CircuitBreaker(name = "hooverClient.detail", fallbackMethod = "fallbackForDetail")
    public Mono<SnapshotDetail> getDetail() {
        return client
                .get()
                    .uri("/snapshot/detail")
                    .retrieve()
                    .bodyToMono(SnapshotDetail.class);
    }

    protected Mono<SnapshotDetail> fallbackForDetail(Exception e) {
        log.warn("Could not obtain results from call to /snapshot/detail", e);
        return Mono.just(SnapshotDetail.builder().build());
    }

    @CircuitBreaker(name = "hooverClient.summary", fallbackMethod = "fallbackForSummary")
    public Mono<SnapshotSummary> getSummary() {
        return client
                .get()
                    .uri("/snapshot/summary")
                    .retrieve()
                    .bodyToMono(SnapshotSummary.class);
    }

    protected Mono<SnapshotSummary> fallbackForSummary(Exception e) {
        log.warn("Could not obtain results from call to /snapshot/summary", e);
        return Mono.just(SnapshotSummary.builder().build());
    }

    @CircuitBreaker(name = "hooverClient.demographics", fallbackMethod = "fallbackForDemographics")
    public Mono<Demographics> getDemographics() {
        return client
                .get()
                    .uri("/snapshot/demographics")
                    .retrieve()
                    .bodyToMono(Demographics.class);
    }

    protected Mono<Demographics> fallbackForDemographics(Exception e) {
        log.warn("Could not obtain results from call to /snapshot/demographics", e);
        return Mono.just(Demographics.builder().build());
    }

    @CircuitBreaker(name = "hooverClient.taskUsageReport", fallbackMethod = "fallbackForTaskReport")
    public Mono<TaskUsageReport> getTaskReport() {
        return client
                .get()
                    .uri("/accounting/tasks")
                    .retrieve()
                    .bodyToMono(TaskUsageReport.class);
    }

    protected Mono<TaskUsageReport> fallbackForTaskReport(Exception e) {
        log.warn("Could not obtain results from call to /accounting/tasks", e);
        return Mono.just(TaskUsageReport.aggregate(Collections.emptyList()));
    }

    @CircuitBreaker(name = "hooverClient.appUsageReport", fallbackMethod = "fallbackForApplicationReport")
    public Mono<AppUsageReport> getApplicationReport() {
        return client
                .get()
                    .uri("/accounting/applications")
                    .retrieve()
                    .bodyToMono(AppUsageReport.class);
    }

    protected Mono<AppUsageReport> fallbackForApplicationReport(Exception e) {
        log.warn("Could not obtain results from call to /accounting/applications", e);
        return Mono.just(AppUsageReport.aggregate(Collections.emptyList()));
    }

    @CircuitBreaker(name = "hooverClient.serviceUsageReport", fallbackMethod = "fallbackForServiceReport")
    public Mono<ServiceUsageReport> getServiceReport() {
        return client
                .get()
                    .uri("/accounting/services")
                    .retrieve()
                    .bodyToMono(ServiceUsageReport.class);
    }

    protected Mono<ServiceUsageReport> fallbackForServiceReport(Exception e) {
        log.warn("Could not obtain results from call to /accounting/services", e);
        return Mono.just(ServiceUsageReport.aggregate(Collections.emptyList()));
    }
}
