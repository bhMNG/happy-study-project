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
		public JSONObject registUser(String username,String password, String no);
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
    //查询用户的班级 （老师->负责的班级   学生->所在的班级）
    public JSONObject queryUserClazz(String username);
    
    //修改用户（root）
    public JSONObject updateUserByRoot(String username, String password);
    
    //根据用户名查询我的成绩
    public JSONObject queryMyGrade(String username, String orderBy, String orderWay);
    
    //根据用户名查询教师用户的教师号
    public JSONObject queryUsertNo(String username);
    
    //查询该（教师）用户正在申请中的课程
    public JSONObject queryApplyingCourseByUsername(String username);
    
    //拆线呢学生用户的学生号
    public JSONObject queryUserSNo(String username);
    
    
//  ----权限----
    //创建一个diy角色
    public JSONObject addRole(Integer rProperty, String rName);
    //删除一个div角色
    public JSONObject deleteRole(Integer rProperty);
    //修改一个div角色的角色名
    public JSONObject updateRoleName(Integer rProperty, String rName);
    //查询所有存在的角色
    public JSONObject queryAllRole();
    //查询所有存在的职责行为
    public JSONObject queryAllDuty();
    //查询某个角色的职责行为(有的+没有的)
    public JSONObject queryRoleDuty(Integer rProperty);
    //给某个角色添加一个职责行为
    public JSONObject addRoleDuty(Integer rProperty, String[] pNos);
    //给某个角色减少一个职责行为
    public JSONObject deleteRoleDuty(Integer rProperty, String[] pNos);
    //清空某个角色的所有职责行为
    public JSONObject deleteRoleAllDuty(Integer rProeprty);
    
}
