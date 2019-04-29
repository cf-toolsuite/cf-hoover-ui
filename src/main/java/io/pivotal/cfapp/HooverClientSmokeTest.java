package io.pivotal.cfapp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import io.pivotal.cfapp.client.HooverClient;
import io.pivotal.cfapp.domain.SnapshotSummary;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class HooverClientSmokeTest implements ApplicationRunner {

    private final HooverClient client;
    private final ObjectMapper mapper;
    
    @Autowired
    public HooverClientSmokeTest(
        HooverClient client,
        ObjectMapper mapper) {
        this.client = client;
        this.mapper = mapper;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        client
            .getSummary()
                .map(summary -> mapWithException("SnapshotSummary", summary))
                .onErrorReturn("Could not obtain SnapshotSummary")
                .doOnNext(r -> log.debug(r))
            .then(client.getDetail())
                .map(detail -> mapWithException("SnapshotDetail", detail))
                .onErrorReturn("Could not obtain SnapshotDetail")
                .doOnNext(r -> log.debug(r))
            .then(client.getApplicationReport())
                .map(appReport -> mapWithException("AppUsageReport", appReport))
                .onErrorReturn("Could not obtain AppUsageReport")
                .doOnNext(r -> log.debug(r))
            .then(client.getServiceReport())
                .map(serviceReport -> mapWithException("ServiceUsageReport", serviceReport))
                .onErrorReturn("Could not obtain ServiceUsageReort")
                .doOnNext(r -> log.debug(r))
            .then(client.getTaskReport())
                .map(taskReport -> mapWithException("TaskUsageReport", taskReport))
                .onErrorReturn("Could not obtain TaskUsageReort")
                .doOnNext(r -> log.debug(r))
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