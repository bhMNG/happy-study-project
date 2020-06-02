package com.happystudy.service;

import cn.hutool.json.JSONObject;
import com.happystudy.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.happystudy.dao.UserMapper;

import java.util.Map;

@Service
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
	public JSONObject updateUserInfo(Map<String,Object> param);
	//查询已存在用户（5个参数）
	public JSONObject queryUser(String keyword,String orderby,String asc,int pageNo,int pageSize);
	//查询用户个人信息
	public JSONObject queryUserInfo(String username);
	//查询用户角色名称
	public JSONObject queryUserRole(String username);
	//查询用户权限职责信息
	public JSONObject queryUserProp(String username);
	//删除用户
	public JSONObject delUserByName(String username);
}
