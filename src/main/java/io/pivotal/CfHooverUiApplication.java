package io.pivotal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.reactive.EndpointRequest;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.reactive.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import reactivefeign.spring.config.EnableReactiveFeignClients;

@SpringBootApplication
@EnableReactiveFeignClients
public class CfHooverUiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CfHooverUiApplication.class, args);
    }

    @Bean
	public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
		return http
				.authorizeExchange()
					.matchers(
						PathRequest.toStaticResources().atCommonLocations(),
						EndpointRequest.toAnyEndpoint())
						.permitAll()
					.pathMatchers("/accounting/**","/snapshot/**")
						.permitAll()
					.and().build();
    }
    
}
