package org.cftoolsuite.cfapp.config;

import javax.net.ssl.SSLException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import reactor.netty.http.client.HttpClient;

@Configuration
public class WebClientConfig {

    // @see https://github.com/spring-cloud-incubator/spring-cloud-loadbalancer/blob/master/docs/src/main/asciidoc/spring-cloud-commons.adoc#spring-webflux-webclient-as-a-load-balancer-client
    // @see https://stackoverflow.com/questions/45418523/spring-5-webclient-using-ssl/53147631#53147631

    @Bean
    @ConditionalOnProperty(prefix="cf", name="sslValidationSkipped", havingValue="true")
    public WebClient insecureWebClient(WebClient.Builder builder, HooverSettings settings, ReactorLoadBalancerExchangeFilterFunction lbFunction,
        @Value("${spring.codec.max-in-memory-size}") Integer maxInMemorySize) throws SSLException {
    	SslContext context = SslContextBuilder.forClient()
    		    .trustManager(InsecureTrustManagerFactory.INSTANCE)
    		    .build();
    	HttpClient httpClient = HttpClient.create().secure(t -> t.sslContext(context));
        return
            builder
                .filter(lbFunction)
                .exchangeStrategies(
                    ExchangeStrategies
                        .builder()
                        .codecs(
                            configurer ->
                                configurer
                                    .defaultCodecs()
                                    .maxInMemorySize(maxInMemorySize)
                        )
                        .build()
                )
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .baseUrl(settings.getBaseUrl())
                .build();
    }

    @Bean
    @ConditionalOnProperty(prefix="cf", name="sslValidationSkipped", havingValue="false", matchIfMissing=true)
    public WebClient secureWebClient(WebClient.Builder builder, HooverSettings settings, ReactorLoadBalancerExchangeFilterFunction lbFunction,
        @Value("${spring.codec.max-in-memory-size}") Integer maxInMemorySize) throws SSLException {
        return
            builder
                .filter(lbFunction)
                .exchangeStrategies(
                    ExchangeStrategies
                        .builder()
                        .codecs(
                            configurer ->
                                configurer
                                    .defaultCodecs()
                                    .maxInMemorySize(maxInMemorySize)
                        )
                        .build()
                )
                .clientConnector(new ReactorClientHttpConnector(HttpClient.newConnection().compress(true)))
                .baseUrl(settings.getBaseUrl())
                .build();
    }

}

