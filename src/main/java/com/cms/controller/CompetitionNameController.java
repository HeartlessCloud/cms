package com.cms.controller;

import com.cms.entity.CompetitionName;
import com.cms.service.CompetitionNameServiceImpl;
import com.cms.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/add-info/competition-name")
public class CompetitionNameController {

    @Autowired
    CompetitionNameServiceImpl competitionNameServiceImpl;

    @GetMapping
    public Result getCompetitionNamesList(){

        List<CompetitionName> competitionNamesList = competitionNameServiceImpl
                .lambdaQuery()
                .orderByAsc(CompetitionName::getId)
                .list();

        return Result.success(competitionNamesList.stream().toList());
    }

    @GetMapping("/count")
    public Result countCompetitionNumber(){
        long count = competitionNameServiceImpl.count();
        return Result.success(count);
    }

    @PostMapping("/add")
    public Result addCompetitionName(@RequestBody CompetitionName competitionName){
        if(competitionNameServiceImpl.save(competitionName))
            return Result.success();
        else return Result.error("插入失败");
    }

    @PostMapping("/update")
    public Result updateCompetitionName(@RequestBody CompetitionName competitionName){
        if(competitionNameServiceImpl.updateById(competitionName)) return Result.success();
        else return Result.error("更新失败了");
    }

    @DeleteMapping("/delete/{id}")
    public Result deleteCompetitionName(@PathVariable("id") int id){
        log.info("id为：{}", id);
        if(competitionNameServiceImpl.removeById(id)) {log.info("删除成功了"); return Result.success();}
        else return Result.error("删除失败");
    }
}
