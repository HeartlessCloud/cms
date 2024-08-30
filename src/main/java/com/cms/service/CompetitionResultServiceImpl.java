package com.cms.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cms.DTO.QueryCondition;
import com.cms.DTO.QueryRequest;
import com.cms.entity.CompetitionName;
import com.cms.entity.CompetitionResult;
import com.cms.entity.FacultyName;
import com.cms.entity.MajorName;
import com.cms.mapper.CompetitionResultMapper;
import com.cms.utils.SomeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.*;

import static com.cms.utils.SomeUtils.*;

@Slf4j
@Service
public class CompetitionResultServiceImpl extends ServiceImpl<CompetitionResultMapper, CompetitionResult> {
    @Autowired
    CompetitionResultMapper competitionResultMapper;

    @Autowired
    private MajorNameServiceImpl majorNameService;

    @Autowired
    private FacultyNameServiceImpl facultyNameService;

    @Autowired
    private CompetitionNameServiceImpl competitionNameService;

    public int countThisYearCompetitionResults(){
        return competitionResultMapper.countThisYearCompetitionResults(String.valueOf(LocalDate.now().getYear()));
    }

    public long countNationalLevelCompetitionResults() {
        // 使用 MyBatis-Plus 提供的 QueryWrapper 进行模糊查询
        QueryWrapper<CompetitionResult> queryWrapper = new QueryWrapper<>();
        canEscapeLike(queryWrapper, "competition_level", "国%");
        return this.count(queryWrapper);
    }

    public long countProvincialLevelCompetitionResults() {
        // 使用 MyBatis-Plus 提供的 QueryWrapper 进行模糊查询
        QueryWrapper<CompetitionResult> queryWrapper = new QueryWrapper<>();
        canEscapeLike(queryWrapper, "competition_level", "省%");
        return this.count(queryWrapper);
    }

    public boolean uploadExcelFile(MultipartFile excelFile) {
        try (InputStream inputStream = excelFile.getInputStream();
             Workbook workbook = new XSSFWorkbook(inputStream)) {

            // 假设数据在第一个 sheet
            Sheet sheet = workbook.getSheetAt(0);

            // 获取表头
            Row headerRow = sheet.getRow(0);
            Map<Integer, String> columnMapping = new HashMap<>();
            for (Cell cell : headerRow) {
                int columnIndex = cell.getColumnIndex();
                // 跳过第一列（序号列）
                if (columnIndex == 0) continue;

                String headerName = cell.getStringCellValue().trim();
                String fieldName = FIELD_MAPPING.get(headerName);

                if (fieldName != null) {
                    columnMapping.put(cell.getColumnIndex(), fieldName);
                }
            }

            // 逐行读取数据
            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row == null) {
                    continue;
                }

                CompetitionResult competitionResult = new CompetitionResult();

                for (Map.Entry<Integer, String> entry : columnMapping.entrySet()) {
                    int columnIndex = entry.getKey();
                    String fieldName = entry.getValue();
                    Cell cell = row.getCell(columnIndex);

                    // 根据字段名反射设置值
                    if (cell != null) {
                        String cellValue = getCellValueAsString(cell).trim();
                        setFieldValue(competitionResult, fieldName, cellValue);
                    }
                }

                // 保存数据到数据库
                this.save(competitionResult);
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<CompetitionResult> queryCompetitionResults(QueryRequest queryRequest) {
        QueryWrapper<CompetitionResult> queryWrapper = new QueryWrapper<>();
        List<QueryCondition> conditionTotals = queryRequest.getConditions();

        for (int i = 0; i <= conditionTotals.size() - 1; i++) {
            QueryCondition conditionTotal = conditionTotals.get(i);
            String logic = conditionTotal.getLogic();
            String field = camelToSnakeCase(conditionTotal.getField());
            String condition = conditionTotal.getValue();
            String[] conditionItems;

            // 处理模糊查询（LIKE）以及删除两边空格以及结尾非转义加
            condition = clearString(condition);
            log.info("condition为：{}", condition);

            //拆分成多个项
            conditionItems = splitByUnescapedPlus(condition);

            if(i == 0){
                if(fieldIsYearOrGrade(field)){
                    queryWrapper.not(qw -> {
                        qw.not(qw1 -> {
                            handleConditionItemsWithTilde(qw1, field, conditionItems);
                        });
                    });
                }

                else{
                    queryWrapper.not(qw -> {
                    qw.not(qw1 -> {
                        handleConditionItemsWithLike(qw1 ,field, conditionItems);
                    });
                });
                }
            }

            else{
                if(logic.equals("AND")){
                    queryWrapper.and(qw -> {
                        if(fieldIsYearOrGrade(field)){
                            handleConditionItemsWithTilde(qw, field, conditionItems);
                        }
                        else{
                            handleConditionItemsWithLike(qw ,field, conditionItems);
                        }
                    });
                }

                else if(logic.equals("OR")){
                    queryWrapper.or(qw -> {
                        if(fieldIsYearOrGrade(field)){
                            handleConditionItemsWithTilde(qw, field, conditionItems);
                        }
                        else{
                            handleConditionItemsWithLike(qw ,field, conditionItems);
                        }
                    });
                }

                else if(logic.equals("NOT")){
                    queryWrapper.not(qw -> {
                    if(fieldIsYearOrGrade(field)){
                        handleConditionItemsWithTilde(qw, field, conditionItems);
                    }
                    else{
                        handleConditionItemsWithLike(qw ,field, conditionItems);
                    }}
                    );
                }
            }
        }

        log.info("查询sql为：{}", queryWrapper.getTargetSql());
        // 执行查询
        return this.list(queryWrapper);
    }

    public boolean updateListByExcel(MultipartFile excelFile) {
        try (Workbook workbook = new XSSFWorkbook(excelFile.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);

            // 查找表头，确定"专业"、"学院"和"具体竞赛名称"列的索引
            Row headerRow = sheet.getRow(0);
            int majorColumnIndex = -1;
            int facultyColumnIndex = -1;
            int competitionColumnIndex = -1;

            for (Cell cell : headerRow) {
                String cellValue = cell.getStringCellValue().trim();
                if ("专业".equals(cellValue)) {
                    majorColumnIndex = cell.getColumnIndex();
                } else if ("学院".equals(cellValue)) {
                    facultyColumnIndex = cell.getColumnIndex();
                } else if ("具体竞赛名称".equals(cellValue)) {
                    competitionColumnIndex = cell.getColumnIndex();
                }
            }

            // 检查是否找到所有需要的列
            if (majorColumnIndex == -1 || facultyColumnIndex == -1 || competitionColumnIndex == -1) {
                log.error("Excel表格缺少必要的列");
                return false;
            }

            Set<String> majorNames = new HashSet<>();
            Set<String> facultyNames = new HashSet<>();
            Set<String> competitionNames = new HashSet<>();

            // 假设 "专业" "学院" "具体竞赛名称"
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // 跳过表头

                String majorName = row.getCell(majorColumnIndex).getStringCellValue().trim();
                String facultyName = row.getCell(facultyColumnIndex).getStringCellValue().trim();
                String competitionName = row.getCell(competitionColumnIndex).getStringCellValue().trim();

                majorNames.add(majorName);
                facultyNames.add(facultyName);
                competitionNames.add(competitionName);
            }

            // 插入不重复的专业名称
            majorNames.forEach(majorName -> {
                if (!majorNameService.existsByName(majorName)) {
                    MajorName majorName1 = new MajorName();
                    majorName1.setMajorName(majorName);
                    majorNameService.save(majorName1);
                }
            });

            // 插入不重复的学院名称
            facultyNames.forEach(facultyName -> {
                if (!facultyNameService.existsByName(facultyName)) {
                    FacultyName facultyName1 = new FacultyName();
                    facultyName1.setFacultyName(facultyName);
                    facultyNameService.save(facultyName1);
                }
            });

            // 插入不重复的竞赛名称
            competitionNames.forEach(competitionName -> {
                if (!competitionNameService.existsByName(competitionName)) {
                    CompetitionName competitionName1 = new CompetitionName();
                    competitionName1.setCompetitionName(competitionName);
                    competitionNameService.save(competitionName1);
                }
            });

            return true;
        } catch (IOException e) {
            log.error("Error processing Excel file", e);
            return false;
        }
    }

}
