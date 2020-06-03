package com.happystudy.controller;

import cn.hutool.json.JSONObject;
import com.happystudy.constants.Constants;
import com.happystudy.service.DepartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/happy-study/depart")
public class DepartController {
    @Autowired
    DepartService departService;

    //查询学院（5个参数）
    @PostMapping("/queryDepart")
    @ResponseBody
    public JSONObject queryDepart(Map<String, Object> param){
        String keyword;
        if (param.get("keyword")==null)keyword="d_no";
        else keyword= (String) param.get("keyword");

        String orderBy;
        if (param.get("orderBy")==null)orderBy="d_no";
        else orderBy= (String) param.get("orderBy");

        String orderWay;
        if (param.get("orderWay")==null)orderWay="asc";
        else orderWay= (String) param.get("orderWay");

        int pageNo;
        if (param.get("pageOffset")==null)pageNo=1;
        else pageNo= (int)param.get("pageOffset");

        int pageSize;
        if (param.get("pageSize")==null)pageSize=5;
        else pageSize= (int) param.get("pageSize");

        return this.departService.queryDepart(keyword,orderBy,orderWay,pageNo,pageSize);
    }


    //查询学院个数（默认为总个数）
    @PostMapping("/queryDepartCount")
    @ResponseBody
    public JSONObject queryDepartCount(String keyword){
        if (keyword.isEmpty()||keyword.trim().isEmpty()){
            return queryDepartCount("");
        }
        else {
            return queryDepartCount(keyword);
        }
    }

    //添加学院
    @PostMapping("/addDepart")
    @ResponseBody
    public JSONObject addDepart(String dNo, String dName)
    {
        if (dName.isEmpty()||dName.trim().isEmpty()||dNo.isEmpty()||dNo.trim().isEmpty())
        {
            return new JSONObject().set("status",Constants.NULL_PARAM_ERROR);
        }
        else return this.departService.addDepart(dNo,dName);
    }

    //修改学院名字
    @PostMapping("/updateDepartByNo")
    @ResponseBody
    public  JSONObject updateDepartByNo(String dNo, String dName){
            if (dNo.isEmpty()||dNo.trim().isEmpty()||dName.trim().isEmpty()||dName.isEmpty())
            {
                return new JSONObject().set("status",Constants.NULL_PARAM_ERROR);
            }
            else return this.departService.updateDepartByNo(dNo,dName);
    }

    //根据学院号删除学院
    @PostMapping("/deleteDepartByNo")
    @ResponseBody
    public JSONObject deleteDepartByNo(String dNo){
            if (dNo.isEmpty()||dNo.trim().isEmpty())
            {
                return new JSONObject().set("status",Constants.NULL_PARAM_ERROR);
            }
            else return this.departService.deleteDepartByNo(dNo);
    }

    //根据学院号精准匹配学院
    @PostMapping("/findDepartByNo")
    @ResponseBody
    public JSONObject findDepartByNo(String dNo){
        if (dNo.isEmpty()||dNo.trim().isEmpty())
        {
            return new JSONObject().set("status",Constants.NULL_PARAM_ERROR);
        }
        else return this.departService.findDepartByNo(dNo);
    }

    //查找某个学院的课程
    @PostMapping("/getDepartCourse")
    @ResponseBody
    public JSONObject getDepartCourse(String dNo){
        if (dNo.isEmpty()||dNo.trim().isEmpty())
        {
            return new JSONObject().set("status",Constants.NULL_PARAM_ERROR);
        }
        else return this.departService.getDepartCourse(dNo);
    }

    //统计该学院的学生人数
    @PostMapping("/queryDepartAllStu")
    @ResponseBody
    public JSONObject queryDepartAllStu(String dNo){
        if (dNo.isEmpty()||dNo.trim().isEmpty())
        {
            return new JSONObject().set("status",Constants.NULL_PARAM_ERROR);
        }
        else return this.departService.queryDepartAllStu(dNo);
    }

    //统计该学院的课程总数
    @PostMapping("/queryDepartAllCourse")
    @ResponseBody
    public JSONObject queryDepartAllCourse(String dNo){
        if (dNo.isEmpty()||dNo.trim().isEmpty())
        {
            return new JSONObject().set("status",Constants.NULL_PARAM_ERROR);
        }
        else return this.departService.queryDepartAllCourse(dNo);
    }

    //统计该学院的老师人数
    @PostMapping("/queryDepartAllTeaCount")
    @ResponseBody
    public JSONObject queryDepartAllTeaCount(String dNo)
    {
        if (dNo.isEmpty()||dNo.trim().isEmpty())
        {
            return new JSONObject().set("status",Constants.NULL_PARAM_ERROR);
        }
        else return this.departService.queryDepartAllTeaCount(dNo);
    }
}
