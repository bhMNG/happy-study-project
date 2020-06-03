package com.happystudy.service.impl;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.happystudy.constants.Constants;
import com.happystudy.dao.StudentMapper;
import com.happystudy.model.*;
import com.happystudy.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LJS
 * @data 2020/6/1 18:59
 */
@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentMapper studentMapper;

    //根据关键字查询学生信息（排序、分页）（5个参数）
    @Override
    public JSONObject queryStudent(String keyword,String orderby,String asc,int pageNo,int pageSize) {
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
        List<Map<String, Object>> mapList = studentMapper.queryStudent(param);
        //查询总条数
        Integer recCount= studentMapper.queryStudentCount(param);
        //总页数
        int pageCount=recCount/pageSize;
        if (recCount%pageCount>0){
            pageCount++;
        }
        json.set("status",Constants.SUCCESS);
        json.set("studentArray", JSONUtil.parseArray(mapList));
        json.set("recCount",recCount);
        json.set("pageCount",pageCount);
        return json;
    }

    //查询学生人数（可以根据条件查询，默认为总人数）
    @Override
    public JSONObject queryStudentCount(Map<String,Object> param) {
        JSONObject json=new JSONObject();
        Integer count = studentMapper.queryStudentCount(param);
        json.set("count",count);
        json.set("status", Constants.SUCCESS);
        return json;
    }

    //添加学生
    @Override
    public JSONObject addStudent(String sNo, String sName) {
        JSONObject json=new JSONObject();
        Student existStudent = studentMapper.findStudentByNo(sNo);
        if (existStudent!=null){//学号已存在
            json.set("status",Constants.SNO_EXIST);
            return json;
        }else {
            studentMapper.addStudent(sNo,sName);
            json.set("status",Constants.SUCCESS);
            return json;
        }
    }

    //根据学号删除学生
    @Override
    public JSONObject deleteStudentByNo(String sNo) {
        JSONObject json=new JSONObject();
        Student existStudent = studentMapper.findStudentByNo(sNo);
        if (existStudent==null){//学号不存在
            json.set("status",Constants.NULL_STU);
            return json;
        }else {
            studentMapper.deleteStudentByNo(sNo);
            json.set("status",Constants.SUCCESS);
            return json;
        }
    }

    //根据学号修改学生
    @Override
    public JSONObject updateStudentByNo(Map<String, Object> param) {
        JSONObject json=new JSONObject();
        studentMapper.updateStudentByNo(param);
        json.set("status",Constants.SUCCESS);
        return json;
    }

    //获取学生所在班级
    @Override
    public JSONObject getStudentClazz(String sNo) {
        JSONObject json=new JSONObject();
        Clazz clazz = studentMapper.getStudentClazz(sNo);
        json.set("status",Constants.SUCCESS);
        json.set("clazz",clazz);
        return json;
    }

    //获取学生成绩
    @Override
    public JSONObject getStudentScore(String sNo) {
        JSONObject json=new JSONObject();
        List<Grade> gradeList = studentMapper.getStudentGrade(sNo);
        json.set("status",Constants.SUCCESS);
        json.set("gradeArray", JSONUtil.parseArray(gradeList));
        return json;
    }

    //学生选课<!!!!!!!!>
    @Override
    public JSONObject courseSelective(String sNo, String cNo) {
        return null;
    }

    //获取学生已选课程
    @Override
    public JSONObject getStudentCourse(String sNo) {
        JSONObject json=new JSONObject();
        List<Course> courseList = studentMapper.getStudentCourse(sNo);
        json.set("status",Constants.SUCCESS);
        json.set("courseArray", JSONUtil.parseArray(courseList));
        return json;
    }

    //获取学生的学院
    @Override
    public JSONObject getStudentDepart(String sNo) {
        JSONObject json=new JSONObject();
        Depart depart = studentMapper.getStudentDepart(sNo);
        json.set("status",Constants.SUCCESS);
        json.set("depart",depart);
        return json;
    }
}
