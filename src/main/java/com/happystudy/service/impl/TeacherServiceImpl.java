package com.happystudy.service.impl;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.happystudy.constants.Constants;
import com.happystudy.dao.TeacherMapper;
import com.happystudy.model.Clazz;
import com.happystudy.model.Course;
import com.happystudy.model.Depart;
import com.happystudy.model.Teacher;
import com.happystudy.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LJS
 * @data 2020/6/1 8:39
 */
@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    private TeacherMapper teacherMapper;
    
    //查询教师（5个参数）
    @Override
    public JSONObject queryTeacher(String keyword,String orderby,String asc,Integer pageNo,Integer pageSize) {
        JSONObject json=new JSONObject();
        Map<String,Object> param=new HashMap<>();
        //计算偏移量
        int offsert=(pageNo-1)*pageSize;
        param.put("keyword",keyword);
        param.put("orderBy",orderby);
        param.put("orderWay",asc);
        param.put("offset",offsert);
        param.put("pageSize",pageSize);
        //查询结果并分页
        List<Map<String, Object>> mapList = teacherMapper.queryTeacher(param);
        //查询总条数
        Integer recCount = teacherMapper.queryTeacherCount(param);
        //总页数
        int pageCount=recCount/pageSize;
        if (recCount%pageCount>0){
            pageCount++;
        }
        json.set("status",Constants.SUCCESS);
        json.set("teacherArray", JSONUtil.parseArray(mapList));
        json.set("recCount",recCount);
        json.set("pageCount",pageCount);
        return json;
    }

    //查询教师人数（默认所有教师人数）
    @Override
    public JSONObject queryTeacherCount(Map<String,Object> param) {
        JSONObject json=new JSONObject();
        Integer count = teacherMapper.queryTeacherCount(param);
        json.set("status", Constants.SUCCESS);
        json.set("count",count);
        return json;
    }

    //添加教师
    @Override
    public JSONObject addTeacher(String tNo, String tName) {
        JSONObject json=new JSONObject();
        Teacher existTeacher = teacherMapper.findTeacherByNo(tNo);
        if (existTeacher!=null){//教师号已存在
            json.set("status",Constants.TNO_EXIST);
            return json;
        }else {
            Teacher teacher=new Teacher();
            teacher.settNo(tNo);
            teacher.settName(tName);
            teacherMapper.addTeacher(teacher);
            json.set("status",Constants.SUCCESS);
            return json;
        }
    }

    //更新教师
    @Override
    public JSONObject updateTeacher(String tNo, Map<String, Object> param) {
        JSONObject json=new JSONObject();
        Teacher existTeacher = teacherMapper.findTeacherByNo(tNo);
        if (existTeacher!=null){//教师号存在
            teacherMapper.updateTeacherByNo(tNo,param);
            json.set("status",Constants.SUCCESS);
            return json;
        }else {
            json.set("status",Constants.NULL_TEACHER);
            return json;
        }
    }

    //删除教师
    @Override
    public JSONObject deleteClazzByNo(String tNo) {
        JSONObject json=new JSONObject();
        Teacher existTeacher = teacherMapper.findTeacherByNo(tNo);
        if (existTeacher!=null){//教师号存在
            teacherMapper.deleteTeacherByNo(tNo);
            json.set("status",Constants.SUCCESS);
            return json;
        }else {
            json.set("status",Constants.NULL_TEACHER);
            return json;
        }
    }

    //根据班级获取老师
    @Override
    public JSONObject findTeacherByClazz(String cNo) {
        JSONObject json=new JSONObject();
        Teacher teacher = teacherMapper.findTeacherByClazz(cNo);
        if (teacher!=null){
            json.set("status",Constants.SUCCESS);
            json.set("teacher",teacher);
            return json;
        }else {
            json.set("status",Constants.NULL_TEACHER);
            return json;
        }
    }

    //根据课程找老师
    @Override
    public JSONObject findTeacherByCourse(String coNo) {
        JSONObject json=new JSONObject();
        Teacher teacher = teacherMapper.findTeacherByCourse(coNo);
        if (teacher!=null){
            json.set("status",Constants.SUCCESS);
            json.set("teacher",teacher);
            return json;
        }else {
            json.set("status",Constants.NULL_TEACHER);
            return json;
        }
    }

    //根据学院找老师
    @Override
    public JSONObject findTeacherByDepart(String dNo) {
        JSONObject json=new JSONObject();
        List<Teacher> teacherList = teacherMapper.findTeacherByDepart(dNo);
        if (teacherList.size()>0){
            json.set("status",Constants.SUCCESS);
            json.set("teacherList",JSONUtil.parseArray(teacherList));
            return json;
        }else {
            json.set("status",Constants.NULL_TEACHER);
            return json;
        }
    }

    //获得该老师所负责的班级
    @Override
    public JSONObject getTeacherClazz(String tNo) {
        JSONObject json=new JSONObject();
        Clazz clazz = teacherMapper.getTeacherClazz(tNo);
        if (clazz!=null){
            json.set("status",Constants.SUCCESS);
            json.set("clazz",clazz);
            return json;
        }else {
            json.set("status",Constants.NULL_CLAZZ);
            return json;
        }
    }

    //获取该老师所负责的课程
    @Override
    public JSONObject getTeacherCourse(String tNo) {
        JSONObject json=new JSONObject();
        Course course = teacherMapper.getTeacherCourse(tNo);
        if (course!=null){
            json.set("status",Constants.SUCCESS);
            json.set("course",course);
            return json;
        }else {
            json.set("status",Constants.NULL_COURSE);
            return json;
        }
    }

    //获得该老师所属的学院
    @Override
    public JSONObject getTeacherDepart(String tNO) {
        JSONObject json=new JSONObject();
        Depart depart = teacherMapper.getTeacherDepart(tNO);
        if (depart!=null){
            json.set("status",Constants.SUCCESS);
            json.set("depart",depart);
            return json;
        }else {
            json.set("status",Constants.NULL_DEPART);
            return json;
        }
    }
}
