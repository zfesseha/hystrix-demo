package com.nsf.hystrix.config;

import com.nsf.hystrix.filter.HystrixRequestContextServletFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

@Configuration
public class HystrixConfiguration {

    @Bean
    public FilterRegistrationBean someFilterRegistration() {

        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(hystrixContextFilter());
        registration.addUrlPatterns("/api/*");
//        registration.addInitParameter("paramName", "paramValue");
        registration.setName("hystrixContextFilter");
        registration.setOrder(1);
        return registration;
    }

    public Filter hystrixContextFilter() {
        return new HystrixRequestContextServletFilter();
    }
}
