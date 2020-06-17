package com.happystudy.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.happystudy.constants.Constants;
import com.happystudy.model.Role;
import com.happystudy.service.CourseService;
import com.happystudy.service.UserService;
import com.happystudy.service.impl.CourseServiceImpl;

import cn.hutool.json.JSONObject;

@Controller
@RequestMapping("/happy-study/course")
public class CourseController {
	@Autowired
    CourseService courseService;
	@Autowired
	UserService userService;
	
	@RequestMapping("")
	public String index() {
		return "course_man";
	}
	
	//查询课程（5个参数）
    @PostMapping("/queryCourse")
    @ResponseBody
    public JSONObject queryCourse(String keyword,String orderBy,String orderWay,Integer pageNo,Integer pageSize)
    {
        if (keyword==null || keyword.equals("")){
            keyword = "";
        }
        if (orderBy==null || orderBy.equals("")){
            orderBy = "co_no";
        }

        if (orderWay==null || orderWay.equals("")){
            orderWay = "asc";
        }

        if (pageNo==null || pageNo.equals("")){
            pageNo = 1;
        }

        if (pageSize==null || pageSize.equals("")){
            pageSize = 5;
        }
        return this.courseService.queryCourse(keyword, orderBy, orderWay, pageNo, pageSize);
    }

    //查询课程数（默认为所有课程）
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
        if (coName==null||coNo==null||coName.trim().isEmpty()||coNo.trim().isEmpty()){
            return new JSONObject().set("status",Constants.NULL_PARAM_ERROR);
        }
        else return this.courseService.addCourse(coNo,coName);
    }

    //修改课程名
    @PostMapping("/updateCourse")
    @ResponseBody
    public  JSONObject updateCourseByNo(String coNo, String coName){
        if (coNo==null||coName==null||coName.isEmpty()||coNo.isEmpty()||coNo.trim().isEmpty()||coName.trim().isEmpty()){
            return new JSONObject().set("status",Constants.NULL_PARAM_ERROR);
        }

        return this.courseService.updateCourseByNo(coNo,coName);
    }

    //根据课程号删除课程
    @PostMapping("/deleteCourse")
    @ResponseBody
    public JSONObject deleteCourseByNo(String coNo){
        if (coNo==null||coNo.trim().isEmpty()){
            return new JSONObject().set("status",Constants.NULL_PARAM_ERROR);
        }
        else return this.courseService.deleteCourseByNo(coNo);
    }

    //根据课程号精确匹配课程
    @PostMapping("/findCourseByNo")
    @ResponseBody
    public JSONObject findCourseByNo(String coNo){
        if (coNo==null||coNo.trim().isEmpty()){
            return new JSONObject().set("status",Constants.NULL_PARAM_ERROR);
        }
        else return this.courseService.findCourseByNo(coNo);
    }

    //通过老师找课程
    @PostMapping("/getTeacherCourse")
    @ResponseBody
    public JSONObject getTeacherCourse(String tNo){
        if (tNo==null||tNo.trim().isEmpty()){
            return new JSONObject().set("status",Constants.NULL_PARAM_ERROR);
        }
        else return this.courseService.getTeacherCourse(tNo);
    }

    //通过学院找课程
    @PostMapping("/findCourseByDepart")
    @ResponseBody
    public JSONObject findCourseByDepart(String dNo){
        if (dNo==null||dNo.trim().isEmpty())
        {
            return new JSONObject().set("status",Constants.NULL_PARAM_ERROR);
        }
        else return this.courseService.findCourseByDepart(dNo);
    }

    //查询报了该课程的所有学生
    @PostMapping("/queryCourseAllStu")
    @ResponseBody
    public JSONObject queryCourseAllStu(String cNo){
        if (cNo==null||cNo.trim().isEmpty())
        {
            return new JSONObject().set("status",Constants.NULL_PARAM_ERROR);
        }
        else return this.courseService.queryCourseAllStu(cNo);
    }


  //查询该课程的学生人数
    @PostMapping("/queryCourseStudentCount")
    @ResponseBody
    public JSONObject queryCourseStudentCount(String coNo){
        if (coNo==null||coNo.trim().isEmpty())
        {
            return new JSONObject().set("status",Constants.NULL_PARAM_ERROR);
        }
        else return this.courseService.queryCourseStudentCount(coNo);
    }
    
    //查询状态为0的课程
    @PostMapping("/queryInitStateCourse")
    @ResponseBody
    public JSONObject queryInitStateCourse(String keyword, String orderBy, String orderWay, String pageNo, String pageSize) {
    	if (keyword == null) {
    		keyword="";
    	}
    	if (orderBy == null || orderBy.equals("")) {
    		orderBy="co_no";
    	}
    	if (orderWay == null || orderWay.equals("")) {
    		orderWay="asc";
    	}
    	if (pageNo == null || pageNo.equals("")) {
    		pageNo="1";
    	}
    	if (pageSize == null || pageSize.equals("")) {
    		pageSize="5";
    	}
    	return courseService.queryCourseByStatus(keyword, orderBy, orderWay, Integer.parseInt(pageNo), Integer.parseInt(pageSize), 0);
    }
    //查询状态为1的课程
    @PostMapping("/queryApplyingStateCourse")
    @ResponseBody
    public JSONObject queryApplyingStateCourse(String keyword, String orderBy, String orderWay, String pageNo, String pageSize) {
    	if (keyword == null) {
    		keyword="";
    	}
    	if (orderBy == null || orderBy.equals("")) {
    		orderBy="co_no";
    	}
    	if (orderWay == null || orderWay.equals("")) {
    		orderWay="asc";
    	}
    	if (pageNo == null || pageNo.equals("")) {
    		pageNo="1";
    	}
    	if (pageSize == null || pageSize.equals("")) {
    		pageSize="5";
    	}
    	return courseService.queryCourseByStatus(keyword, orderBy, orderWay, Integer.parseInt(pageNo), Integer.parseInt(pageSize), 1);
    }
    //查询状态为2的课程
    @PostMapping("/queryChoosedStateCourse")
    @ResponseBody
    public JSONObject queryChoosedStateCourse(String keyword, String orderBy, String orderWay, String pageNo, String pageSize) {
    	if (keyword == null) {
    		keyword="";
    	}
    	if (orderBy == null || orderBy.equals("")) {
    		orderBy="co_no";
    	}
    	if (orderWay == null || orderWay.equals("")) {
    		orderWay="asc";
    	}
    	if (pageNo == null || pageNo.equals("")) {
    		pageNo="1";
    	}
    	if (pageSize == null || pageSize.equals("")) {
    		pageSize="5";
    	}
    	return courseService.queryCourseByStatus(keyword, orderBy, orderWay, Integer.parseInt(pageNo), Integer.parseInt(pageSize), 2);
    }
    //负责课程申请
    @PostMapping("/teacherApplyCourse")
    @ResponseBody
    public JSONObject teacherApplyCourse(String username, String coNo) {
    	Object role = userService.queryUserRole(username).get("role");
    	if (role==null) {
    		return new JSONObject().set("status", Constants.NULL_RESULT_ERROR);
    	}
    	System.out.println(role.toString());
    	Integer property = ((JSONObject)role).getInt("rProperty");
    	if (property!=1&&property!=0) {
    		return new JSONObject().set("status", Constants.NULL_PARAM_ERROR);
    	}
    	JSONObject userJson = userService.queryUsertNo(username);
    	if (userJson.getInt("status")!=Constants.SUCCESS) {
    		return new JSONObject().set("status", Constants.DB_ERROR);
    	}
    	String tNo = userJson.getStr("tNo");
    	return courseService.setCourseStatus(tNo, coNo, 1);
    }
    //批准课程负责申请
    @PostMapping("/passCourseApply")
    @ResponseBody
    public JSONObject passCourseApply(String tNo, String coNo) {
    	if (tNo == null || tNo.equals("")) {
    		return new JSONObject().set("status", Constants.NULL_PARAM_ERROR);
    	}
    	if (coNo == null || coNo.equals("")) {
    		return new JSONObject().set("status", Constants.NULL_PARAM_ERROR);
    	}
    	return courseService.setCourseStatus(tNo, coNo, 2);
    }
    //驳回课程负责申请
    @PostMapping("/rejectCourseApply")
    @ResponseBody
    public JSONObject rejectCourseApply(String tNo, String coNo) {
    	if (tNo == null || tNo.equals("")) {
    		return new JSONObject().set("status", Constants.NULL_PARAM_ERROR);
    	}
    	if (coNo == null || coNo.equals("")) {
    		return new JSONObject().set("status", Constants.NULL_PARAM_ERROR);
    	}
    	return courseService.setCourseStatus(tNo, coNo, 0);
    }
    
    //查询负责课程申请
    @PostMapping("/queryApplyingCourse")
    @ResponseBody
    public JSONObject queryApplyingCourse(String username) {
    	if (username == null || "".equals(username)) {
    		return new JSONObject().set("status", Constants.NULL_PARAM_ERROR);
    	}
    	Integer property = ((JSONObject)userService.queryUserRole(username).get("role")).getInt("rProperty");
    	if (property != 1 && property != 0) {
    		return new JSONObject().set("status", Constants.NULL_RESULT_ERROR);
    	}
    	return userService.queryApplyingCourseByUsername(username);
    }
}
