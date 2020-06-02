package com.happystudy.service.impl;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.happystudy.constants.Constants;
import com.happystudy.dao.GradeMapper;
import com.happystudy.model.Grade;
import com.happystudy.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author LJS
 * @data 2020/6/2 11:28
 */
@Service
public class GradeServiceImpl implements GradeService {
    @Autowired
    private GradeMapper gradeMapper;

    //查询某个课程的成绩单
    @Override
    public JSONObject findGradeByCourse(String cNo) {
        JSONObject json=new JSONObject();
        List<Grade> gradeList = gradeMapper.findGradeByCourse(cNo);
        json.set("status", Constants.SUCCESS);
        json.set("gradeList", JSONUtil.parseArray(gradeList));
        return json;
    }

    //查询某个学生的成绩
    @Override
    public JSONObject findGradeByStudent(String sNo) {
        JSONObject json=new JSONObject();
        List<Grade> gradeList = gradeMapper.findGradeByStudent(sNo);
        json.set("status", Constants.SUCCESS);
        json.set("gradeList", JSONUtil.parseArray(gradeList));
        return json;
    }

    //查询某个学生某门课程的成绩单 || 查询某门课程某个学生的成绩单
    @Override
    public JSONObject findGradeByCS(String cNo, String sNo) {
        JSONObject json=new JSONObject();
        Grade grade = gradeMapper.findGradeByCS(cNo, sNo);
        json.set("status", Constants.SUCCESS);
        json.set("grade",grade);
        return json;
    }
}
