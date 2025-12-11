package org.cftoolsuite.cfapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;

import com.vaadin.flow.spring.security.VaadinSecurityConfigurer;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private static final PathPatternRequestMatcher.Builder MATCHER = PathPatternRequestMatcher.withDefaults();

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth ->
                auth.requestMatchers(
                    MATCHER.matcher("/public/**"))
                .permitAll()
            );

        http.with(VaadinSecurityConfigurer.vaadin(), configurer -> {});

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(
            MATCHER.matcher(HttpMethod.GET, "/"),
            MATCHER.matcher(HttpMethod.GET, "/accounting/**"),
            MATCHER.matcher("/actuator/**"),
            MATCHER.matcher(HttpMethod.POST, "/cache/refresh"),
            MATCHER.matcher(HttpMethod.GET, "/snapshot/**")
        );
    }

}
