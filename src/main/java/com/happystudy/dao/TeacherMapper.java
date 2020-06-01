package com.happystudy.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.happystudy.model.Clazz;
import com.happystudy.model.Course;
import com.happystudy.model.Depart;
import com.happystudy.model.Teacher;

@Mapper
public interface TeacherMapper {
	//查询教师（5个参数）
	public Map<String, Object> queryTeacher(String keyword,String orderBy,String orderWay,String offset,String pageSize);
	//查询教师人数（默认为所有教师人数）
	public Integer queryTeacherCount(String keyword);	
	//添加教师
	public void addTeacher(Teacher teacher);	
	//更新教师
	public void updateTeacherByNo(String tNo);	
	//删除教师
	public void deleteTeacherByNo(String tNo);
	//根据班级获取老师
	public Teacher findTeacherByClazz(String cNo);
	//根据课程找老师
	public Teacher findTeacherByCourse(String coNo);
	//根据学院找老师
	public List<Teacher> findTeacherByDepart(String dNo);
	//获得该老师所负责的班级
	public Clazz getTeacherClazz(String tNo);
	//获得该老师所负责的课程
	public Course getTeacherCourse(String tNo);
	//获得该老师所属的学院
	public Depart getTeacherDepart(String tNo);
}
