package com.heeexy.example.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
public class CorsConfig {
    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*"); // 1 设置访问源地址
        corsConfiguration.addAllowedHeader("*"); // 2 设置访问源请求头
        corsConfiguration.addAllowedMethod("*"); // 3 设置访问源请求方法
        corsConfiguration.setAllowCredentials(true);// 4 允许跨域携带cookie
        corsConfiguration.setMaxAge((long) 1209600);
        return corsConfiguration;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig()); // 5 对接口配置跨域设置
        return new CorsFilter(source);
    }
}
/*@Configuration
public class CorsConfig extends WebMvcConfigurationSupport {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("POST", "GET", "OPTIONS", "DELETE", "PUT")
                .allowedOrigins("*")
                .allowCredentials(true)
                .allowedHeaders("*");
        super.addCorsMappings(registry);
    }

*//*    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        *//**//** 这块调用两次addResourceLocations方法，第二次调用是本文精髓所在，我灵机一动发现的?**//**//*

        registry.addResourceHandler("/upload/**").addResourceLocations("/upload/");
    }*//*


}*/
