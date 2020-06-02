package com.happystudy.service.impl;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.happystudy.constants.Constants;
import com.happystudy.dao.CourseMapper;
import com.happystudy.model.Course;
import com.happystudy.model.Student;
import com.happystudy.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LJS
 * @data 2020/6/2 9:48
 */
@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseMapper courseMapper;

    //查询课程
    @Override
    public JSONObject queryCourse(String keyword,String orderby,boolean asc,int pageNo,int pageSize) {
        JSONObject json=new JSONObject();
        Map<String,Object> param=new HashMap<>();
        //计算偏移量
        int offsert=(pageNo-1)*pageSize;
        param.put("keyword",keyword);
        param.put("orderby",orderby);
        param.put("asc",asc);
        param.put("pageNo",offsert);
        param.put("pageSize",pageSize);
        //查询结果并分页
        List<Map<String, Object>> mapList = courseMapper.queryCourse(param);
        //查询总条数
        Integer recCount = courseMapper.queryCourseCount(param);
        //总页数
        int pageCount=recCount/pageSize;
        if (recCount%pageCount>0){
            pageCount++;
        }
        json.set("status", Constants.SUCCESS);
        json.set("courseArray", JSONUtil.parseArray(mapList));
        json.set("recCount",recCount);
        json.set("pageCount",pageCount);
        return json;
    }

    //查询课程数（默认为所有课程）
    @Override
    public JSONObject queryCourseCount(Map<String,Object> param) {
        JSONObject json=new JSONObject();
        Integer count =courseMapper.queryCourseCount(param);
        json.set("count",count);
        json.set("status", Constants.SUCCESS);
        return json;
    }

    //添加课程
    @Override
    public JSONObject addCourse(String coNo, String coName) {
        JSONObject json=new JSONObject();
        Course existCourse = courseMapper.findCourseByNo(coNo);
        if (existCourse!=null){//课程号已存在
            json.set("status",Constants.CONO_EXIST);
            return json;
        }else {
            courseMapper.addCourse(coNo,coName);
            json.set("status",Constants.SUCCESS);
            return json;
        }
    }

    //修改课程名
    @Override
    public JSONObject updateCourseByNo(String coNo,Map<String, Object> param) {
        JSONObject json=new JSONObject();
        Course existCourse = courseMapper.findCourseByNo(coNo);
        if (existCourse!=null){
            courseMapper.updateCourseByNo(coNo,param);
            json.set("status",Constants.SUCCESS);
            return json;
        }else {
            json.set("status",Constants.NULL_COURSE);
            return json;
        }
    }

    //根据课程号删除课程
    @Override
    public JSONObject deleteCourseByNo(String coNo) {
        JSONObject json=new JSONObject();
        Course existCourse = courseMapper.findCourseByNo(coNo);
        if (existCourse==null){//课程不存在
            json.set("status",Constants.NULL_COURSE);
            return json;
        }else {
            courseMapper.deleteCourseByNo(coNo);
            json.set("status",Constants.SUCCESS);
            return json;
        }
    }

    //根据课程号精确匹配课程
    @Override
    public JSONObject findCourseByNo(String coNo) {
        JSONObject json=new JSONObject();
        Course course = courseMapper.findCourseByNo(coNo);
        if (course!=null){
            json.set("status",Constants.SUCCESS);
            json.set("course",course);
            return json;
        }else {
            json.set("status",Constants.NULL_COURSE);
            return json;
        }
    }

    //通过老师找课程
    @Override
    public JSONObject getTeacherCourse(String tNO) {
        JSONObject json=new JSONObject();
        Course course = courseMapper.getTeacherCourse(tNO);
        if (course!=null){
            json.set("status",Constants.SUCCESS);
            json.set("course",course);
            return json;
        }else {
            json.set("status",Constants.NULL_COURSE);
            return json;
        }
    }

    //通过学院找课程
    @Override
    public JSONObject findCourseByDepart(String dNo) {
        JSONObject json=new JSONObject();
        List<Course> courseList = courseMapper.findCourseByDepart(dNo);
        json.set("status",Constants.SUCCESS);
        json.set("courseList",JSONUtil.parseArray(courseList));
        return json;
    }

    //查询报了该课程的所有学生
    @Override
    public JSONObject queryCourseAllStu(String cNo) {
        JSONObject json=new JSONObject();
        Course course = courseMapper.findCourseByNo(cNo);
        if (course==null){
            json.set("status",Constants.NULL_COURSE);
            return json;
        }else {
            List<Student> studentList = courseMapper.queryCourseAllStu(cNo);
            json.set("status",Constants.SUCCESS);
            json.set("studentList",JSONUtil.parseArray(studentList));
            return json;
        }
    }

    //查询该课程的学生人数
    @Override
    public JSONObject queryCourseStudentCount(Map<String, Object> param) {
        JSONObject json=new JSONObject();
        Integer count = courseMapper.queryCourseStudentCount(param);
        json.set("status",Constants.SUCCESS);
        json.set("count",count);
        return json;
    }
}
