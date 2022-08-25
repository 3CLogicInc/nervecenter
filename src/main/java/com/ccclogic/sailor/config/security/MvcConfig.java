package com.ccclogic.sailor.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

	@Override
	public void configurePathMatch(PathMatchConfigurer configurer) {
		CustomUrlPathHelper urlPathhelper = new CustomUrlPathHelper();
		configurer.setUrlPathHelper(urlPathhelper);
	}

}
