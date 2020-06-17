package com.happystudy.service;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;

import java.security.PublicKey;
import java.util.Map;

/**
 * @author LJS
 * @data 2020/5/31 13:28
 */
public interface CourseService {
	//查询课程
    public JSONObject queryCourse(String keyword,String orderby,String asc,Integer pageNo,Integer pageSize);
    //查询课程数（默认为所有课程）
    public JSONObject queryCourseCount(Map<String,Object> param);
    //添加课程
    public JSONObject addCourse(String coNo,String coName);
    //修改课程名
    public JSONObject updateCourseByNo(String coNo,String coName);
    //根据课程号删除课程
    public JSONObject deleteCourseByNo(String coNo);
    //根据课程号精确匹配课程
    public JSONObject findCourseByNo(String coNo);
    //通过老师找课程
    public JSONObject getTeacherCourse(String tNo);
    //通过学院找课程
    public JSONObject findCourseByDepart(String dNo);
    //查询报了该课程的所有学生
    public JSONObject queryCourseAllStu(String cNo);
    //查询该课程的学生人数
    public JSONObject queryCourseStudentCount(String coNo);
    //根据课程状态码查询课程
    public JSONObject queryCourseByStatus(String keyword, String orderBy, String orderWay, Integer pageNo, Integer pageSize, Integer status);
    //置状态码
    public JSONObject setCourseStatus(String tNo, String coNo, Integer status);
    //学生选课
    public JSONObject chooseCourseBySNo(String sNo, String coNo);
    
    
    
}
