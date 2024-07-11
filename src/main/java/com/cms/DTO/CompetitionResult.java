package com.cms.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompetitionResult {
    private String studentName;
    private String studentNumber;
    private String major;
    private String faculty;
    private String group;
    private String title;
    private String grade;
    private String mentor;
    private String prize;
    private String competitionName;
    private boolean inFaculty;
    private int year;
}
