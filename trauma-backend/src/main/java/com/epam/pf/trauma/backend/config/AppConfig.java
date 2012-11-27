package com.epam.pf.trauma.backend.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;

@Configuration
@ComponentScan(basePackages = { "com.epam.pf.trauma.backend" }, excludeFilters = { @Filter(Configuration.class), @Filter(Controller.class) })

public class AppConfig {

}
