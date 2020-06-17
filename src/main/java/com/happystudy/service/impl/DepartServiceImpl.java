package com.happystudy.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.happystudy.constants.Constants;
import com.happystudy.dao.DepartMapper;
import com.happystudy.dao.StudentMapper;
import com.happystudy.dao.TeacherMapper;
import com.happystudy.dao.ClazzMapper;
import com.happystudy.model.Course;
import com.happystudy.model.Depart;
import com.happystudy.service.DepartService;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

/**
 * @author LJS
 * @data 2020/6/2 10:20
 */
@Service
public class DepartServiceImpl implements DepartService {
	@Autowired
    private DepartMapper departMapper;
	@Autowired
	private TeacherMapper teacherMapper;
	@Autowired
	private StudentMapper studentMapper;
	@Autowired
	private ClazzMapper clazzMapper;

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
        //List<Depart> mapList = departMapper.queryDepart(param);
        List<Map<String, Object>> mapList = departMapper.queryDepart(param);
        //学院学生人数、教师人数
        for (Map<String ,Object> map : mapList) {
        	map.put("stuCount", (queryDepartAllStu((String)map.get("d_no"))).get("count"));
        	map.put("teacherCount", (queryDepartAllTeaCount((String)map.get("d_no"))).get("count"));
        }
        //查询总条数
        Integer recCount=departMapper.queryDepartCount(param);
        //总页数
        int pageCount=recCount/pageSize;
        if (recCount%pageSize>0){
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
    public JSONObject updateDepartByNo(String dNo, String dName) {
        JSONObject json=new JSONObject();
        Depart existDepart = departMapper.findDepartByNo(dNo);
        if (existDepart==null){//学院不存在
            json.set("status",Constants.NULL_DEPART);
            return json;
        }else {
            departMapper.updateDepartByNo(dNo,dName);
            json.set("status",Constants.SUCCESS);
            return json;
        }
    }

    //根据学院号删除学院
    @Transactional
    @Override
    public JSONObject deleteDepartByNo(String[] dNos) {
        JSONObject json=new JSONObject();
        try {
        	for (String dNo : dNos) {
        		Depart existDepart = departMapper.findDepartByNo(dNo);
                if (existDepart==null){//学院不存在
                    json.set("status",Constants.NULL_DEPART);
                    return json;
                }else {
                    departMapper.deleteDepartByNo(dNo);
                    teacherMapper.setTeacherFk(Constants.T_DEPART_FK, dNo, null);
                    studentMapper.setStudentFk(Constants.S_DEPART_FK, dNo, null);
                    clazzMapper.setClazzFk(Constants.C_DEPART_FK, dNo, null);
                }
        	}
        	json.set("status",Constants.SUCCESS);
        	return json;
        }catch(Exception e) {
        	e.printStackTrace();
        	return json.set("status", Constants.DB_ERROR);
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
    public JSONObject queryDepartAllStu(String dNo) {
        JSONObject json=new JSONObject();
        if (departMapper.findDepartByNo(dNo)==null){
            return json.set("status",Constants.NULL_DEPART);
        }
        else {
        Integer count=departMapper.queryDepartAllStu(dNo);
        json.set("status",Constants.SUCCESS);
        json.set("count",count);
        return json;
        }
    }

    //统计该学院的课程总数
    @Override
    public JSONObject queryDepartAllCourse(String dNo) {
        JSONObject json=new JSONObject();
        if (departMapper.findDepartByNo(dNo)==null){
            return json.set("status",Constants.NULL_DEPART);
        }
        else {
        Integer count=departMapper.queryDepartAllCourse(dNo);
        json.set("status",Constants.SUCCESS);
        json.set("count",count);
        return json;
        }
    }

    //统计该学院的老师人数
    @Override
    public JSONObject queryDepartAllTeaCount(String dNo) {
        JSONObject json=new JSONObject();
        if (departMapper.findDepartByNo(dNo)==null){
            return json.set("status",Constants.NULL_DEPART);
        }
        else {
        Integer count=departMapper.queryDepartAllTeaCount(dNo);
        json.set("status",Constants.SUCCESS);
        json.set("count",count);
        return json;
        }
    }
}