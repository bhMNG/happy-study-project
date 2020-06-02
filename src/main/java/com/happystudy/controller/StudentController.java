package com.happystudy.controller;

import cn.hutool.json.JSONObject;
import com.happystudy.constants.Constants;
import com.happystudy.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/happy-study/student")
public class StudentController {
    @Autowired
    StudentService studentService;
    
    @RequestMapping("")
	public String index() {
		return "student_man";
	}

    //根据关键字查询学生信息（排序、分页）(5个参数)
    @ResponseBody
    public JSONObject queryStudent(Map<String, Object> queryStudent){
        Map<String,Object> newParam=new HashMap<String,Object>();
        if (queryStudent.get("keyword")==null){
            newParam.put("keyword","s_no");
        } else newParam.put("keyword",queryStudent.get("keyword"));

        if (queryStudent.get("orderBy")==null){
            newParam.put("orderBy","s_no");
        }else newParam.put("orderBy",queryStudent.get("orderBy"));

        if (queryStudent.get("orderWay")==null){
            newParam.put("orderWay","asc");
        }else newParam.put("orderWay",queryStudent.get("orderWay"));

        if (queryStudent.get("pageOffset")==null){
            newParam.put("pageOffset","1");
        }else newParam.put("pageOffset",queryStudent.get("pageOffset"));

        if (queryStudent.get("pageSize")==null){
            newParam.put("pageSize","5");
        }else newParam.put("pageSize",queryStudent.get("pageSize"));

        return this.studentService.queryStudent(newParam);
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
    public JSONObject updateStudentByNo(String sNo, Map<String, Object> param)
    {
        if (sNo.isEmpty()||sNo.trim().isEmpty()||param.isEmpty())
        {
                return new JSONObject().set("status",Constants.NULL_PARAM_ERROR);
        }
        return this.studentService.updateStudentByNo(sNo,param);
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

    //学生选课
    @ResponseBody
    public JSONObject courseSelective(String sNo, String cNo){
        if (sNo.isEmpty()||cNo.isEmpty()||cNo.trim().isEmpty()||sNo.trim().isEmpty())
        {
            return new JSONObject().set("status",Constants.NULL_PARAM_ERROR);
        }
        else {
            return this.studentService.courseSelective(sNo,cNo);
        }
    }

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
