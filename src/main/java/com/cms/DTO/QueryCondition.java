package com.cms.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryCondition {
    private String logic;  // 逻辑操作符，如 AND, OR, NOT
    private String field;  // 查询字段
    private String value;  // 查询的内容
}
