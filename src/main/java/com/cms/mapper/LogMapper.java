package com.cms.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface LogMapper {
//    @Select("SELECT teacher_name FROM teacher_info WHERE teahcer_id = #{teacherId}")
    public String queryNameWithTeacherId(String teacherId);
}
