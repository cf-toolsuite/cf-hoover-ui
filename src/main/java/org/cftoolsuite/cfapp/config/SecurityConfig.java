package org.cftoolsuite.cfapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import com.vaadin.flow.spring.security.RequestUtil;

@Configuration
public class SecurityConfig {

    @Autowired
    private RequestUtil requestUtil;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Configure CSRF protection selectively
        http.csrf(csrf -> csrf
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .ignoringRequestMatchers(requestUtil::isFrameworkInternalRequest)
        );

        // Configure authorization
        http.authorizeHttpRequests(authz -> authz
                .requestMatchers("/", "/accounting/**", "/actuator/**", "/h2-console", "/cache/refresh", "/snapshot/**").permitAll()
                .anyRequest().authenticated() // More secure default; requires authentication for other requests
        );

        return http.build();
    }
}
