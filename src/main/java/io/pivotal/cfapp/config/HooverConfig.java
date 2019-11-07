package io.pivotal.cfapp.config;

import javax.net.ssl.SSLException;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;

@RefreshScope
@Configuration
public class HooverConfig {

    // @see https://github.com/spring-cloud-incubator/spring-cloud-loadbalancer/blob/master/docs/src/main/asciidoc/spring-cloud-commons.adoc#spring-webflux-webclient-as-a-load-balancer-client
    // @see https://stackoverflow.com/questions/45418523/spring-5-webclient-using-ssl/53147631#53147631

    @Bean
    @ConditionalOnProperty(prefix="cf", name="sslValidationSkipped", havingValue="true")
    public WebClient insecureWebClient(HooverSettings settings, ReactorLoadBalancerExchangeFilterFunction lbFunction) throws SSLException {
        SslContext sslContext = SslContextBuilder
                .forClient()
                .trustManager(InsecureTrustManagerFactory.INSTANCE)
                .build();
        TcpClient tcpClient = TcpClient.create().secure(sslProviderBuilder -> sslProviderBuilder.sslContext(sslContext));
        HttpClient httpClient = HttpClient.from(tcpClient);
        return
            WebClient
                .builder()
                    .filter(lbFunction)
                    .clientConnector(new ReactorClientHttpConnector(httpClient))
                    .baseUrl(settings.getBaseUrl())
                    .build();
    }

    @Bean
    @ConditionalOnProperty(prefix="cf", name="sslValidationSkipped", havingValue="false", matchIfMissing=true)
    public WebClient secureWebClient(HooverSettings settings, ReactorLoadBalancerExchangeFilterFunction lbFunction) throws SSLException {
        return
            WebClient
                .builder()
                    .filter(lbFunction)
                    .clientConnector(new ReactorClientHttpConnector(HttpClient.newConnection().compress(true)))
                    .baseUrl(settings.getBaseUrl())
                    .build();
    }

}

