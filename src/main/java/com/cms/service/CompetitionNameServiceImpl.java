package com.cms.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cms.entity.CompetitionName;
import com.cms.mapper.CompetitionNameMapper;
import org.springframework.stereotype.Service;

@Service
public class CompetitionNameServiceImpl extends ServiceImpl<CompetitionNameMapper, CompetitionName> {
    public boolean existsByName(String competitionName) {
        return lambdaQuery().eq(CompetitionName::getCompetitionName, competitionName).count() > 0;
    }

}
