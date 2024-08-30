package com.cms.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cms.entity.MajorName;
import com.cms.mapper.MajorNameMapper;
import org.springframework.stereotype.Service;

@Service
public class MajorNameServiceImpl extends ServiceImpl<MajorNameMapper, MajorName> {
    public boolean existsByName(String majorName) {
        return lambdaQuery().eq(MajorName::getMajorName, majorName).count() > 0;
    }
}
