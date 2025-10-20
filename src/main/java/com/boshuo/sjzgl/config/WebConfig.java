package com.boshuo.sjzgl.config;

// 导入Spring配置和Web配置相关的类
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration  // 标记为Spring配置类，会被自动加载
public class WebConfig implements WebMvcConfigurer {  // 实现WebMvcConfigurer接口

    @Override  // 重写接口方法
    public void addCorsMappings(CorsRegistry registry) {
        // 配置CORS规则：
        registry.addMapping("/api/**")  // 匹配所有以/api开头的URL路径
                .allowedOrigins("*")    // 允许所有域名访问（生产环境应指定具体域名）
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // 允许的HTTP方法
                .allowedHeaders("*")    // 允许所有请求头
                .maxAge(3600);          // 预检请求缓存时间（秒）
    }
}
