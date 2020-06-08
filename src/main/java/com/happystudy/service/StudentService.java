package com.happystudy.service;

import java.util.List;
import java.util.Map;

import cn.hutool.json.JSONObject;

/**
 * @author LJS
 * @data 2020/5/31 12:47
 */

public interface StudentService {
	//根据关键字查询学生信息（排序、分页）（5个参数）
    public JSONObject queryStudent(String keyword,String orderby,String asc,Integer pageNo,Integer pageSize);
    //查询学生人数（可以根据条件查询，默认为总人数）
    public JSONObject queryStudentCount(Map<String,Object> param);
    //添加学生
    public JSONObject addStudent(String sNo,String sName, String sSex, String sEnterYear);
    //根据学号删除学生
    public JSONObject deleteStudentByNo(String sNo);
    //根据学号修改学生
    public JSONObject updateStudentByNo(Map<String,Object> param);
    //获取学生所在班级
    public JSONObject getStudentClazz(String sNo);
    //获取学生成绩
    public JSONObject getStudentScore(String sNo);
    //获取学生已选课程
    public JSONObject getStudentCourse(String sNo);
    //获取学生的学院
    public JSONObject getStudentDepart(String sNo);
    
    //批量导入
    public String addStudentBatch(List<JSONObject> stuList);
    
}
