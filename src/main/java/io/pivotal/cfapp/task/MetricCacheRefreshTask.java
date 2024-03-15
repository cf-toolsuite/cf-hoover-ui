package io.pivotal.cfapp.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.pivotal.cfapp.client.HooverClient;
import io.pivotal.cfapp.repository.MetricCache;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MetricCacheRefreshTask implements ApplicationRunner {

    private final HooverClient hooverClient;
    private final ObjectMapper mapper;
    private final MetricCache cache;


    @Autowired
    public MetricCacheRefreshTask(
        HooverClient hooverClient,
        ObjectMapper mapper,
        MetricCache cache) {
        this.hooverClient = hooverClient;
        this.mapper = mapper;
        this.cache = cache;
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        refreshCache();
    }

    @Scheduled(cron = "${cron.collection}")
    protected void refreshCache() {
        log.info("MetricCacheRefreshTask started");
        hooverClient
            .getSummary()
                .doOnNext(r -> {
                    log.trace(mapWithException("SnapshotSummary", r));
                    cache.setSnapshotSummary(r);
                })
            .then(hooverClient.getDetail())
                .doOnNext(r -> {
                    log.trace(mapWithException("SnapshotDetail", r));
                    cache.setSnapshotDetail(r);
                })
            .then(hooverClient.getApplicationReport())
                .doOnNext(r -> {
                    log.trace(mapWithException("AppUsageReport", r));
                    cache.setAppUsage(r);
                })
            .then(hooverClient.getServiceReport())
                .doOnNext(r -> {
                    log.trace(mapWithException("ServiceUsageReport", r));
                    cache.setServiceUsage(r);
                })
            .then(hooverClient.getTaskReport())
                .doOnNext(r -> {
                    log.trace(mapWithException("TaskUsageReport", r));
                    cache.setTaskUsage(r);
                })
            .then(hooverClient.getDemographics())
                .doOnNext(r -> {
                    log.trace(mapWithException("Demographics", r));
                    cache.setDemographics(r);
                })
            .then(hooverClient.craftSpringApplicationReport())
                .doOnNext(r -> {
                    log.trace(mapWithException("SpringApplicationReport", r));
                    cache.setSpringApplicationReport(r);;
                })
            .subscribe(e -> log.info("MetricCacheRefreshTask completed"));
    }

    private String mapWithException(String type, Object value) {
        try {
            return mapper.writeValueAsString(value);
        } catch (JsonProcessingException jpe) {
            throw new RuntimeException("Problem mapping " + type);
        }
    }

}