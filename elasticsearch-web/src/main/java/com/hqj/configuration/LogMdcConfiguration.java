package com.hqj.configuration;

import com.hqj.filter.MDCFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * log mdc配置
 */
@Configuration
public class LogMdcConfiguration {

    @Bean
    public FilterRegistrationBean mdcLogFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setOrder(Integer.MIN_VALUE);
        registration.setFilter(new MDCFilter());
        registration.addUrlPatterns("/*");
        registration.setName("mdc-filter");
        return registration;
    }

}
