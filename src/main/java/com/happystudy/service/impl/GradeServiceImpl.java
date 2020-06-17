package com.happystudy.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.happystudy.constants.Constants;
import com.happystudy.dao.CourseMapper;
import com.happystudy.dao.GradeMapper;
import com.happystudy.dao.StudentMapper;
import com.happystudy.model.Grade;
import com.happystudy.model.Student;
import com.happystudy.service.GradeService;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

/**
 * @author LJS
 * @data 2020/6/2 11:28
 */
@Service
public class GradeServiceImpl implements GradeService {
	@Autowired
    private GradeMapper gradeMapper;
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private StudentMapper studentMapper;


    //查询某个课程的成绩单
    @Override
    public JSONObject findGradeByCourse(String coNo) {
        JSONObject json = new JSONObject();
        if (courseMapper.findCourseByNo(coNo) == null) {
            return json.set("status",Constants.NULL_COURSE);
        } else {
            List<Grade> gradeList = gradeMapper.findGradeByCourse(coNo);
            json.set("status", Constants.SUCCESS);
            json.set("gradeList", JSONUtil.parseArray(gradeList));
            return json;
        }
    }
    //查询某个学生的成绩
    @Override
    public JSONObject findGradeByStudent(String sNo) {
        JSONObject json = new JSONObject();
        if (studentMapper.findStudentByNo(sNo) == null) {
            return json.set("status", Constants.NULL_COURSE);
        } else {
            List<Grade> gradeList = gradeMapper.findGradeByStudent(sNo);
            json.set("status", Constants.SUCCESS);
            json.set("gradeList", JSONUtil.parseArray(gradeList));
            return json;
        }
    }
    //查询某个学生某门课程的成绩单 || 查询某门课程某个学生的成绩单
    @Override
    public JSONObject findGradeByCS(String coNo,String sNo) {
        JSONObject json=new JSONObject();
        if (studentMapper.findStudentByNo(sNo) == null) {
            return json.set("status", Constants.NULL_COURSE);
        } else {
        Grade grade = gradeMapper.findGradeByCS(coNo, sNo);
        json.set("status", Constants.SUCCESS);
        json.set("grade",grade);
        return json;
        }
    }
	
    @Override
    public JSONObject queryGradeWithNull(Map<String,Object> param) {
        JSONObject json=new JSONObject();
        List<Map<String, Object>> mapList = gradeMapper.queryGrade(param);
        List<Map<String, Object>> mapListWithoutGrade = gradeMapper.queryNullGradeButHasCourseRow(param);
        for (Map<String, Object> item : mapListWithoutGrade) {
        	item.put("g_score", "");
        	mapList.add(item);
        }
        Collections.reverse(mapList);
        json.set("status",Constants.SUCCESS);
        json.set("gradeList",JSONUtil.parseArray(mapList));
        return json;
    }
    @Override
    public JSONObject queryGrade(Map<String,Object> param) {
        JSONObject json=new JSONObject();
        List<Map<String, Object>> mapList = gradeMapper.queryGrade(param);
        json.set("status",Constants.SUCCESS);
        json.set("gradeList",JSONUtil.parseArray(mapList));
        return json;
    }
    
    @Transactional
    @Override
    public JSONObject enterScore(String sNo, String coNo, String score) {
    	JSONObject result = new JSONObject();
    	try {
    		//判单学生有没有退课，如果已退课不予修改最终成绩
    		Student student = courseMapper.findStudentByCourseAndSno(coNo, sNo);
    		if (student==null) {
        		result.set("status", Constants.NULL_STU);
        		//result.set("error", "该学生以退出这门课程，无法再修改其最终成绩");
        		return result;
        	}
    		//判断执行修改还剩插入操作
    		Grade grade = gradeMapper.findGradeByCS(coNo, sNo);
        	if (grade != null) {
        		gradeMapper.updateStudentScore(sNo, coNo, score);
        		result.set("status", Constants.SUCCESS);
        	}else {
        		gradeMapper.setStudentScore(sNo, coNo, score);
        		result.set("status", Constants.SUCCESS);
        	}
        	return result;
    	}catch(Exception e) {
    		return result.set("status", Constants.DB_ERROR);
    	}
    	
    }
    
    
}
