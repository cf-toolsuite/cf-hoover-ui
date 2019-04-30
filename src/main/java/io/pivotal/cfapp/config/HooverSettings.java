package io.pivotal.cfapp.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@RefreshScope
@Configuration
@ConfigurationProperties(prefix = "cf")
public class HooverSettings {

	private String baseUrl = "https://cf-hoover"; // use a virtual host name (e.g., service name, not a host name).
	private boolean sslValidationSkipped;

}
