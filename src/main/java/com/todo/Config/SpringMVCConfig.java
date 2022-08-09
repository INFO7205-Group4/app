package com.todo.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.todo.service.authservice.interceptor.SecurityInterceptor;

@Configuration
public class SpringMVCConfig implements WebMvcConfigurer {

	@Autowired
	SecurityInterceptor securityInterceptor;

	@Override
	public void addInterceptors(final InterceptorRegistry registry) {
		registry.addInterceptor(securityInterceptor).addPathPatterns("/**");
		;
	}

}
