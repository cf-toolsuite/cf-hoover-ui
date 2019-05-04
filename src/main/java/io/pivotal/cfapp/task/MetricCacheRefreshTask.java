package io.pivotal.cfapp.task;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import io.pivotal.cfapp.client.HooverClient;
import io.pivotal.cfapp.repository.MetricCache;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@ConditionalOnProperty(prefix="cron", name="enabled", havingValue="true")
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
        hooverClient
            .getSummary()
                .doOnNext(r -> {
                    log.debug(mapWithException("SnapshotSummary", r));
                    cache.setSnapshotSummary(r);
                })
            .then(hooverClient.getDetail())
                .doOnNext(r -> {
                    log.debug(mapWithException("SnapshotDetail", r));
                    cache.setSnapshotDetail(r);
                })
            .then(hooverClient.getApplicationReport())
                .doOnNext(r -> {
                    log.debug(mapWithException("AppUsageReport", r));
                    cache.setAppUsage(r);
                })
            .then(hooverClient.getServiceReport())
                .doOnNext(r -> {
                    log.debug(mapWithException("ServiceUsageReport", r));
                    cache.setServiceUsage(r);
                })
            .then(hooverClient.getTaskReport())
                .doOnNext(r -> {
                    log.debug(mapWithException("TaskUsageReport", r));
                    cache.setTaskUsage(r);
                })
            .subscribe();
    }

    private String mapWithException(String type, Object value) {
        try {
            return mapper.writeValueAsString(value);
        } catch (JsonProcessingException jpe) {
            throw new RuntimeException("Problem mapping " + type);
        }
    }

}