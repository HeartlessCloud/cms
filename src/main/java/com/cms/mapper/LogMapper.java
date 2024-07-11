package com.cms.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface LogMapper {
    public String queryNameWithTeacherId(String teacherId);
}
