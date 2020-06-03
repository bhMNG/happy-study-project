package com.happystudy.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.happystudy.constants.Constants;
import com.happystudy.service.CourseService;

import cn.hutool.json.JSONObject;

@Controller
@RequestMapping("/happy-study/course")
public class CourseController {
	@Autowired
    CourseService courseService;


	@RequestMapping("")
	public String index() {
		return "course_man";
	}
	
	//查询课程（5个参数）
    @PostMapping("/queryCourse")
    @ResponseBody
    public JSONObject queryCourse(String keyword,String orderBy,String orderWay,Integer pageNo,Integer pageSize)
    {
        if (keyword==null){
            keyword = "co_no";
        }

        if (orderBy==null){
            orderBy = "co_no";
        }

        if (orderWay==null){
            orderWay = "asc";
        }

        if (pageNo==null){
            pageNo = 1;
        }

        if (pageSize == null){
            pageSize = 5;
        }

        return this.courseService.queryCourse(keyword, orderBy, orderWay, pageNo, pageSize);
    }

    //查询课程数（默认为所有课程）
    @PostMapping("/queryCourse")
    @ResponseBody
    public JSONObject queryCourseCount(String keyword, String keyType){
    	Map<String, Object> param = new HashMap<>();
    	if (keyType == null || keyType.equals("")) {
    		param.put("keyType", "");
    		param.put("keyword", "");
    	}else if(keyword.equals("d_no")) {
    		param.put(keyType, "d_no");
    		param.put("keyword", keyword);
    	}
    	
        return this.courseService.queryCourseCount(param);
    }

    //添加课程
    @PostMapping("/addCourse")
    @ResponseBody
    public  JSONObject addCourse(String coNo, String coName){
        if (coName.isEmpty()||coName.trim().isEmpty()||coNo.isEmpty()||coNo.trim().isEmpty()){
            return new JSONObject().set("status",Constants.NULL_PARAM_ERROR);
        }
        else return this.courseService.addCourse(coNo,coName);
    }

    //修改课程名
    @PostMapping("/updateCourseByNo")
    @ResponseBody
    public  JSONObject updateCourseByNo(String coNo, String coName){
    	Map<String, Object> param = new HashMap<>();
        if (coNo==null||coName==null||coName.isEmpty()||coNo.isEmpty()||coNo.trim().isEmpty()||coName.trim().isEmpty()){
            return new JSONObject().set("status",Constants.NULL_PARAM_ERROR);
        }
        param.put("coNo", coNo);
        param.put("coName", coName);
        return this.courseService.updateCourseByNo(coNo, param);
    }

    //根据课程号删除课程
    @PostMapping("/deleteCourseByNo")
    @ResponseBody
    public JSONObject deleteCourseByNo(String coNo){
        if (coNo.isEmpty()||coNo.trim().isEmpty()){
            return new JSONObject().set("status",Constants.NULL_PARAM_ERROR);
        }
        else return this.courseService.deleteCourseByNo(coNo);
    }

    //根据课程号精确匹配课程
    @PostMapping("/findCourseByNo")
    @ResponseBody
    public JSONObject findCourseByNo(String coNo){
        if (coNo.isEmpty()||coNo.trim().isEmpty()){
            return new JSONObject().set("status",Constants.NULL_PARAM_ERROR);
        }
        else return this.courseService.findCourseByNo(coNo);
    }

    //通过老师找课程
    @PostMapping("/getTeacherCourse")
    @ResponseBody
    public JSONObject getTeacherCourse(String tNo){
        if (tNo.isEmpty()||tNo.trim().isEmpty()){
            return new JSONObject().set("status",Constants.NULL_PARAM_ERROR);
        }
        else return this.courseService.getTeacherCourse(tNo);
    }

    //通过学院找课程
    @PostMapping("/findCourseByDepart")
    @ResponseBody
    public JSONObject findCourseByDepart(String dNo){
        if (dNo.isEmpty()||dNo.trim().isEmpty())
        {
            return new JSONObject().set("status",Constants.NULL_PARAM_ERROR);
        }
        else return this.courseService.findCourseByDepart(dNo);
    }

    //查询报了该课程的所有学生
    @PostMapping("/queryCourseAllStu")
    @ResponseBody
    public JSONObject queryCourseAllStu(String cNo){
        if (cNo.isEmpty()||cNo.trim().isEmpty())
        {
            return new JSONObject().set("status",Constants.NULL_PARAM_ERROR);
        }
        else return this.courseService.queryCourseAllStu(cNo);
    }

    //查询该课程的学生人数
    @PostMapping("/queryCourseStudentCount")
    @ResponseBody
    public JSONObject queryCourseStudentCount(String cNo){
        if (cNo.isEmpty()||cNo.trim().isEmpty())
        {
            return new JSONObject().set("status",Constants.NULL_PARAM_ERROR);
        }
        else return this.courseService.queryCourseStudentCount(cNo);
    }
}
