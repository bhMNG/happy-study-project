package com.happystudy.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.happystudy.constants.Constants;
import com.happystudy.service.impl.StudentServiceImpl;

import cn.hutool.json.JSONObject;

@Controller
@RequestMapping("/happy-study/student")
public class StudentController {
	@Autowired
    StudentServiceImpl studentService;


	@RequestMapping("")
	public String index() {
		return "student_man";
	}
	
	@PostMapping("/queryStudent")
	@ResponseBody
    public JSONObject queryStudent(String keyword,String orderBy,String orderWay,Integer pageNo,Integer pageSize){
        Map<String,Object> newParam=new HashMap<String,Object>();
        if (keyword==null){
            keyword = "";
        }

        if (orderBy==null){
            orderBy = "s_no";
        }

        if (orderWay==null){
        	orderWay = "asc";
        }

        if (pageNo==null){
            pageNo=1;
        }

        if (pageSize==null){
            pageSize = 5;
        }

        return this.studentService.queryStudent(keyword, orderBy, orderWay, pageNo, pageSize);
    }

    //查询学生人数（可以根据条件查询，默认为总人数）
    @ResponseBody
    public JSONObject queryStudentCount(Map<String,Object> param){
        if (param.get("keyword")==null)
        {
            param.put("keyword","");
        }
        Map<String,Object> param2 =new HashMap<>();
        param2.put("s_sex","1");
        return this.studentService.queryStudentCount(param2);
    }

    //添加学生
    @ResponseBody
    public JSONObject addStudent(String sNo, String sName){
        if (sName.isEmpty()||sNo.isEmpty()||sName.trim().isEmpty()||sNo.trim().isEmpty())
        {
            return new JSONObject().set("status",Constants.NULL_PARAM_ERROR);
        }

        else return this.studentService.addStudent(sNo,sName);
    }

    //根据学号删除学生
    @ResponseBody
    public JSONObject deleteStudentByNo(String sNo){
        if (sNo.isEmpty()||sNo.trim().isEmpty())
        {
            return new JSONObject().set("status",Constants.NULL_PARAM_ERROR);
        }
        else return deleteStudentByNo(sNo);
    }

    //根据学号修改学生
    @ResponseBody
    public JSONObject updateStudentByNo(Map<String, Object> param){
    	Object sNo = param.get("sNo");
    	if (sNo == null) {
    		return new JSONObject().set("status",Constants.NULL_PARAM_ERROR);
    	}
        
        return this.studentService.updateStudentByNo(param);
    }

    //获取学生所在班级
    @ResponseBody
    public JSONObject getStudentClazz(String sNo){
        if (sNo.trim().isEmpty()||sNo.isEmpty())
        {
            return new JSONObject().set("status",Constants.NULL_PARAM_ERROR);
        }
        else {
            return this.studentService.getStudentClazz(sNo);
        }
    }

    //获取学生成绩
    @ResponseBody
    public JSONObject getStudentScore(String sNo){
        if (sNo.trim().isEmpty()||sNo.isEmpty())
        {
            return new JSONObject().set("status",Constants.NULL_PARAM_ERROR);
        }
        else {
            return this.studentService.getStudentScore(sNo);
        }
    }

    //学生选课 //TODO 暂时注释 service还未实现该方法
    /*@ResponseBody
    public JSONObject courseSelective(String sNo, String cNo){
    	if (sNo == null || sNo == "") {
    		return new JSONObject().set("status",Constants.NULL_PARAM_ERROR);
    	}
        
      
        return this.studentService.courseSelective(sNo,cNo);
  
    }*/

    //获取学生已选课程
    @ResponseBody
    public JSONObject getStudentCourse(String sNo){
        if (sNo.isEmpty()||sNo.trim().isEmpty())
        {
            return new JSONObject().set("status",Constants.NULL_PARAM_ERROR);
        }
        else
        {
            return this.studentService.getStudentCourse(sNo);
        }
    }

    //获取学生的学院
    @ResponseBody
    public JSONObject getStudentDepart(String sNo){
        if (sNo.isEmpty()||sNo.trim().isEmpty())
        {
            return new JSONObject().set("status",Constants.NULL_PARAM_ERROR);
        }
        else return this.studentService.getStudentDepart(sNo);
    }
}
