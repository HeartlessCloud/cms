package com.cms.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cms.entity.FacultyName;
import com.cms.mapper.FacultyNameMapper;
import org.springframework.stereotype.Service;

@Service
public class FacultyNameServiceImpl extends ServiceImpl<FacultyNameMapper, FacultyName> {
    public boolean existsByName(String facultyName) {
        return lambdaQuery().eq(FacultyName::getFacultyName, facultyName).count() > 0;
    }

}
