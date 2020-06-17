package com.happystudy.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.happystudy.model.Course;
import com.happystudy.model.Student;

@Mapper
public interface CourseMapper {

	//查询课程（5个参数）
		public List<Map<String, Object>> queryCourse(Map<String, Object> param);
		//查询课程数（默认为所有课程）
		public Integer queryCourseCount(Map<String, Object> param);
		//添加课程
		public void addCourse(@Param("coNo") String coNo,@Param("coName") String coName);
		//修改课程名
		public void updateCourseByNo(@Param("coNo") String coNo,@Param("coName") String coName);
		//根据课程号删除课程
		public void deleteCourseByNo(String coNo);
		//根据课程号精确匹配课程
		public Course findCourseByNo(String coNo);
		//通过老师找课程
		public Course getTeacherCourse(String tNo);
		//通过学院找课程
		public List<Course> findCourseByDepart(String dNo);
		//查询报了该课程的所有学生
		public List<Student> queryCourseAllStu(String coNo);
		//查询该课程的学生人数
		public Integer queryCourseStudentCount(String coNo);
		
		//根据旧外键设置新外键(删除外键对象的时候用)
		public void setCourseFk(String FKName, String oldFk, String newFk);
		
		/**
		 * 根据旧外键设置新外键（课程表与学生表的中间表的外键）
		 * @param FKName
		 * @param oldFk
		 * @param newFk
		 */
		public void setCourseStuListFk(String FKName, String oldFk, String newFk);
		/**
		 * 当一门课程被删除之后，应当销毁起学生名单表（也就是和学生表的中间表）
		 * @param cslCourseFk 对应co_no
		 */
		public void deleteCourseStuListDataRow(String cslCourseFk);
		
		/**
		 * 查询该学生是否还在这个课程里面
		 */
		public Student findStudentByCourseAndSno(String cNo, String sNo);
		
		/**
		 * 根据课程（是否有负责老师）的状态码来查询课程
		 * @param keyword 模糊查询关键字
		 * @param orderBy 排序依据
		 * @param orderWay 升序or降序
		 * @param status 课程状态码（0-未有负责老师， 1-正在被申请负责， 2-已有负责老师）
		 * @return
		 */
		public List<Map<String, Object>> queryCourseByStatus(String keyword, String orderBy, String orderWay, 
				Integer offset, Integer pageSize, Integer status);
		/**
		 * 设置课程状态码（0-未有负责老师， 1-正在被申请负责， 2-已有负责老师）
		 * @param co_no
		 * @param status
		 */
		public void setCourseStatus(String co_no, Integer status);
		
		/**
		 * 删除教师申请课程表中的一条字段（当申请成功或者失败，或是课程、教师被删除时调用）
		 */
		public void deleteCourseApplyDataRow(String noType, String noValue);
		/**
		 * 新增一条教师申请课程表中的字段（当教师申请负责课程时调用）
		 */
		public void insertCourseApplyDataRow(String tNo, String coNo);
		/**
		 * 根据查询某个教师正在申请中的课程
		 */
		public List<Map<String, Object>> queryApplyingCourseByTNo(String tNo);
		/**
		 * 学生选课
		 */
		public void chooseCourseBySNo(String sNo, String coNo);
}
