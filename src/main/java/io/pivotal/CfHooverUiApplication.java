package io.pivotal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import io.netty.channel.ChannelOption;
import reactivefeign.spring.config.EnableReactiveFeignClients;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;

@SpringBootApplication
@EnableReactiveFeignClients
public class CfHooverUiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CfHooverUiApplication.class, args);
    }

    @Configuration
    static class ApplicationSecurityOverride extends WebSecurityConfigurerAdapter {

        @Override
        public void configure(HttpSecurity web) throws Exception {
            web.csrf().disable();
            web.authorizeRequests().antMatchers("/**").permitAll();
        }

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
                    "/manifest.webmanifest", "/sw.js", "/offline-page.html",

                    // (development mode) static resources
                    "/frontend/**",

                    // (development mode) webjarsØ
                    "/webjars/**",

                    // (production mode) static resources
                    "/frontend-es5/**", "/frontend-es6/**");
        }
    }

}
