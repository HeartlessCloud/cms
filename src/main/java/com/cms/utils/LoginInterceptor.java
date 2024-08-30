package com.cms.utils;

import cn.dev33.satoken.router.SaHttpMethod;
import cn.dev33.satoken.router.SaRouter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;

import cn.dev33.satoken.stp.StpUtil;

@Slf4j
public class LoginInterceptor implements HandlerInterceptor{

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
//        // 从请求头中获取 satoken
//        Enumeration<String> headerNames = request.getHeaderNames();
//        if (headerNames != null) {
//            while (headerNames.hasMoreElements()) {
//                String headerName = headerNames.nextElement();
//                String headerValue = request.getHeader(headerName);
//                log.info(headerName + ": " + headerValue);
//            }
//        }
//        String satoken = request.getHeaders("access-control-request-headers")[1];
        SaRouter.match(SaHttpMethod.OPTIONS)
                .free(r -> log.info("--------OPTIONS预检请求，不做处理"))
                .back();

        String satoken = request.getHeader("satoken");

        // 检查 satoken 是否为 null 或者空字符串
        Boolean checkLogin = (satoken != null && !satoken.trim().isEmpty());

        // 如果未登录（即 satoken 无效）
        if (!checkLogin) {
            log.info("登录失败！！！");
            // 重定向到登录页面
            response.sendRedirect("/log/login");
            return false;
        }

        // 如果登录成功，继续处理请求
        return true;
    }
}
