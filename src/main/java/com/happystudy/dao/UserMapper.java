package com.happystudy.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.happystudy.model.Property;
import com.happystudy.model.Role;
import com.happystudy.model.User;
import com.happystudy.model.UserInfo;

@Mapper
public interface UserMapper {
	
	//修改密码
		public void updateUser(@Param("username") String username,@Param("password") String password);
		//修改用户个人信息
		public void updateUserInfo(String username, String key, String value);
		//查询已存在用户(5个参数)
		public List<Map<String, Object>> queryUser(Map<String, Object> param);
		//查询用户个人信息
		public UserInfo queryUserInfo(String username);
		//查询用户角色名称
		public Role queryUserRole(String username);
		//查询用户权限职责信息
		public Property queryUserProp(String username);
		//查询用户职权名和权限值
		public Map<String, Object> queryUserPosition (String username);
		//删除用户
		public void delUserByName(String username);
		//通过用户名查询用户信息
		public User findUserByName(String username);
		//查询用户人数
		public Integer queryUserCount(Map<String, Object> param);
		//添加用户
		public void addUser(User user);
		//根据电话查找用户
		public User findUserByPhone(String phonenum);
		//绑定手机
		public void bindPhone(@Param("username") String username,@Param("phonenum") String phonenum);
	    //查询课程状态
	    public Integer getCourseStatus(String coNo);
	    //修改课程状态
	    public void setCourseStatus(@Param("coNo") String coNo,@Param("status") String status);
	    //查询教师是否选课
	    public String getTeacherCourseFk(String tNo);
	    //修改教师课程状态
	    public void setTeacherCourseFk(@Param("tNo") String tNo,@Param("coNo") String coNo);
	
	//查询学生用户的成绩表
	public List<Map<String, Object>> queryUserCourseGrade(Map<String, Object> param);
	//查询学生用户的成绩表条数
	public Integer queryUserCourseCount(String username);
	
	//查询老师用户的负责课程
	public List<Map<String, Object>> queryTeacherUserCourse(String username, String orderBy, String orderWay);
	//查询学生用户的已选课程
	public List<Map<String, Object>> queryStudentUserCourse(String username, String orderBy, String orderWay);
	//插入一条学生信息
	public void insertUserInfo(String username, String key, String value);
}
