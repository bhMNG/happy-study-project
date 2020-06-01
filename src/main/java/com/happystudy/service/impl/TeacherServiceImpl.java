package com.happystudy.service.impl;

import cn.hutool.json.JSONObject;
import com.happystudy.service.TeacherService;

import java.util.Map;

/**
 * @author LJS
 * @data 2020/6/1 8:39
 */
public class TeacherServiceImpl implements TeacherService {
    @Override
    public JSONObject queryTeacher(Map<String, Object> param) {
        return null;
    }

    @Override
    public JSONObject queryTeacherCount(String keyword) {
        return null;
    }

    @Override
    public JSONObject addTeacher(String tNo, String tName) {
        return null;
    }

    @Override
    public JSONObject updateTeacher(String tNo, Map<String, Object> param) {
        return null;
    }

    @Override
    public JSONObject deleteClazzByNo(String tNo) {
        return null;
    }

    @Override
    public JSONObject findTeacherByClazz(String cNo) {
        return null;
    }

    @Override
    public JSONObject findTeacherByCourse(String coNo) {
        return null;
    }

    @Override
    public JSONObject findTeacherByDepart(String dNo) {
        return null;
    }

    @Override
    public JSONObject getTeacherClazz(String tNo) {
        return null;
    }

    @Override
    public JSONObject getTeacherCourse(String tNo) {
        return null;
    }

    @Override
    public JSONObject getTeacherDepart(String tNO) {
        return null;
    }
}
