package com.cms.controller;

import com.cms.DTO.CompetitionResult;
import com.cms.mapper.AddInfoMapper;
import com.cms.service.AddInfoService;
import com.cms.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/add-info")
public class AddInfoController {
    @Autowired
    AddInfoService addInfoService;

    @PostMapping("/competition-result")
    public Result addCompetitionResult(@RequestBody CompetitionResult competitionResult){
        if(addInfoService.addCompetitionResult(competitionResult)) return Result.success();
        else return Result.error("添加竞赛记录失败！");
    }

    @PostMapping("/competition-name")
    public Result addCompetitionName(@RequestParam String competitionName){
        if(addInfoService.addcompetitionName(competitionName)) return Result.success();
        else return Result.error("添加竞赛名称失败！");
    }

    @PostMapping("/major")
    public Result addMajor(@RequestParam String major){
        if(addInfoService.addMajor(major)) return Result.success();
        else return Result.error("添加专业失败！");
    }

    @PostMapping("/faculty")
    public Result addFaculty(@RequestParam String faculty){
        if(addInfoService.addFaculty(faculty)) return Result.success();
        else return Result.error("添加学院名称失败！");
    }
}
