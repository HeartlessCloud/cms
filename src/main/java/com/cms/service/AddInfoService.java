package com.cms.service;

import com.cms.DTO.CompetitionResult;
import com.cms.mapper.AddInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddInfoService {
    @Autowired
    AddInfoMapper addInfoMapper;

    /**
     * 添加竞赛名称
     */
    public boolean addcompetitionName(String competitionName){
        int isSuccess = addInfoMapper.insertCompetitionName(competitionName);
        return isSuccess > 0;
    }

    /**
     * 添加专业
     */
    public boolean addMajor(String major){
        int isSuccess = addInfoMapper.insertMajor(major);
        return isSuccess > 0;
    }

    /**
     * 添加学院
     */
    public boolean addFaculty(String faculty){
        int isSuccess = addInfoMapper.insertFaculty(faculty);
        return isSuccess > 0;
    }

    /**
     * 添加竞赛成果
     */
    public boolean addCompetitionResult(CompetitionResult competitionResult){
        int isSuccess = addInfoMapper.insertCompetitionResult(competitionResult);
        return isSuccess > 0;
    }
}
