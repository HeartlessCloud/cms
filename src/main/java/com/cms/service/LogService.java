package com.cms.service;

import com.cms.entity.Teacher;
import com.cms.mapper.LogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpSession;

@Service
public class LogService {
    @Autowired
    private LogMapper logMapper;

    @Autowired
    private HttpSession session;

    /**
     * 登录服务
     * @param teacher
     * @return 登录是否成功
     */
    public boolean login(Teacher teacher){
        String teacherName = logMapper.queryNameWithTeacherId(teacher.getTeacherId());
        boolean hasPermission = teacherName.equals(teacher.getTeacherName());
        if(hasPermission) {
            session.setAttribute("isLogin", true);
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * 登出服务
     */
    public void logout(){
        session.removeAttribute("isLogin");
    }

}


