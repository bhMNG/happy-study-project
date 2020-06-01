package com.happystudy.controller;

import cn.hutool.json.JSONObject;
import com.happystudy.constants.Constants;
import com.happystudy.service.ClazzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class ClazzController {
    @Autowired
    ClazzService clazzService;

    //查询班级（5个参数）
    @ResponseBody
    public JSONObject queryClazz(Map<String, Object> param){
        Map<String,Object> newParam=new HashMap<String,Object>();
        if (param.get("keyword")==null){
            newParam.put("keyword","c_no");
        } else newParam.put("keyword",param.get("keyword"));

        if (param.get("orderBy")==null){
            newParam.put("orderBy","c_no");
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

        return this.clazzService.queryClazz(newParam);
    }

    //查询班级个数（默认为所有班级个数）
    @ResponseBody
    public JSONObject queryClazzCount(String keyword){
        return this.clazzService.queryClazzCount(keyword);
    }

    //添加班级
    @ResponseBody
    public JSONObject addClazz(String cNo, String cName){
        if (cName.isEmpty()||cName.trim().isEmpty()||cNo.isEmpty()||cNo.trim().isEmpty())
        {
            return new JSONObject().set("status",Constants.NULL_PARAM_ERROR);
        }
        else return this.clazzService.addClazz(cNo,cName);
    }

    //修改班级信息
    @ResponseBody
    public JSONObject updateClazzByNo(String cNo, Map<String, Object> param){
        if (cNo.isEmpty()||cNo.trim().isEmpty())
        {
            return new JSONObject().set("status",Constants.NULL_CLAZZ);
        }
        else {
            Map<String,Object> newParam=new HashMap<String,Object>();
            if (param.get("c_name")!=null)
            {
                newParam.put("c_name",param.get("c_name"));
            }
            if (param.get("c_enter_year")!=null)
            {
                newParam.put("c_enter_year",param.get("c_enter_year"));
            }
            return this.clazzService.updateClazzByNo(cNo,newParam);
        }
    }

    //删除班级
    @ResponseBody
    public JSONObject deleteClazzByNo(String cNo){
        if (cNo.isEmpty()||cNo.trim().isEmpty())
        {
            return new JSONObject().set("status",Constants.NULL_PARAM_ERROR);
        }
        else{
            return this.clazzService.deleteClazzByNo(cNo);
        }
    }

    //根据班级号精确匹配班级
    @ResponseBody
    public JSONObject findClazzBycNo(String cNo){
        if (cNo.isEmpty()||cNo.trim().isEmpty())
        {
            return new JSONObject().set("status",Constants.NULL_PARAM_ERROR);
        }
        else return this.clazzService.findClazzByNo(cNo);
    }

    //通过老师找班级
    @ResponseBody
    public  JSONObject findClazzByTeacher(String tNo){
        if (tNo.trim().isEmpty()||tNo.isEmpty()){
            return new JSONObject().set("status",Constants.NULL_PARAM_ERROR);
        }
        else return this.clazzService.findClazzByTeacher(tNo);
    }

    //查询该班级的所有学生
    public JSONObject queryClazzAllStu(String cNo){
        if (cNo.isEmpty()||cNo.trim().isEmpty()){
            return new JSONObject().set("status",Constants.NULL_PARAM_ERROR);
        }
        else return this.clazzService.queryClazzAllStu(cNo);
    }

    //查询该班级的学生人数
    public JSONObject queryClazzStuCount(String cNo){
        if (cNo.isEmpty()||cNo.trim().isEmpty()){
            return new JSONObject().set("status",Constants.NULL_PARAM_ERROR);
        }
        return this.clazzService.queryClazzStuCount(cNo);
    }

}
