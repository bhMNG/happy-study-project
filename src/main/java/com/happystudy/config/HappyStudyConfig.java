package com.happystudy.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.happystudy.interceptor.HappyStudyInterceptor;
import com.happystudy.interceptor.PropGuardianInterceptor;
import com.happystudy.interceptor.UserInterceptor;



@Configuration
public class HappyStudyConfig implements WebMvcConfigurer{
	@Autowired
	private HappyStudyInterceptor happyStudyInterceptor;
	@Autowired
	private UserInterceptor userInterceptor;
	@Autowired
	private PropGuardianInterceptor propInterceptor;
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(happyStudyInterceptor).addPathPatterns("/**");
		registry.addInterceptor(userInterceptor).addPathPatterns("/happy-study/**");
		registry.addInterceptor(propInterceptor).addPathPatterns("/happy-study/**");
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/fileupload/**").addResourceLocations("file:G:/Java_learning/fileupload/happy-study/");
	}
}
