package com.znh.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.File;

@SuppressWarnings("deprecation")
@Configuration
public class WebMvcConfigurer extends WebMvcConfigurerAdapter {
	
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	String path = new File("").getAbsoluteFile().toString();
    	String reppath = path.replace("\\", "/");
        registry.addResourceHandler("/static/**")
        .addResourceLocations("classpath:/static/")
        .addResourceLocations("file:"+reppath+"/static/upload/**");
    }
}
