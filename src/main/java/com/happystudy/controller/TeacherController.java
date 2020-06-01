package com.happystudy.controller;

import cn.hutool.json.JSONObject;
import com.happystudy.constants.Constants;
import com.happystudy.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/happy-study/teacher")
public class TeacherController {
    @Autowired
    TeacherService teacherService;

    //查询教师（5个参数）
    @ResponseBody
    public JSONObject queryTeacher(Map<String, Object> param){
        Map<String,Object> newParam=new HashMap<String,Object>();
        if (param.get("keyword")==null){
            newParam.put("keyword","t_no");
        } else newParam.put("keyword",param.get("keyword"));

        if (param.get("orderBy")==null){
            newParam.put("orderBy","t_no");
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

        return this.teacherService.queryTeacher(newParam);
    }

    //查询教师人数（默认为所有教师人数）
    @ResponseBody
    public JSONObject queryTeacherCount(String keyword){
        return this.teacherService.queryTeacherCount(keyword);
    }

    //添加教师
    @ResponseBody
    public JSONObject addTeacher(String tNo, String tName)
    {
        if (tName.isEmpty()||tName.trim().isEmpty()||tNo.isEmpty()||tName.trim().isEmpty())
        {
            return new JSONObject().set("status",Constants.NULL_PARAM_ERROR);
        }
        else return this.teacherService.addTeacher(tNo,tName);

    }

    //更新教师
    @ResponseBody
    public JSONObject updateTeacher(String tNo, Map<String, Object> param){
        if (tNo.isEmpty()||tNo.trim().isEmpty())
        {
            return new JSONObject().set("status",Constants.NULL_TEACHER);
        }
        else {
            Map<String,Object> newParam=new HashMap<String,Object>();
            newParam.put("t_no",tNo);
            if (param.get("t_name")!=null){
                newParam.put("t_name",param.get("t_name"));
            }

            if (param.get("t_sex")!=null){
                newParam.put("t_sex",param.get("t_sex"));
            }

            if (param.get("t_depart_fk")!=null){
                newParam.put("t_depart_fk",param.get("t_depart_fk"));
            }

            if (param.get("t_course_fk")!=null){
                newParam.put("t_course_fk",param.get("t_course_fk"));
            }

            if (param.get("t_role_fk")!=null){
                newParam.put("t_role_fk",param.get("t_role_fk"));
            }
            return this.teacherService.queryTeacher(newParam);
        }
    }

    //删除教师
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
    @ResponseBody
    public JSONObject findTeacherByClazz(String cNo){
        if (cNo.isEmpty()||cNo.trim().isEmpty()){
            return new JSONObject().set("status",Constants.NULL_PARAM_ERROR);
        }
        else return this.teacherService.findTeacherByClazz(cNo);
    }

    //根据课程找老师
    @ResponseBody
    public JSONObject findTeacherByCourse(String coNo)
    {
        if (coNo.isEmpty()||coNo.trim().isEmpty()){
            return new JSONObject().set("status",Constants.NULL_PARAM_ERROR);
        }
        else return this.teacherService.findTeacherByCourse(coNo);
    }

    //根据学院找老师
    @ResponseBody
    public JSONObject findTeacherByDepart(String dNo)
    {
        if (dNo.isEmpty()||dNo.trim().isEmpty()){
            return new JSONObject().set("status",Constants.NULL_PARAM_ERROR);
        }
        else return this.teacherService.findTeacherByDepart(dNo);
    }

    //获得该老师所负责的班级
    @ResponseBody
    public JSONObject getTeacherClazz(String tNo)
    {
        if (tNo.isEmpty()||tNo.trim().isEmpty()){
            return new JSONObject().set("status",Constants.NULL_PARAM_ERROR);
        }
        else return this.teacherService.getTeacherClazz(tNo);
    }

    //获得该老师所负责的课程
    @ResponseBody
    public JSONObject getTeacherCourse(String tNo){
        if (tNo.isEmpty()||tNo.trim().isEmpty()){
            return new JSONObject().set("status",Constants.NULL_PARAM_ERROR);
        }
        else return this.teacherService.getTeacherCourse(tNo);
    }


    //获得该老师所属的学院
    @ResponseBody
    public JSONObject getTeacherDepart(String tNo){
        if (tNo.isEmpty()||tNo.trim().isEmpty()){
            return new JSONObject().set("status",Constants.NULL_PARAM_ERROR);
        }
        else return this.teacherService.getTeacherDepart(tNo);
    }

}
