package com.rv.goAuth;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Default view and path mapping configuration.
 * 
 * @author aterati
 *
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer{

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		
		registry.addViewController("/").setViewName("login");
		registry.addViewController("/login").setViewName("login");
		registry.addViewController("/logoutSuccess").setViewName("logout");
		
		WebMvcConfigurer.super.addViewControllers(registry);
	}
	

}
