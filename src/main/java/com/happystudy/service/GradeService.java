package com.happystudy.service;

import java.util.Map;

import cn.hutool.json.JSONObject;

/**
 * @author LJS
 * @data 2020/5/31 13:45
 */
public interface GradeService {
	//查询某个课程的成绩单
    public JSONObject findGradeByCourse(String cNo);
    //查询某个学生的成绩
    public JSONObject findGradeByStudent(String sNo);
    //查询某个学生某门课程的成绩单 || 查询某门课程某个学生的成绩单
    public JSONObject findGradeByCS(String cNo,String sNo);
    //查询学生成绩
    public JSONObject queryGrade(Map<String,Object> param);
    //查询学生成绩（有成绩的+选了课还未有成绩的）
    public JSONObject queryGradeWithNull(Map<String,Object> param);
    //录入成绩
    public JSONObject enterScore(String sNo, String coNo, String score);
    
}
