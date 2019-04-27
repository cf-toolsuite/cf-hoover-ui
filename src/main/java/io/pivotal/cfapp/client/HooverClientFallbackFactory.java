package io.pivotal.cfapp.client;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.springframework.stereotype.Component;

import io.pivotal.cfapp.domain.SnapshotDetail;
import io.pivotal.cfapp.domain.SnapshotSummary;
import io.pivotal.cfapp.domain.accounting.application.AppUsageReport;
import io.pivotal.cfapp.domain.accounting.service.ServiceUsageReport;
import io.pivotal.cfapp.domain.accounting.task.TaskUsageReport;
import lombok.extern.slf4j.Slf4j;
import reactivefeign.FallbackFactory;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class HooverClientFallbackFactory implements FallbackFactory<HooverClient> {

    @Override
    public HooverClient apply(Throwable throwable) {
        return new HooverClient() {

			@Override
			public Mono<SnapshotDetail> getDetail() {
                log.warn("Could not obtain snapshot detail. {}", getStackTrace(throwable));
				return Mono.just(SnapshotDetail.builder().build());
			}

			@Override
			public Mono<SnapshotSummary> getSummary() {
                log.warn("Could not obtain snapshot summary. {}", getStackTrace(throwable));
				return Mono.just(SnapshotSummary.builder().build());
			}

			@Override
			public Mono<TaskUsageReport> getTaskReport() {
                log.warn("Could not obtain system-wide task usage. {}", getStackTrace(throwable));
				return Mono.just(new TaskUsageReport());
			}

			@Override
			public Mono<AppUsageReport> getApplicationReport() {
                log.warn("Could not obtain system-wide application usage. {}", getStackTrace(throwable));
				return Mono.just(new AppUsageReport());
			}

			@Override
			public Mono<ServiceUsageReport> getServiceReport() {
                log.warn("Could not obtain system-wide service usage. {}", getStackTrace(throwable));
				return Mono.just(new ServiceUsageReport());
			}

        };
	}
	
	private static String getStackTrace(final Throwable throwable) {
		final StringWriter sw = new StringWriter();
		final PrintWriter pw = new PrintWriter(sw, true);
		throwable.printStackTrace(pw);
		return sw.getBuffer().toString();
   }

}