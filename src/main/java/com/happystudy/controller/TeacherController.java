package com.happystudy.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.happystudy.constants.Constants;
import com.happystudy.service.impl.TeacherServiceImpl;

import cn.hutool.json.JSONObject;

@Controller
@RequestMapping("/happy-study/teacher")
public class TeacherController {
	@Autowired
    TeacherServiceImpl teacherService;

	@RequestMapping("")
	public String index() {
		return "teacher_man";
	}
	
	 //查询教师（5个参数）
	@PostMapping("/queryTeacher")
    @ResponseBody
    public JSONObject queryTeacher(String keyword,String orderBy,String orderWay,Integer pageNo,Integer pageSize){
        Map<String,Object> newParam=new HashMap<String,Object>();
        if (keyword==null){
            keyword = "t_no";
        }

        if (orderBy==null){
            orderBy = "t_no";
        }

        if (orderWay==null){
            orderWay = "asc";
        }

        if (pageNo==null){
            pageNo = 1;
        }

        if (pageSize==null){
            pageSize = 5;
        }

        return this.teacherService.queryTeacher(keyword,orderBy,orderWay,pageNo,pageSize);
    }

    //查询教师人数（默认为所有教师人数）
    @ResponseBody
    public JSONObject queryTeacherCount(String keyword){
    	Map<String, Object> param = new HashMap<>();
    	if (keyword==null) {
    		keyword="";
    	}
    	if (keyword.equals("d_no")) {
    		param.put("d_no", keyword);
    	}
    	// maby TODO
    	
        return this.teacherService.queryTeacherCount(param);
        
    }

    //添加教师   !!!!!!有些字段没赋值
    @PostMapping("/addTeacher")
    @ResponseBody
    public JSONObject addTeacher(String tNo, String tName)
    {
        if (tName==null||tNo==null||tNo.trim().isEmpty()||tName.trim().isEmpty())
        {
            return new JSONObject().set("status",Constants.NULL_PARAM_ERROR);
        }
        else return this.teacherService.addTeacher(tNo,tName);
    }

    //更新教师
    @PostMapping("/updateTeacher")
    @ResponseBody
    public JSONObject updateTeacher(String tNo,String tName,String tSex){
    	
        if (tNo==null||tNo.trim().isEmpty())
        {
            return new JSONObject().set("status",Constants.NULL_TEACHER);
        }
        if ((tName==null||tName.trim().isEmpty())&&(tSex==null||tSex.trim().isEmpty())){
            return new JSONObject().set("status",Constants.NULL_PARAM_ERROR);
        }
        else return this.teacherService.updateTeacher(tNo,tName,tSex);
    }

    //删除教师
    @PostMapping("/deleteClazzByNo")
    @ResponseBody
    public JSONObject deleteClazzByNo(String tNo){
        if (tNo.isEmpty()||tNo.trim().isEmpty())
        {
            return new JSONObject().set("status",Constants.NULL_PARAM_ERROR);
        }
        else {
            return this.teacherService.deleteClazzByNo(tNo);
        }
    }

    //根据班级获取老师
    @PostMapping("/findTeacherByClazz")
    @ResponseBody
    public JSONObject findTeacherByClazz(String cNo){
        if (cNo==null||cNo.trim().isEmpty()){
            return new JSONObject().set("status",Constants.NULL_PARAM_ERROR);
        }
        else return this.teacherService.findTeacherByClazz(cNo);
    }


    //根据课程找老师
    @PostMapping("/findTeacherByCourse")
    @ResponseBody
    public JSONObject findTeacherByCourse(String coNo)
    {
        if (coNo==null||coNo.trim().isEmpty()){
            return new JSONObject().set("status",Constants.NULL_PARAM_ERROR);
        }
        else return this.teacherService.findTeacherByCourse(coNo);
    }

  //根据学院找老师
    @PostMapping("/findTeacherByDepart")
    @ResponseBody
    public JSONObject findTeacherByDepart(String dNo)
    {
        if (dNo==null||dNo.trim().isEmpty()){
            return new JSONObject().set("status",Constants.NULL_PARAM_ERROR);
        }
        else return this.teacherService.findTeacherByDepart(dNo);
    }

  //获得该老师所负责的班级
    @PostMapping("/getTeacherClazz")
    @ResponseBody
    public JSONObject getTeacherClazz(String tNo)
    {
        if (tNo==null||tNo.trim().isEmpty()){
            return new JSONObject().set("status",Constants.NULL_PARAM_ERROR);
        }
        else return this.teacherService.getTeacherClazz(tNo);
    }

    //获得该老师所负责的课程
    @PostMapping("/getTeacherCourse")
    @ResponseBody
    public JSONObject getTeacherCourse(String tNo){
        if (tNo==null||tNo.trim().isEmpty()){
            return new JSONObject().set("status",Constants.NULL_PARAM_ERROR);
        }
        else return this.teacherService.getTeacherCourse(tNo);
    }


    //获得该老师所属的学院
    @PostMapping("/getTeacherDepart")
    @ResponseBody
    public JSONObject getTeacherDepart(String tNo){
        if (tNo.isEmpty()||tNo.trim().isEmpty()){
            return new JSONObject().set("status",Constants.NULL_PARAM_ERROR);
        }
        else return this.teacherService.getTeacherDepart(tNo);
    }
}
