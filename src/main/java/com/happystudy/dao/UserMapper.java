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
	//查询老师用户的负责班级
	public Map<String, Object> queryTeacherUserClazz(String username);
	//查询学生用户的所在班级
	public Map<String, Object> queryStudentUserClazz(String username);
	//插入一条学生信息
	public void insertUserInfo(String username, String key, String value);
	//查询老师用户没有负责的课程
	//public List<Map<String, Object>> queryTeacherUserUnchoosedCourse(String username, String orderBy, String orderWay);
	//查询学生用户没有负责的课程
	//public List<Map<String, Object>> queryStudentUserUnchoosedCourse(String username, String orderBy, String orderWay);
	
	//根据旧外键设置新外键(删除外键对象的时候用)
	public void setUserFk(String FKName, String oldFk, String newFk);
	//根据旧外键设置新外键(删除外键对象的时候用) property表
	@Deprecated
	public void setPropertyFk(String FKName, String oldFk, String newFk);
	//根据旧外键设置新外键(删除外键对象的时候用) userinfo表
	public void setUserinfoFk(String FKName, String oldFk, String newFk);
	
	//查询我的成绩
	//	代码添加或修改
	public List<Map<String,Object>> queryMyGrade(String username, String orderBy, String orderWay);
	
	//根据用户名获取教师用户的教师号
	public String getTeacherNoByUsername(String username);
	//根据用户名获取学生用户的学生号
	public String getStudentNoByUsername(String username);
	
	//添加一行用户详细信息row（创建用户时使用）
	public void addUserInfo(String username);
	//删除一条用户详细信息row（删除用户时使用）
	public void deleteUserInfo(String username);
	
	
	//权限
	/**
	 * 删除t_role_actionlist表中的多条记录（删除自定义role角色的时候用，删除该角色所有职责行为时用）
	 */
	public void deleteRoleActionListDataRow(Integer ralRoleFk);
	/**
	 * 删除t_role_actionlist表中的一条记录（给role角色减少职责行为的时候用）
	 * @param ralRoleFk
	 * @param ralPropFk
	 */
	public void deleteRoleActionListDataOneRow(Integer ralRoleFk, String ralPropFk);
	/**
	 * 添加t_role_actionlist表中的一条记录（给role角色添加职责行为的时候使用）
	 */
	public void addRoleActionListDataRow(Integer ralRoleFk, String ralPropFk);
	/**
	 * 查询某个角色所有的职责行为（已有）
	 */
	public List<Map<String, Object>> queryPropByRole(Integer rProperty);
	/**
	 * 查询该系统中的所有职责行为
	 */
	public List<Map<String, Object>> queryAllPropDuty();
	/**
	 * 查询系统中所拥有的全部角色
	 */
	public List<Map<String, Object>> queryAllRole();
	/**
	 * 新建一个角色
	 */
	public void addRole(Integer rProperty, String rName);
	/**
	 * 删除一个角色
	 */
	public void deleteRole(Integer rProperty);
	/**
	 * 修改一个角色的名称
	 */
	public void updateRoleName(Integer rProperty, String rName);
	/**
	 * 根据角色的权限值 精确查找一个角色
	 */
	public Role findRoleByProperty(Integer rProperty);
	/**
	 * 根据职责行为编号 精确查询一个职责行为
	 */
	public Property findPropertyByPNo(String pNo);
	/**
	 * 查询某个角色的某个职责
	 */
	public Property findRolePropertyByPNo(Integer rProperty, String pNo);
	/**
	 * 根据p_duty获取p_No
	 */
	public String getpNoByDuty(String pDuty);
}
