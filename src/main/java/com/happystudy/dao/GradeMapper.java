package com.happystudy.dao;

import java.util.List;

import com.happystudy.model.Grade;

public interface GradeMapper {

	//查找某个课程的成绩单
	public List<Grade> findGradeByCourse(String coNo);
	//查询某个学生的成绩单
	public List<Grade> findGradeByStudent(String sNo);
	//查询某个学生某门课程的成绩单 || 查询某门课程某个学生的成绩单
	public Grade findGradeByCS(String coNo, String sNo);
}
