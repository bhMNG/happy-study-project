package com.happystudy.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.happystudy.model.Clazz;
import com.happystudy.model.Course;
import com.happystudy.model.Depart;
import com.happystudy.model.Grade;
import com.happystudy.model.Student;

@Mapper
public interface StudentMapper {

	//根据关键字查询学生信息（排序、分页）(5个参数)
	public List<Map<String, Object>> queryStudent(Map<String, Object> param);
	//查询学生人数（可以根据条件查询，默认为总人数）
	public Integer queryStudentCount(Map<String, Object> param);
	//添加学生
	public void addStudent(String sNo, String sName, String sSex, String sEnterYear);
	//根据学号删除学生
	public void deleteStudentByNo(String sNo);
	//根据学号修改学生
	public void updateStudentByNo(Map<String, Object> param);
	//根据学号查询学生
	public Student findStudentByNo(String sNo);
	//获取学生所在班级
	public Clazz getStudentClazz(String sNo);
	//获取学生成绩
	public List<Grade> getStudentGrade(String sNo);
	//获取学生已选课程
	public List<Course> getStudentCourse(String sNo);
	//获取学生的学院
	public Depart getStudentDepart(String sNo);
	//添加学生（完整信息）
	public void doAddStudent(String sNo, String sName, String sBirthday, String sSex, String sEnterYear, String sClazzFk, String sDepartFk);
}
