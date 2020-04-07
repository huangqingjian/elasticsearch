package com.hqj.configuration;

import com.hqj.common.interceptor.SecurityInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * web配置
 */
@Configuration
public class WebConfiguration extends WebMvcConfigurationSupport {
    @Value("${image.dir:/data/image/}")
    private String imageDir;

    /**
     * 静态资源
     *
     * @param registry
     */
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/picture/**").addResourceLocations("file:" + imageDir);

        super.addResourceHandlers(registry);
    }

    @Bean
    public SecurityInterceptor securityInterceptor() {
        return new SecurityInterceptor();
    }

    /**
     * 拦截器配置
     *
     * @param registry 注册类
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(securityInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/static/**")
                .excludePathPatterns("/picture/**")
                .excludePathPatterns("/test/**")
                .excludePathPatterns("/remote/**")
                .excludePathPatterns("/page/login.html")
                .excludePathPatterns("/user/login/check");
        super.addInterceptors(registry);
    }
}
