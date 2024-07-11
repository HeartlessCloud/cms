package com.cms.controller;

import com.cms.entity.Teacher;
import com.cms.service.LogService;
import com.cms.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 实现登录功能
 */
@RequestMapping("/log")
@RestController
public class LogController {
    @Autowired
    public LogService logService;

    @GetMapping("/login")
    public Result login(@RequestBody Teacher teacher){
        boolean isLogin = logService.login(teacher);
        if(isLogin) return Result.success();
        else return Result.error("账号或密码错误！");
    }

    @GetMapping("/logout")
    public Result logout(){
        logService.logout();
        return Result.success();
    }


}
