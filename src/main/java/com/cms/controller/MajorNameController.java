package com.cms.controller;

import com.cms.entity.MajorName;
import com.cms.service.MajorNameServiceImpl;
import com.cms.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/add-info/major-name")
public class MajorNameController {

    @Autowired
    MajorNameServiceImpl majorNameServiceImpl;

    @GetMapping
    public Result getMajorNamesList(){

        List<MajorName> majorNamesList = majorNameServiceImpl
                .lambdaQuery()
                .orderByAsc(MajorName::getId)
                .list();

        return Result.success(majorNamesList.stream().toList());
    }

    @PostMapping("/add")
    public Result addMajorName(@RequestBody MajorName majorName){
        log.info("majorName为：{}", majorName);
        if(majorNameServiceImpl.save(majorName))
            return Result.success();
        else return Result.error("插入失败");
    }

    @PostMapping("/update")
    public Result updateMajorName(@RequestBody MajorName majorName){
        if(majorNameServiceImpl.updateById(majorName)) return Result.success();
        else return Result.error("更新失败了");
    }

    @DeleteMapping("/delete/{id}")
    public Result deleteMajorName(@PathVariable("id") int id){
        log.info("id为：{}", id);
        if(majorNameServiceImpl.removeById(id)) {log.info("删除成功了"); return Result.success();}
        else return Result.error("删除失败");
    }
}
