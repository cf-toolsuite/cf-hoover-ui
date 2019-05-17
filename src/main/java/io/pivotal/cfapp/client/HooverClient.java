package io.pivotal.cfapp.client;

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
import reactor.core.publisher.Mono;

@Service
@CircuitBreaker(name = "hooverClient")
public class HooverClient {

    private final WebClient client;

    @Autowired
    public HooverClient(WebClient client) {
        this.client = client;
    }

    public Mono<SnapshotDetail> getDetail() {
        return client
                .get()
                    .uri("/snapshot/detail")
                    .retrieve()
                    .bodyToMono(SnapshotDetail.class);
    }

    public Mono<SnapshotSummary> getSummary() {
        return client
                .get()
                    .uri("/snapshot/summary")
                    .retrieve()
                    .bodyToMono(SnapshotSummary.class);
    }

    public Mono<Demographics> getDemographics() {
        return client
                .get()
                    .uri("/snapshot/demographics")
                    .retrieve()
                    .bodyToMono(Demographics.class);
    }

    public Mono<TaskUsageReport> getTaskReport() {
        return client
                .get()
                    .uri("/accounting/tasks")
                    .retrieve()
                    .bodyToMono(TaskUsageReport.class);
    }

    public Mono<AppUsageReport> getApplicationReport() {
        return client
                .get()
                    .uri("/accounting/applications")
                    .retrieve()
                    .bodyToMono(AppUsageReport.class);
    }

    public Mono<ServiceUsageReport> getServiceReport() {
        return client
                .get()
                    .uri("/accounting/services")
                    .retrieve()
                    .bodyToMono(ServiceUsageReport.class);
    }

}
