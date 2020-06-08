package com.happystudy.service;

import cn.hutool.json.JSONObject;
import com.happystudy.model.User;
import com.happystudy.model.UserInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.happystudy.dao.UserMapper;

import java.util.Map;


public interface UserService {
	//登录方法
		public JSONObject login(String username, String password);
		//修改密码
		public JSONObject updateUser(String username,String oldPassword,String newPassword);
		//注册用户
		public JSONObject registUser(String username,String password);
	    //绑定手机
	    public JSONObject bindPhone(String username, String phonenum);
	    //修改用户个人信息
		public JSONObject updateUserInfo(String username, String key, String value);
		//查询已存在用户（5个参数）
		public JSONObject queryUser(String keyword,String orderby,String asc,Integer pageNo,Integer pageSize);
		//查询用户个人信息
		public JSONObject queryUserInfo(String username);
		//查询用户角色名称
		public JSONObject queryUserRole(String username);
		//查询用户权限职责信息
		public JSONObject queryUserProp(String username);
		//删除用户
		public JSONObject delUserByName(String username);
	    //教师能否选课
		public JSONObject validateTeacherCourse(String tNo);
		//课程状态
	    public JSONObject validateCourseStatus(String cNo);
	    //执行教师选课
	    public JSONObject doTeacherChooseCourse(String tNo, String cNo);
	
	//查询学生用户成绩
    public JSONObject queryUserCourseGrade(String username,String orderby,String asc,Integer pageNo,Integer pageSize);
    
    //查询用户的课程
    public JSONObject queryUserCourse(String username, String orderBy, String orderWay);
}
