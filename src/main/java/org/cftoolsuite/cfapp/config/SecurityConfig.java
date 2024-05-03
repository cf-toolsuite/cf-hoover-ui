package org.cftoolsuite.cfapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.vaadin.flow.spring.security.VaadinWebSecurity;


@EnableWebSecurity
@Configuration
public class SecurityConfig extends VaadinWebSecurity {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
            .authorizeHttpRequests(auth ->
                auth.requestMatchers(
                    new AntPathRequestMatcher("/public/**"))
                .permitAll()
            );

        super.configure(http);
    }

    @Override
    protected void configure(WebSecurity web) throws Exception {
        web.ignoring().requestMatchers(
            new AntPathRequestMatcher("/", HttpMethod.GET.name()),
            new AntPathRequestMatcher("/accounting/**", HttpMethod.GET.name()),
            new AntPathRequestMatcher("/actuator/**"),
            new AntPathRequestMatcher("/cache/refresh", HttpMethod.POST.name()),
            new AntPathRequestMatcher("/snapshot/**", HttpMethod.GET.name())
        );
    }

}
