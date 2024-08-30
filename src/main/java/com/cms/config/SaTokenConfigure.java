package com.cms.config;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.filter.SaServletFilter;
import cn.dev33.satoken.router.SaHttpMethod;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.alibaba.fastjson.JSON;
import com.cms.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.config.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * [Sa-Token 权限认证] 配置类
 *
 * @author click33
 */
@Slf4j
@Configuration
public class SaTokenConfigure implements WebMvcConfigurer {

    /**
     * 注册 [Sa-Token 全局过滤器]
     */
    @Bean
    public SaServletFilter getSaServletFilter() {
        return new SaServletFilter()

                // 指定 [拦截路由] 与 [放行路由]
                .addInclude("/**")
                .addExclude("/login", "/registry", "/login.html", "/register.html")

                // 认证函数: 每次请求执行
                .setAuth(obj -> {
                    if (!StpUtil.isLogin()) {  //原先判断内容为satoken == null || satoken.trim().isEmpty()
                        log.warn("cookie中不存在satoken, 用户未登录！");
                        // 阻止请求继续处理
                        SaRouter.back(JSON.toJSONString(Result.error("redirect2login")));
                    } else {
                        log.info("用户已登录");
                    }
                })

                // 异常处理函数：每次认证函数发生异常时执行此函数
                .setError(e -> {
                    return SaResult.error(e.getMessage());
                })

                // 前置函数：在每次认证函数之前执行
                .setBeforeAuth(obj -> {
                    log.info("前置处理函数运行中");
//                    SaHolder.getResponse()
//
//                            // ---------- 设置跨域响应头 ----------
//                            // 允许指定域访问跨域资源
//                            .setHeader("Access-Control-Allow-Origin", "http://localhost:63342") //修改为当前域测试
//                            // 允许所有请求方式
//                            .setHeader("Access-Control-Allow-Methods", "*")
//                            // 允许的header参数
//                            .setHeader("Access-Control-Allow-Headers", "*")
//                            // 有效时间
//                            .setHeader("Access-Control-Max-Age", "3600")
//                            // 允许添加cookie
//                            .setHeader("Access-Control-Allow-Credentials", "true" )
//                    ;

                    // 如果是预检请求，则立即返回到前端
                    SaRouter.match(SaHttpMethod.OPTIONS)
                            .free(r -> log.info("--------OPTIONS预检请求，不做处理"))
                            .back();
                })
                ;
    }
}
