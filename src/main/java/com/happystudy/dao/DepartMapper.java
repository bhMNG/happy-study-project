package com.happystudy.dao;

import com.happystudy.model.Course;
import com.happystudy.model.Depart;

import java.util.List;
import java.util.Map;

/**
 * @author LJS
 * @data 2020/6/2 12:27
 */
public interface DepartMapper {
    //查询学院（5个参数）
    public List<Map<String, Object>> queryDepart(Map<String, Object> param);
    //查询学院个数（默认为总个数）
    public Integer queryDepartCount(Map<String, Object> param);
    //添加学院
    public void addDepart(String dNo,String dName);
    //修改学院名字
    public void updateDepartByNo(String dNo,Map<String, Object> param);
    //根据学院号删除学院
    public void deleteDepartByNo(String dNo);
    //根据学院号精准匹配学院
    public Depart findDepartByNo(String dNo);
    //查找某个学生的学院
    public Depart getStudentDepart(String sNo);
    //查找某个老师的学院
    public Depart getTeacherDepart(String tNo);
    //查找某个学院的课程
    public Course getDepartCourse(String dNo);
    //统计该学院的学生人数
    public Integer queryDepartAllStu(Map<String, Object> param);
    //统计该学院的课程总数
    public Integer queryDepartAllCourse(Map<String, Object> param);
    //统计该学院的老师人数
    public Integer queryDepartAllTeaCount(Map<String, Object> param);
}
