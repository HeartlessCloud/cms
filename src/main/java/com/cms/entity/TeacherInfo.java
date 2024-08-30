package com.cms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class TeacherInfo {
    /**
     * 教师名称
     */
    private String teacherName;

    /**
     * 教师工号
     */
    private String teacherId;
    @TableId(value="id", type = IdType.AUTO)
    private Integer id;
}
