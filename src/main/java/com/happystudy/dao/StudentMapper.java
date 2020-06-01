package com.happystudy.dao;

import java.util.Map;

import com.happystudy.model.Student;

public interface StudentMapper {

	//根据关键字查询学生信息（排序、分页）(5个参数)
	public Student queryStudent(Map<String, Object> param);
	//查询学生人数（可以根据条件查询，默认为总人数）
	public Student queryStudentCount(String keyword);
	//添加学生
	public Student addStudent(String sNo, String sName);
	//根据学号删除学生
	public Student deleteStudentByNo(String sNo);
	//根据学号修改学生
	public Student updateStudentByNo(String sNo, Map<String, Object> param);
	//获取学生所在班级
	public Student getStudentClazz(String sNo);
	//获取学生成绩
	public Student getStudentScore(String sNo);
	//学生选课
	public Student courseSelective(String sNo, String cNo);
	//获取学生已选课程
	public Student getStudentCourse(String sNo);
	//获取学生的学院
	public Student getStudentDepart(String sNo);
}
