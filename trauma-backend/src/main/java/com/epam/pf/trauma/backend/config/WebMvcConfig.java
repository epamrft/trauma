package com.epam.pf.trauma.backend.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "com.epam.pf.trauma.backend" }, includeFilters = { @Filter(Controller.class) }, excludeFilters = { @Filter(Configuration.class) })
public class WebMvcConfig extends WebMvcConfigurerAdapter {

}
