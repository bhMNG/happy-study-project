package com.happystudy.service.impl;

import cn.hutool.json.JSONObject;
import com.happystudy.constants.Constants;
import com.happystudy.dao.TeacherMapper;
import com.happystudy.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author LJS
 * @data 2020/6/1 8:39
 */
@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    private TeacherMapper teacherMapper;
    //查询教师（5个参数）
    @Override
    public JSONObject queryTeacher(Map<String, Object> param) {
        return null;
    }

    //查询教师人数（默认所有教师人数）
    @Override
    public JSONObject queryTeacherCount(String keyword) {
        JSONObject json=new JSONObject();
        Integer count = teacherMapper.queryTeacherCount(keyword);
        json.set("status", Constants.SUCCESS);
        json.set("count",count);
        return json;
    }

    //添加教师
    @Override
    public JSONObject addTeacher(String tNo, String tName) {

        return null;
    }

    //更新教师
    @Override
    public JSONObject updateTeacher(String tNo, Map<String, Object> param) {
        return null;
    }

    //删除教师
    @Override
    public JSONObject deleteClazzByNo(String tNo) {
        return null;
    }

    //根据班级获取老师
    @Override
    public JSONObject findTeacherByClazz(String cNo) {
        return null;
    }

    //根据课程找老师
    @Override
    public JSONObject findTeacherByCourse(String coNo) {
        return null;
    }

    //根据学院找老师
    @Override
    public JSONObject findTeacherByDepart(String dNo) {
        return null;
    }

    //获得该老师所负责的班级
    @Override
    public JSONObject getTeacherClazz(String tNo) {
        return null;
    }

    //获取该老师所负责的课程
    @Override
    public JSONObject getTeacherCourse(String tNo) {
        return null;
    }

    //获得该老师所属的学院
    @Override
    public JSONObject getTeacherDepart(String tNO) {
        return null;
    }
}
