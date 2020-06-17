package com.happystudy.controller;

import cn.hutool.json.JSONObject;
import com.happystudy.constants.Constants;
import com.happystudy.service.GradeService;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/happy-study/grade")
public class GradeController {
    @Autowired
    GradeService gradeService;

    @RequestMapping("")
    public String index() {
    	return "grade_man";
    }



    //查找某个课程的成绩单!!!!!!!!!!!!!!!!!!!!!!
    @PostMapping("/findGradeByCourse")
    @ResponseBody
    public JSONObject findGradeByCourse(String coNo){
        if (coNo==null||coNo.trim().isEmpty())
        {
            return new JSONObject().set("status",Constants.NULL_COURSE);
        }
        else {
            return this.gradeService.findGradeByCourse(coNo);
        }
    }



    //查询某个学生的成绩单!!!!!!!!!!!!!!!!!!!
    @PostMapping("/findGradeByStudent")
    @ResponseBody
    public JSONObject findGradeByStudent(String sNo){
        if (sNo==null||sNo.trim().isEmpty())
        {
            return new JSONObject().set("status",Constants.NULL_COURSE);
        }
        else {
            return this.gradeService.findGradeByStudent(sNo);
        }
    }

    //查询某个学生某门课程的成绩单 || 查询某门课程某个学生的成绩单
    @PostMapping("/findGradeByCS")
    @ResponseBody
    public JSONObject findGradeByCS(String coNo, String sNo){
        if (sNo==null||coNo==null||sNo.trim().isEmpty()||coNo.trim().isEmpty())
        {
            return new JSONObject().set("status",Constants.NULL_COURSE);
        }
        else {
            return this.gradeService.findGradeByCS(coNo,sNo);
        }
    }
    
    //查询成绩
    //	代码添加或修改
    @PostMapping("/queryGrade")
    @ResponseBody
    public JSONObject queryGrade(String coNo,String cNo,String tNo,String dNo, String orderBy, String orderWay){
    	if (orderBy == null || orderBy.equals("")) {
    		orderBy = "co_no";
    	}
    	if (orderWay == null || orderWay.equals("")) {
    		orderWay = "desc";
    	}
    	
    	if (coNo == null) {
    		coNo = "";
    	}
    	if (cNo == null) {
    		cNo = "";
    	}
    	if (tNo == null) {
    		tNo = "";
    	}
    	if (dNo == null) {
    		dNo = "";
    	}
        Map<String,Object> param=new HashMap<>();
        param.put("coNo",coNo);
        param.put("cNo",cNo);
        param.put("tNo",tNo);
        param.put("dNo",dNo);
        param.put("orderBy", orderBy);
        param.put("orderWay", orderWay);
        return this.gradeService.queryGrade(param);
    }
    //所有成绩（有成绩的+选了课还没有成绩的）
    @PostMapping("/queryGradeWithNull")
    @ResponseBody
    public JSONObject queryGradeWithNull(String coNo,String cNo,String tNo,String dNo, String orderBy, String orderWay){
    	if (orderBy == null || orderBy.equals("")) {
    		orderBy = "co_no";
    	}
    	if (orderWay == null || orderWay.equals("")) {
    		orderWay = "asc";
    	}
    	//因为返回的数组是倒叙过后了的
    	if (orderWay.equals("asc")) {
    		orderWay="desc";
    	}else {
    		orderWay="asc";
    	}
    	if (coNo == null) {
    		coNo = "";
    	}
    	if (cNo == null) {
    		cNo = "";
    	}
    	if (tNo == null) {
    		tNo = "";
    	}
    	if (dNo == null) {
    		dNo = "";
    	}
        Map<String,Object> param=new HashMap<>();
        param.put("coNo",coNo);
        param.put("cNo",cNo);
        param.put("tNo",tNo);
        param.put("dNo",dNo);
        param.put("orderBy", orderBy);
        param.put("orderWay", orderWay);
        return this.gradeService.queryGradeWithNull(param);
    }
    //录入成绩
    @PostMapping("/enterScore")
    @ResponseBody
    public JSONObject enterScore(String sNo, String coNo, String score) {
    	if (sNo == null || coNo==null || score==null || sNo.equals("") || coNo.equals("") || score.equals("")) {
    		return  new JSONObject().set("status", Constants.NULL_PARAM_ERROR);
    	}
    	return gradeService.enterScore(sNo, coNo, score);
    }
}
