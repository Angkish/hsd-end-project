package com.angkish.config;


import com.angkish.interceptor.JwtTokenUserInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;


/**
 * 配置类，注册web层相关组件
 */
@Configuration
@Slf4j
public class WebMvcConfiguration extends WebMvcConfigurationSupport {

    @Autowired
    private JwtTokenUserInterceptor jwtTokenUserInterceptor;

    /**
     * 注册自定义拦截器
     *
     * @param registry
     */
    protected void addInterceptors(InterceptorRegistry registry) {
        log.info("开始注册自定义拦截器...");

        registry.addInterceptor(jwtTokenUserInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/user/login");
    }

//    /**
//     * 设置静态资源映射
//     * @param registry
//     */
//    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/doc.html").addResourceLocations("classpath:/META-INF/resources/");
//        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
//
//        // 图片静态资源映射 - 使用绝对路径
//        String imagePath = "file:D:/CangQiong/images/";
//        log.info("注册静态资源映射: /images/** -> {}", imagePath);
//
//        registry.addResourceHandler("/images/**")
//                .addResourceLocations(imagePath);
//
//        // 添加更多日志来确认映射生效
//        log.info("静态资源映射注册完成");
//    }
//
//
//    //扩展Spring MVC 框架的消息转换器
//    @Override
//    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
//        log.info("扩展消息转换器...");
//        //创建一个消息转换器对象
//        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
//        //需要为消息转换器设置一个对象转化器，对象转换器可以将java对象序列化为json数据
//        converter.setObjectMapper(new JacksonObjectMapper());
//        //将自己的消息转换器加入容器中
//        converters.add(0,converter);
//    }


}
