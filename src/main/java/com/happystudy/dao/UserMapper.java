package com.happystudy.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import com.happystudy.model.User;

@Mapper
public interface UserMapper {
	
	//修改密码
	public void updateUser(String username, String password);
	//修改用户个人信息
	public void updateUserInfo(Map<String, Object> userinfo);
	//查询已存在用户(5个参数)
	public List<Map<String, Object>> queryUser(Map<String, Object> param);
	//查询用户个人信息
	public User queryUserInfo(String username);	
	//查询用户角色名称
	public User queryUserRole(String username);
	//查询用户权限职责信息
	public User queryUserProp(String username);
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
	public User bindPhone(String username,String phonenum);
}
