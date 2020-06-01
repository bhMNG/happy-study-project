package com.happystudy.service;

import cn.hutool.json.JSONObject;

import java.util.Map;

/**
 * @author LJS
 * @data 2020/5/31 13:50
 */
public interface DepartService {
    //查询学院(5个参数）
    public JSONObject queryDepart(Map<String,Object> param);
    //查询学院个数（默认为总个数）
    public JSONObject queryDepartCount(String keyword);
    //添加学院
    public JSONObject addDepart(String dNo,String dName);
    //修改学院名字
    public JSONObject updateDepartByNo(String dNo,String dName);
    //根据学院号删除学院
    public JSONObject deleteDepartByNo(String dNo);
    //根据学院号精准匹配学院
    public JSONObject findDepartByNo(String dNo);
    //查找某个学生的学院
    public JSONObject getStudentDepart(String sNo);
    //查找某个老师的学院
    public JSONObject getTeacherDepart(String tNo);
    //查找某个学院的课程
    public JSONObject getDepartCourse(String dNo);
    //统计该学院的学生人数
    public JSONObject queryDepartAllStu(String dNo);
    //统计该学院的课程总数
    public JSONObject queryDepartAllCourse(String dNo);
    //统计该学院的老师人数
    public JSONObject queryDepartAllTeaCount(String dNo);

}
