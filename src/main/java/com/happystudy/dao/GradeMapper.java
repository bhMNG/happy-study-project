package com.happystudy.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.happystudy.model.Grade;

@Mapper
public interface GradeMapper {

	//查找某个课程的成绩单
	public List<Grade> findGradeByCourse(String coNo);
	//查询某个学生的成绩单
	public List<Grade> findGradeByStudent(String sNo);
	//查询某个学生某门课程的成绩单 
	public Grade findGradeByCS(String coNo, String sNo);
	
	//根据旧外键设置新外键(删除外键对象的时候用)
	public void setGradeFk(String FKName, String oldFk, String newFk);
	
	//查询成绩
	public List<Map<String,Object>> queryGrade(Map<String,Object> param);
	
	//设置录入该学生的成绩
	public void setStudentScore(String sNo, String coNo, String score);
	//更新该学生的成绩
	public void updateStudentScore(String sNo, String coNo, String score);
	
	//查找没有成绩但是选了课程的学生
	public List<Map<String, Object>> queryNullGradeButHasCourseRow(Map<String,Object> param);
}
