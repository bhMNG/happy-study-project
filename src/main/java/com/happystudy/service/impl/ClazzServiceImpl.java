package com.happystudy.service.impl;

import cn.hutool.json.JSONObject;
import com.happystudy.service.ClazzService;

import java.util.Map;

/**
 * @author LJS
 * @data 2020/6/1 21:07
 */
public class ClazzServiceImpl implements ClazzService {
    //查询班级
    @Override
    public JSONObject queryClazz(Map<String, Object> param) {
        return null;
    }

    //查询班级个数（默认班级人数）
    @Override
    public JSONObject queryClazzCount(String keyword) {
        return null;
    }

    //添加班级
    @Override
    public JSONObject addClazz(String cNo, String cName) {
        return null;
    }

    //修改班级信息
    @Override
    public JSONObject updateClazzByNo(String cNo, Map<String, Object> param) {
        return null;
    }

    //删除班级
    @Override
    public JSONObject deleteClazzByNo(String cNo) {
        return null;
    }

    //通过老师找班级
    @Override
    public JSONObject findClazzByTeacher(String tNo) {
        return null;
    }

    //查询该班级的所有学生
    @Override
    public JSONObject queryClazzAllStu(String cNo) {
        return null;
    }

    //查询该班级的学生人数
    @Override
    public JSONObject queryClazzStuCount(String cNo) {
        return null;
    }
}
