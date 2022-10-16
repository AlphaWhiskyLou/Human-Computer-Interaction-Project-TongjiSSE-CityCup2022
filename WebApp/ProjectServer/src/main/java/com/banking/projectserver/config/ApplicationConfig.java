package com.banking.projectserver.config;

/**
 * @ program: Bank
 * @ description:
 * @ author: Tianyu Lou
 * @ date: 2021-11-27 00:09:23
 */
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class ApplicationConfig extends WebMvcConfigurationSupport {

    /**
     * 全局配置 axios 跨域请求
     * addMapping：配置可以被跨域的路径，可以任意配置，可以具体到直接请求路径
     * allowedOrigins：允许所有的请求域名访问跨域资源，可以固定单条或者多条内容，
     * 如：”http://www.xxx.com“，只有该域名可以访问我们的跨域资源
     * allowCredentials：是否允许用户发送、处理 cookie
     * allowedMethods：允许所有的请求方法访问该跨域资源服务器，如：POST、GET、PUT、DELETE等
     * allowedHeaders：允许所有的请求header访问，可以自定义设置任意请求头信息
     * maxAge：预检请求的有效期，单位为秒。有效期内，不会重复发送预检请求
     * 当预检请求通过后，浏览器会发送真实请求到服务器。这就实现了跨域请求
     *
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        String[] origins = {"http://101.132.250.23:82", "http://101.132.250.23:81","http://localhost:8080", "http://localhost:8081","http://localhost:81","http://localhost:82","http://127.0.0.1:5000"};
        registry.addMapping("/**")
                .allowedOrigins(origins)
                .allowCredentials(true)
                .allowedMethods("*")
                .allowedHeaders("*")
                .maxAge(3600);
    }
}