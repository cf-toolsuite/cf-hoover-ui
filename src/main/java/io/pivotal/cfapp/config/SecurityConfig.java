package io.pivotal.cfapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig  extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(HttpSecurity web) throws Exception {
        web.csrf().disable();
        // FIXME this is a bit too permissive
        web.authorizeRequests().antMatchers("/**").permitAll();
    }
}
