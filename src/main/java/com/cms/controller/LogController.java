package com.cms.controller;

import com.cms.entity.TeacherInfo;
import com.cms.service.LogServiceImpl;
import com.cms.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 实现登录功能
 */
@RequestMapping
@RestController
@Slf4j
@CrossOrigin
public class LogController {
    @Autowired
    public LogServiceImpl logServiceImpl;

    @PostMapping("/login")
    public Result login(@RequestBody TeacherInfo teacherInfo){
        String loginToken = logServiceImpl.login(teacherInfo);
        if(loginToken != null) return Result.success(loginToken);
        else return Result.error("教师名称或工号错误！");
    }

    @GetMapping("/logout")
    public Result logout(){
        logServiceImpl.logout();
        return Result.success();
    }

}
