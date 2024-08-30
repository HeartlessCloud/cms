package com.cms.controller;

import com.cms.DTO.QueryRequest;
import com.cms.entity.CompetitionName;
import com.cms.entity.CompetitionResult;
import com.cms.service.CompetitionResultServiceImpl;
import com.cms.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Comparator;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/add-info/competition-result")
@Controller
public class CompetitionResultController{
    @Autowired
    CompetitionResultServiceImpl competitionResultServiceImpl;

    @PostMapping("/add")
    public Result addCompetitionResult(@RequestBody CompetitionResult competitionResult){
        if(competitionResultServiceImpl.save(competitionResult)) return Result.success();
        else return Result.error("添加竞赛信息出错!");
    }

    @GetMapping("/count-all")
    public Result countAllCompetitionResultNumber(){
        long count = competitionResultServiceImpl.count();
        return Result.success(count);
    }

    @GetMapping("/count-thisYear")
    public Result countThisYearCompetitionResultNumber(){
        long count = competitionResultServiceImpl.countThisYearCompetitionResults();
        return Result.success(count);
    }

    @GetMapping("/count-nationalLevel")
    public Result countNationalCompetitionResultNumber(){
        long count = competitionResultServiceImpl.countNationalLevelCompetitionResults();
        return Result.success(count);
    }

    @GetMapping("/count-provincialLevel")
    public Result countProvincialCompetitionResultNumber(){
        long count = competitionResultServiceImpl.countProvincialLevelCompetitionResults();
        return Result.success(count);
    }

    @PostMapping("/upload")
    public Result uploadExcelFile(@RequestParam("excelFile") MultipartFile excelFile){
        if(competitionResultServiceImpl.uploadExcelFile(excelFile)) return Result.success();
        else return Result.error("上传失败");
    }

    @PostMapping("/update-list")
    public Result updateListByExcel (@RequestParam("excelFile") MultipartFile excelFile){
        if(competitionResultServiceImpl.updateListByExcel(excelFile)) return Result.success();
        else return Result.error("上传失败");
    }

    @PostMapping("/query")
    public Result queryCompetitionResults(@RequestBody QueryRequest queryRequest){
        log.info("到达了controller层");
        log.info("内容为:{}", queryRequest.getConditions());
        List<CompetitionResult> competitionResultsList = competitionResultServiceImpl
                .queryCompetitionResults(queryRequest);

        competitionResultsList.sort(Comparator.comparing(CompetitionResult::getId));

        return Result.success(competitionResultsList.stream().toList());
    }
}
