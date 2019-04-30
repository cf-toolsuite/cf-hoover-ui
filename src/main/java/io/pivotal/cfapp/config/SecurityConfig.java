package io.pivotal.cfapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig {

    @Configuration
    static class WebSecurity extends WebSecurityConfigurerAdapter {

    	@Override
    	public void configure(HttpSecurity web) throws Exception {
            web.csrf().disable();
            // FIXME this is a bit too permissive
			web.authorizeRequests().antMatchers("/**").permitAll();
		}

        /*
		@Override
    	public void configure(WebSecurity web) throws Exception {
			web.ignoring().antMatchers(
            // Vaadin Flow static resources
            "/VAADIN/**",

            // the standard favicon URI
            "/favicon.ico",

            // the robots exclusion standard
            "/robots.txt",

            // web application manifest
            "/manifest.webmanifest",
            "/sw.js",
            "/offline-page.html",

            // (development mode) static resources
            "/frontend/**",

            // (development mode) webjarsØ
            "/webjars/**",

            // (production mode) static resources
            "/frontend-es5/**", "/frontend-es6/**");
        }
        */
	}

    /*
    @Configuration
    @EnableWebFluxSecurity
    static class WebfluxSecurityConfig {
        @Bean
        public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
            return http
                    .authorizeExchange()
                        .matchers(
                            PathRequest.toStaticResources().atCommonLocations(),
                            EndpointRequest.toAnyEndpoint()
                        )
                        .permitAll()
                        .pathMatchers(
                            "/accounting/**",
                            "/snapshot/**",
                            "/space-users/**",
                            "/users/**",
                            "/VAADIN/**",
                            "/favicon.ico",
                            "/robots.txt",
                            "/manifest.webmanifest",
                            "/sw.js",
                            "/offline-page.html",
                            "/frontend/**",
                            "/frontend-es5/**",
                            "/frontend-es6/**"
                        )
                        .permitAll()
                        .and()
                        .build();
        }
    }
    */
}