package com.happystudy.service.impl;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.happystudy.constants.Constants;
import com.happystudy.dao.DepartMapper;
import com.happystudy.model.Course;
import com.happystudy.model.Depart;
import com.happystudy.service.DepartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LJS
 * @data 2020/6/2 10:20
 */
@Service
public class DepartServiceImpl implements DepartService {
    @Autowired
    private DepartMapper departMapper;

    //查询学院(5个参数）
    @Override
    public JSONObject queryDepart(String keyword,String orderby,String asc,Integer pageNo,Integer pageSize) {
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
        List<Map<String, Object>> mapList = departMapper.queryDepart(param);
        //查询总条数
        Integer recCount=departMapper.queryDepartCount(param);
        //总页数
        int pageCount=recCount/pageSize;
        if (recCount%pageCount>0){
            pageCount++;
        }
        json.set("status", Constants.SUCCESS);
        json.set("departArray", JSONUtil.parseArray(mapList));
        json.set("recCount",recCount);
        json.set("pageCount",pageCount);
        return json;
    }

    //查询学院个数（默认为总个数）
    @Override
    public JSONObject queryDepartCount(Map<String,Object> param) {
        JSONObject json=new JSONObject();
        Integer count = departMapper.queryDepartCount(param);
        json.set("count",count);
        json.set("status", Constants.SUCCESS);
        return json;
    }

    //添加学院
    @Override
    public JSONObject addDepart(String dNo, String dName) {
        JSONObject json=new JSONObject();
        Depart existDepart = departMapper.findDepartByNo(dNo);
        if (existDepart!=null){//学院已存在
            json.set("status",Constants.DNO_EXIST);
            return json;
        }else {
            departMapper.addDepart(dNo,dName);
            json.set("status",Constants.SUCCESS);
            return json;
        }
    }

    //修改学院名字
    @Override
    public JSONObject updateDepartByNo(String dNo, Map<String, Object> param) {
        JSONObject json=new JSONObject();
        Depart existDepart = departMapper.findDepartByNo(dNo);
        if (existDepart==null){//学院不存在
            json.set("status",Constants.NULL_DEPART);
            return json;
        }else {
            departMapper.updateDepartByNo(dNo,param);
            json.set("status",Constants.SUCCESS);
            return json;
        }
    }

    //根据学院号删除学院
    @Override
    public JSONObject deleteDepartByNo(String dNo) {
        JSONObject json=new JSONObject();
        Depart existDepart = departMapper.findDepartByNo(dNo);
        if (existDepart==null){//学院不存在
            json.set("status",Constants.NULL_DEPART);
            return json;
        }else {
            departMapper.deleteDepartByNo(dNo);
            json.set("status",Constants.SUCCESS);
            return json;
        }
    }

    //根据学院号精准匹配学院
    @Override
    public JSONObject findDepartByNo(String dNo) {
        JSONObject json=new JSONObject();
        Depart depart = departMapper.findDepartByNo(dNo);
        if (depart!=null){
            json.set("status",Constants.SUCCESS);
            json.set("depart",depart);
            return json;
        }else {
            json.set("status",Constants.NULL_DEPART);
            return json;
        }
    }

    //查找某个学生的学院
    @Override
    public JSONObject getStudentDepart(String sNo) {
        JSONObject json=new JSONObject();
        Depart depart = departMapper.getStudentDepart(sNo);
        if (depart!=null){
            json.set("status",Constants.SUCCESS);
            json.set("depart",depart);
            return json;
        }else {
            json.set("status",Constants.NULL_DEPART);
            return json;
        }
    }

    //查找某个老师的学院
    @Override
    public JSONObject getTeacherDepart(String tNo) {
        JSONObject json=new JSONObject();
        Depart depart = departMapper.getTeacherDepart(tNo);
        if (depart!=null){
            json.set("status",Constants.SUCCESS);
            json.set("depart",depart);
            return json;
        }else {
            json.set("status",Constants.NULL_DEPART);
            return json;
        }
    }

    //查找某个学院的课程
    @Override
    public JSONObject getDepartCourse(String dNo) {
        JSONObject json=new JSONObject();
        Depart existDepart = departMapper.findDepartByNo(dNo);
        if (existDepart==null){//学院不存在
            json.set("status",Constants.NULL_DEPART);
            return json;
        }else {//学院存在
            List<Course> courseList= departMapper.getDepartCourse(dNo);
            if (courseList!=null&&(!courseList.isEmpty())){//课程存在
                json.set("status",Constants.SUCCESS);
                json.set("course",courseList);
                return json;
            }else {//课程不存在
                json.set("status",Constants.NULL_COURSE);
                return json;
            }
        }
    }

    //统计该学院的学生人数
    @Override
    public JSONObject queryDepartAllStu(Map<String, Object> param) {
        JSONObject json=new JSONObject();
        Integer count=departMapper.queryDepartAllStu(param);
        json.set("status",Constants.SUCCESS);
        json.set("count",count);
        return json;
    }

    //统计该学院的课程总数
    @Override
    public JSONObject queryDepartAllCourse(Map<String, Object> param) {
        JSONObject json=new JSONObject();
        Integer count=departMapper.queryDepartAllCourse(param);
        json.set("status",Constants.SUCCESS);
        json.set("count",count);
        return json;
    }

    //统计该学院的老师人数
    @Override
    public JSONObject queryDepartAllTeaCount(Map<String, Object> param) {
        JSONObject json=new JSONObject();
        Integer count=departMapper.queryDepartAllTeaCount(param);
        json.set("status",Constants.SUCCESS);
        json.set("count",count);
        return json;
    }
}
