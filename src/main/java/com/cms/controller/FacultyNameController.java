package com.cms.controller;

import com.cms.entity.FacultyName;
import com.cms.service.FacultyNameServiceImpl;
import com.cms.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/add-info/faculty-name")
public class FacultyNameController {

    @Autowired
    FacultyNameServiceImpl facultyNameServiceImpl;

    @GetMapping
    public Result getFacultyNamesList(){

        List<FacultyName> facultyNamesList = facultyNameServiceImpl
                .lambdaQuery()
                .orderByAsc(FacultyName::getId)
                .list();

        return Result.success(facultyNamesList.stream().toList());
    }

    @PostMapping("/add")
    public Result addFacultyName(@RequestBody FacultyName facultyName){
        log.info("facultyName为：{}", facultyName);
        if(facultyNameServiceImpl.save(facultyName))
            return Result.success();
        else return Result.error("插入失败");
    }

    @PostMapping("/update")
    public Result updateFacultyName(@RequestBody FacultyName facultyName){
        if(facultyNameServiceImpl.updateById(facultyName)) return Result.success();
        else return Result.error("更新失败了");
    }

    @DeleteMapping("/delete/{id}")
    public Result deleteFacultyName(@PathVariable("id") int id){
        log.info("id为：{}", id);
        if(facultyNameServiceImpl.removeById(id)) {log.info("删除成功了"); return Result.success();}
        else return Result.error("删除失败");
    }
}
