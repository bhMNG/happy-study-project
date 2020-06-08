package com.happystudy.controller;

import cn.hutool.json.JSONObject;
import com.happystudy.constants.Constants;
import com.happystudy.service.GradeService;
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



}
