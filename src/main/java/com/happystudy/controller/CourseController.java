package com.happystudy.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.happystudy.constants.Constants;
import com.happystudy.service.CourseService;

import cn.hutool.json.JSONObject;

@Controller
@RequestMapping("happy-study/course")
public class CourseController {
    @Autowired
    CourseService courseService;
    
    @RequestMapping("")
	public String index() {
		return "course_man";
	}

    //查询课程（5个参数）
    @ResponseBody
    public JSONObject queryCourse(Map<String, Object> param)
    {
        Map<String,Object> newParam=new HashMap<String,Object>();
        if (param.get("keyword")==null){
            newParam.put("keyword","co_no");
        } else newParam.put("keyword",param.get("keyword"));

        if (param.get("orderBy")==null){
            newParam.put("orderBy","co_no");
        }else newParam.put("orderBy",param.get("orderBy"));

        if (param.get("orderWay")==null){
            newParam.put("orderWay","asc");
        }else newParam.put("orderWay",param.get("orderWay"));

        if (param.get("pageOffset")==null){
            newParam.put("pageOffset","1");
        }else newParam.put("pageOffset",param.get("pageOffset"));

        if (param.get("pageSize")==null){
            newParam.put("pageSize","5");
        }else newParam.put("pageSize",param.get("pageSize"));

        return this.courseService.queryCourse(newParam);
    }

    //查询课程数（默认为所有课程）
    @ResponseBody
    public JSONObject queryCourseCount(String keyword){
        return this.courseService.queryCourseCount(keyword);
    }

    //添加课程
    public  JSONObject addCourse(String coNo, String coName){
        if (coName.isEmpty()||coName.trim().isEmpty()||coNo.isEmpty()||coNo.trim().isEmpty()){
            return new JSONObject().set("status",Constants.NULL_PARAM_ERROR);
        }
        else return this.courseService.addCourse(coNo,coName);
    }

    //修改课程名
    public  JSONObject updateCourseByNo(String coNo, String coName){
        if (coName.isEmpty()||coName.trim().isEmpty()||coNo.isEmpty()||coNo.trim().isEmpty()){
            return new JSONObject().set("status",Constants.NULL_PARAM_ERROR);
        }
        else return this.courseService.updateCourseByNo(coNo,coName);
    }

    //根据课程号删除课程
    public JSONObject deleteCourseByNo(String coNo){
        if (coNo.isEmpty()||coNo.trim().isEmpty()){
            return new JSONObject().set("status",Constants.NULL_PARAM_ERROR);
        }
        else return this.courseService.deleteCourseByNo(coNo);
    }

    //根据课程号精确匹配课程
    public JSONObject findCourseByNo(String coNo){
        if (coNo.isEmpty()||coNo.trim().isEmpty()){
            return new JSONObject().set("status",Constants.NULL_PARAM_ERROR);
        }
        else return this.courseService.findCourseByNo(coNo);
    }

    //通过老师找课程
    public JSONObject getTeacherCourse(String tNo){
        if (tNo.isEmpty()||tNo.trim().isEmpty()){
            return new JSONObject().set("status",Constants.NULL_PARAM_ERROR);
        }
        else return this.courseService.getTeacherCourse(tNo);
    }

    //通过学院找课程
    public JSONObject findCourseByDepart(String dNo){
        if (dNo.isEmpty()||dNo.trim().isEmpty())
        {
            return new JSONObject().set("status",Constants.NULL_PARAM_ERROR);
        }
        else return this.courseService.findCourseByDepart(dNo);
    }

    //查询报了该课程的所有学生
    public JSONObject queryCourseAllStu(String cNo){
        if (cNo.isEmpty()||cNo.trim().isEmpty())
        {
            return new JSONObject().set("status",Constants.NULL_PARAM_ERROR);
        }
        else return this.courseService.queryCourseAllStu(cNo);
    }

    //查询该课程的学生人数
    public JSONObject queryCourseStudentCount(String cNo){
        if (cNo.isEmpty()||cNo.trim().isEmpty())
        {
            return new JSONObject().set("status",Constants.NULL_PARAM_ERROR);
        }
        else return this.courseService.queryCourseStudentCount(cNo);
    }
}
