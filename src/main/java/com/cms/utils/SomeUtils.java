package com.cms.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cms.entity.CompetitionResult;
import org.apache.poi.ss.usermodel.Cell;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class SomeUtils {
    // 字段映射
    static public final Map<String, String> FIELD_MAPPING = new HashMap<String, String>() {{
        put("姓名", "studentName");
        put("名字", "studentName");
        put("studentName", "studentName");
        put("学号", "studentNumber");
        put("studentNumber", "studentNumber");
        put("专业", "majorName");
        put("majorName", "majorName");
        put("学院", "facultyName");
        put("facultyName", "facultyName");
        put("年级", "grade");
        put("grade", "grade");
        put("组别", "groupName");
        put("groupName", "groupName");
        put("作品名称", "title");
        put("title", "title");
        put("指导教师", "mentor");
        put("指导老师", "mentor");
        put("mentor", "mentor");
        put("获奖等级", "prize");
        put("prize", "prize");
        put("具体竞赛名称", "competitionName");
        put("competitionName", "competitionName");
        put("竞赛名称", "competitionName");
        put("competitionLevel", "competitionLevel");
        put("级别", "competitionLevel");
        put("year", "year");
        put("年份", "year");
        put("inFaculty", "inFaculty");
        put("业绩在学院", "inFaculty");
    }};


    public static void setFieldValue(CompetitionResult competitionResult, String fieldName, String value) {
        try {
            Field field = CompetitionResult.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(competitionResult, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static String getCellValueAsString(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                return removeDecimalPart(String.valueOf(cell.getNumericCellValue()));
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }

    public static String removeDecimalPart(String value) {
        int dotIndex = value.indexOf(".");
        return dotIndex == -1 ? value : value.substring(0, dotIndex);
    }

    // 方法将驼峰命名转换为下划线命名（如：studentName -> student_name）
    public static String camelToSnakeCase(String fieldName) {
        String regex = "([a-z])([A-Z]+)";
        String replacement = "$1_$2";
        return fieldName.replaceAll(regex, replacement).toLowerCase();
    }

    //替换？和*，并删除两边空格以及非转义加
    public static String clearString(String value) {
        if (value.length() > 1 && value.charAt(value.length() - 1) == '+' && value.charAt(value.length() - 2) == ' ') {
            value = value.substring(0, value.length() - 1).trim();
        }
        return value.trim();
    }


    //写入可以处理转义字符的模糊查询sql语句
    public static void canEscapeLike(QueryWrapper<CompetitionResult> queryWrapper, String column, String value){
        queryWrapper.apply(column + " LIKE {0} ESCAPE '\\\\'", value);
    }

    public static String[] splitByUnescapedPlus(String input) {
        // 如果字符串中不包含非转义的加号，直接返回包含一个元素的数组
        if (!input.matches(".*(?<!\\\\)\\+.*")) {
            return new String[]{input.trim()};
        }

        // 使用正则表达式来匹配非转义的加号
        String[] res = input.split("(?<!\\\\)\\+");

        // 将每个项两边多余的空格去掉，并且将转义＋替换为加号
        for (int i = 0; i < res.length; i++) {
            res[i] = res[i].replace("\\\\+", "+").trim();
        }

        return res;
    }

    public static String[] splitByTilde(String input){
        // 如果字符串中不包含波浪号，直接返回包含一个元素的数组
        if (!input.contains("~")) {
            return new String[]{input.trim()};
        }

        String[] res = input.split("~");

        // 将每个项两边多余的空格去掉，并且将转义＋替换为加号
        for (int i = 0; i < res.length; i++) {
            res[i] = res[i].trim();
        }

        return res;
    }

    // 处理条件项
    public static void handleConditionItemsWithLike(QueryWrapper<CompetitionResult> wrapper, String field, String[] conditionItems) {
        for (int j = 0; j < conditionItems.length; j++) {
            String conditionItem = conditionItems[j];

            // 检查是否包含非转义的 % 或 _
            boolean containsUnescapedWildcard = conditionItem.matches(".*(?<!\\\\)[%_].*");

            // 否则使用等值查询
            if (j != 0) {
                wrapper.or();
            }
            if (containsUnescapedWildcard) canEscapeLike(wrapper, field, conditionItem);
            else wrapper.eq(field, conditionItem);
        }
    }

    // 处理条件项
    public static void handleConditionItemsWithTilde(QueryWrapper<CompetitionResult> wrapper, String field, String[] conditionItems) {
        for (int j = 0; j < conditionItems.length; j++) {
            String conditionItem = conditionItems[j];

            // 检查是否包含tilde
            boolean containsTilde = conditionItem.contains("~");

            // 否则使用等值查询
            if (j != 0) {
                wrapper.or();
            }

            //检查是否含tilde，波浪线
            if (containsTilde){
                String[] lu = splitByTilde(conditionItem);
                wrapper.between(field, lu[0], lu[1]);
            }
            else wrapper.eq(field, conditionItem);
        }
    }

    public static boolean fieldIsYearOrGrade(String field){
        return field.equals("year") || field.equals("grade");
    }
}
