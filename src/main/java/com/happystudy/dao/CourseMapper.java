package com.happystudy.dao;

import java.util.List;
import java.util.Map;

import com.happystudy.model.Course;
import com.happystudy.model.Student;

public interface CourseMapper {

	//查询课程（5个参数）
	public List<Map<String, Object>> queryCourse(Map<String, Object> param);
	//查询课程数（默认为所有课程）
	public Integer queryCourseCount(Map<String, Object> param);
	//添加课程
	public void addCourse(String coNo, String coName);
	//修改课程名
	public void updateCourseByNo(String coNo,Map<String, Object> param);
	//根据课程号删除课程
	public void deleteCourseByNo(String coNo);
	//根据课程号精确匹配课程
	public Course findCourseByNo(String coNo);
	//通过老师找课程
	public Course getTeacherCourse(String tNo);
	//通过学院找课程
	public List<Course> findCourseByDepart(String dNo);
	//查询报了该课程的所有学生
	public List<Student> queryCourseAllStu(String cNo);
	//查询该课程的学生人数
	public Integer queryCourseStudentCount(Map<String, Object> param);
}
