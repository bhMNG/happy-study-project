package com.happystudy.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import cn.hutool.json.JSONObject;

/**
 * @author LJS
 * @data 2020/5/31 12:59
 */

public interface TeacherService {
	//查询教师（5个参数）
    public JSONObject queryTeacher(String keyword,String orderby,String asc,Integer pageNo,Integer pageSize);
    //查询教师人数（默认所有教师人数）
    public JSONObject queryTeacherCount(Map<String,Object> param);
    //添加教师
    public JSONObject addTeacher(String tNo,String tName, String tSex, String tDepartFk);
    //更新教师
    public JSONObject updateTeacher(String tNo,String tName,String tSex,String tDepartFk);
    //删除教师
    public JSONObject deleteTeacherByNo(String[] tNos);
    //根据班级获取老师
    public JSONObject findTeacherByClazz(String cNo);
    //根据课程找老师
    public JSONObject findTeacherByCourse(String coNo);
    //根据学院找老师
    public JSONObject findTeacherByDepart(String dNo);
    //获得该老师所负责的班级
    public JSONObject getTeacherClazz(String tNo);
    //获取该老师所负责的课程
    public JSONObject getTeacherCourse(String tNo);
    //获得该老师所属的学院
    public JSONObject getTeacherDepart(String tNO);
}
