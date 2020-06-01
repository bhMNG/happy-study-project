package com.happystudy.service;

import cn.hutool.json.JSONObject;

import java.util.Map;

/**
 * @author LJS
 * @data 2020/5/31 13:12
 */

public interface ClazzService {
    //查询班级
    public JSONObject queryClazz(Map<String,Object> param);
    //查询班级个数（默认班级人数）
    public JSONObject queryClazzCount(String keyword);
    //添加班级
    public JSONObject addClazz(String cNo,String cName);
    //修改班级信息
    public JSONObject updateClazzByNo(String cNo,Map<String,Object> param);
    //删除班级
    public JSONObject deleteClazzByNo(String cNo);
    //通过老师找班级
    public JSONObject findClazzByTeacher(String tNo);
    //查询该班级的所有学生
    public JSONObject queryClazzAllStu(String cNo);
    //查询该班级的学生人数
    public JSONObject queryClazzStuCount(String cNo);
}
