////package com.cms.config;
////
////import cn.dev33.satoken.context.SaHolder;
////import cn.dev33.satoken.filter.SaServletFilter;
////import cn.dev33.satoken.router.SaHttpMethod;
////import cn.dev33.satoken.router.SaRouter;
////import cn.dev33.satoken.stp.StpUtil;
////import cn.dev33.satoken.util.SaResult;
////import com.cms.utils.LoginInterceptor;
////import lombok.extern.slf4j.Slf4j;
////import org.springframework.context.annotation.Bean;
////import org.springframework.context.annotation.Configuration;
////import org.springframework.web.servlet.config.annotation.*;
////
////@Configuration
////@EnableWebMvc
////public class WebConfig implements WebMvcConfigurer {
////
////    @Override
////    public void addCorsMappings(CorsRegistry registry) {
////        registry.addMapping("/**")
////                // 对所有路径生效
////                .setHeader("Access-Control-Allow-Origin", "*")
////                // 允许所有请求方式
////                .setHeader("Access-Control-Allow-Methods", "*")
////                // 允许的header参数
////                .setHeader("Access-Control-Allow-Headers", "*")
////                .allowCredentials(true); // 允许携带凭证
////    }
////}
//package com.cms.config;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.*;
//import org.springframework.core.annotation.Order;
//
//@Slf4j
//@Configuration
//@Order(1)
//public class WebConfig implements WebMvcConfigurer {
//
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        log.info("cors加载了");
//        registry.addMapping("/**")  // 匹配所有路径
////                .allowedOrigins("*")  // 允许所有域
//                .allowedOrigins("http://localhost:63342", "http://localhost:63343")  //设置为当前域，进行测试
//                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // 允许的请求方法
//                .allowedHeaders("*")  // 允许的请求头
//                .allowCredentials(true)
//                .maxAge(3600);  // 预检请求的缓存时间
//    }
//}
