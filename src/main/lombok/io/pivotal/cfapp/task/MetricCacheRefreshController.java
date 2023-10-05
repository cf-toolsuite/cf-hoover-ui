package io.pivotal.cfapp.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
@ConditionalOnProperty(prefix="cron", name="enabled", havingValue="true")
public class MetricCacheRefreshController {

    private final MetricCacheRefreshTask task;

    @Autowired
    public MetricCacheRefreshController(MetricCacheRefreshTask task) {
        this.task = task;
    }

    @PostMapping("/cache/refresh")
    public Mono<ResponseEntity<Void>> refreshCache() {
        task.refreshCache();
        return Mono.just(ResponseEntity.noContent().build());
    }
}