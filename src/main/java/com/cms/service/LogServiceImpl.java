package com.cms.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cms.entity.TeacherInfo;
import com.cms.mapper.LogMapper;
import com.cms.mapper.TeacherInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpSession;
import cn.dev33.satoken.stp.StpUtil;

@Slf4j
@Service
public class LogServiceImpl extends ServiceImpl<TeacherInfoMapper, TeacherInfo> {
    @Autowired
    private LogMapper logMapper;

    @Autowired
    private HttpSession session;

    /**
     * 登录服务
     * @param teacherInfo
     * @return 登录是否成功
     */
    public String login(TeacherInfo teacherInfo){
        String teacherName = logMapper.queryNameWithTeacherId(teacherInfo.getTeacherId());
        boolean hasPermission;

        if(teacherName == null) hasPermission = false;
        else hasPermission = teacherName.equals(teacherInfo.getTeacherName());
//        log.info("当前hasPermission的状态是{}", hasPermission);

        if(hasPermission) {
            StpUtil.login(teacherInfo.getTeacherId());
            return StpUtil.getTokenValue();
        }
        else{
            return null;
        }
    }

    /**
     * 登出服务
     */
    public void logout(){
        session.removeAttribute("isLogin");
    }
}


