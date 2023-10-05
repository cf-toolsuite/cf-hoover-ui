package io.pivotal.cfapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.material.Material;

@EnableScheduling
@SpringBootApplication
@ConfigurationPropertiesScan
@Theme(themeClass = Material.class, variant = Material.DARK)
@PWA(name = "A simple dashboard display for metrics gathered from a cf-hoover instance", shortName = "cf-hoover-ui")
public class CfHooverUiApplication implements AppShellConfigurator {

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
        SpringApplication.run(CfHooverUiApplication.class, args);
    }

}
