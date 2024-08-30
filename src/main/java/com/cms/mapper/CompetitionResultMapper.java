package com.cms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cms.entity.CompetitionResult;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CompetitionResultMapper extends BaseMapper<CompetitionResult> {
    public Integer countThisYearCompetitionResults(String thisYear);
}
