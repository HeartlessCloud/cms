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

    @PostMapping("/add-competition-result")
    public Result addCompetitionResult(@RequestBody CompetitionResult competitionResult){
        if(addInfoService.addCompetitionResult(competitionResult)) return Result.success();
        else return Result.error("添加竞赛记录失败！");
    }

    @PostMapping("/add-competition-name")
    public Result addCompetitionName(@RequestParam String competitionName){
        if(addInfoService.addCompetitionName(competitionName)) return Result.success();
        else return Result.error("添加竞赛名称失败！");
    }

    @DeleteMapping("/delete-competition-name")
    public Result deleteCompetitionName(@RequestParam int sequence){
        if(addInfoService.deleteCompetitionName(sequence)) return Result.success();
        else return Result.error("删除竞赛条目失败！");
    }

    @PostMapping("/add-major")
    public Result addMajor(@RequestParam String major){
        if(addInfoService.addMajor(major)) return Result.success();
        else return Result.error("添加专业失败！");
    }

    @DeleteMapping("/delete-major")
    public Result deleteMajor(@RequestParam int sequence){
        if(addInfoService.deleteMajor(sequence)) return Result.success();
        else return Result.error("删除专业名称条目失败！");
    }

    @PostMapping("/add-faculty")
    public Result addFaculty(@RequestParam String faculty){
        if(addInfoService.addFaculty(faculty)) return Result.success();
        else return Result.error("添加学院名称失败！");
    }

    @DeleteMapping("/delete-faculty")
    public Result deleteFaculty(@RequestParam int sequence){
        if(addInfoService.deleteFaculty(sequence)) return Result.success();
        else return Result.error("删除学院条目失败");
    }
}

