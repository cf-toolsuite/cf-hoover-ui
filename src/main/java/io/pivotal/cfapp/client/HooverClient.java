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
    public HooverClient(WebClient client) {
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

    protected Mono<SnapshotDetail> fallbackForDetail(Throwable t) {
        log.warn("Could not obtain results from call to /snapshot/detail", t);
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

    protected Mono<SnapshotSummary> fallbackForSummary(Throwable t) {
        log.warn("Could not obtain results from call to /snapshot/summary", t);
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

    protected Mono<Demographics> fallbackForDemographics(Throwable t) {
        log.warn("Could not obtain results from call to /snapshot/demographics", t);
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

    protected Mono<TaskUsageReport> fallbackForTaskReport(Throwable t) {
        log.warn("Could not obtain results from call to /accounting/tasks", t);
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

    protected Mono<AppUsageReport> fallbackForApplicationReport(Throwable t) {
        log.warn("Could not obtain results from call to /accounting/applications", t);
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

    protected Mono<ServiceUsageReport> fallbackForServiceReport(Throwable t) {
        log.warn("Could not obtain results from call to /accounting/services", t);
        return Mono.just(ServiceUsageReport.aggregate(Collections.emptyList()));
    }
}
