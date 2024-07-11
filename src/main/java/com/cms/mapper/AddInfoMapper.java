package com.cms.mapper;

import com.cms.DTO.CompetitionResult;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AddInfoMapper {
    /**
     *新增竞赛条目
     */
    public int insertCompetitionName(String competitionName);

    /**
     * 新增专业条目
     */
    public int insertMajor(String major);

    /**
     * 新增学院条目
     */
    public int insertFaculty(String faculty);

    /**
     * 添加竞赛成果记录
     */
    public int insertCompetitionResult(CompetitionResult competitionResult);
}
