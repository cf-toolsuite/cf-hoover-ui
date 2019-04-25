package io.pivotal.cfapp.client;

import feign.Headers;
import feign.RequestLine;
import io.pivotal.cfapp.domain.SnapshotDetail;
import io.pivotal.cfapp.domain.SnapshotSummary;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

@ReactiveFeignClient(name = "https://cf-hoover")
@Headers({ "Accept: application/json" })
public interface SnapshotClient {

	@RequestLine("GET /snapshot/detail")
	public Mono<SnapshotDetail> getDetail();

	@RequestLine("GET /snapshot/summary")
	public Mono<SnapshotSummary> getSummary();

}
