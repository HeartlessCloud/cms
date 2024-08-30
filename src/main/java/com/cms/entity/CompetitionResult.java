package com.cms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompetitionResult {
    private String studentName;
    private String studentNumber;
    private String majorName;
    private String facultyName;
    private String groupName;
    private String title;
    private String grade;
    private String mentor;
    private String prize;
    private String competitionLevel;
    private String competitionName;
    private String inFaculty;
    private String year;
    @TableId(value="id", type = IdType.AUTO)
    private Integer id;
}
