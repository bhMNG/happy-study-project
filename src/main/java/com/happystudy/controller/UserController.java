package com.happystudy.controller;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.happystudy.util.camelToUnderline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.happystudy.constants.Constants;
import com.happystudy.model.UserInfo;
import com.happystudy.service.UserService;

import cn.hutool.json.JSONObject;

@Controller
@RequestMapping("/happy-study/user")
public class UserController {
	@Autowired
    UserService userService;
	
	@RequestMapping("")
	public String index() {
		return "user_man";
	}
	
	@RequestMapping("propertyMan")
	public String gotoPropMan() {
		return "property_man";
	}
	
	//修改密码
    @PostMapping("/updateUser")
    @ResponseBody
    public JSONObject updateUser(String username, String oldPassword, String newPassword) {
        if (username.isEmpty()||username.trim().isEmpty()){
            return new JSONObject().set("status",Constants.NULL_USER);
        }
        else {
            return userService.updateUser(username,oldPassword,newPassword);
        }
    }

    //注册用户
    @PostMapping("/registUser")
    @ResponseBody
    public JSONObject registUser(String username, String password) {
        if (username.isEmpty()||username.trim().isEmpty()||password.isEmpty()||password.trim().isEmpty()){
            return new JSONObject().set("status",Constants.NULL_USER);
        }
        else {
            return this.userService.registUser(username,password);
        }
    }

    //绑定手机
    @PostMapping("/bindPhone")
    @ResponseBody
    public JSONObject bindPhone(String username, String phonenum) {
        if (username.isEmpty()||username.trim().isEmpty()){
            return new JSONObject().set("status",Constants.NULL_USER);
        }
        else
        {
            return this.userService.bindPhone(username,phonenum);
        }
    }

    //注销
    @PostMapping("/loginOut")
    @ResponseBody
    public void loginOut(HttpServletRequest httpServletRequest)
    {
        httpServletRequest.getSession().invalidate();
    }

    //修改用户个人信息
    @PostMapping("/updateUserInfo")
    @ResponseBody
    public JSONObject updateUserInfo(Map<String, Object> userinfo)
    {
        if (userinfo.get("i_name")==null) return new JSONObject().set("status",Constants.NULL_USER);
        else {
            Map<String,Object> param=new HashMap<String,Object>();
            Field[] fields=UserInfo.class.getDeclaredFields();//反射获取属性名
            String[] fieldsname=new String[fields.length];
            for (int i=0;i<fields.length;i++)
            {
                fieldsname[i]=camelToUnderline.camelToUnderline(fields[i].getName());
                if (userinfo.get(fieldsname[i])!=null)
                {
                    param.put(fieldsname[i],userinfo.get(fieldsname[i]));
                }
            }
            return this.userService.updateUserInfo(param);
        }
    }

    //查询已存在用户(5个参数)
    @PostMapping("/queryUser")
    @ResponseBody
    public JSONObject queryUser(String keyword, String orderBy, String orderWay, Integer pageNo, Integer pageSize){
        Map<String,Object> newParam=new HashMap<String,Object>();
        if (keyword==null){
            keyword = "u_username";
        }

        if (orderBy==null){
            newParam.put("orderBy","u_username");
            orderBy = "u_username";
        }

        if (orderWay==null){
            orderWay="asc";
        }

        if (pageNo==null){
            pageNo = 1;
        }

        if (pageSize==null){
            pageSize = 5;
        }

        return this.userService.queryUser(keyword, orderBy, orderWay, pageNo, pageSize);
    }
    //查询用户个人信息
    @PostMapping("/queryUserInfo")
    @ResponseBody
    public JSONObject queryUserInfo(String username){
        if (username.trim().isEmpty() || username.isEmpty()){
            return new JSONObject().set("status",Constants.NULL_USER);
        }
        else return this.userService.queryUserInfo(username);
    }

    //查询用户角色名称
    @PostMapping("/queryUserRole")
    @ResponseBody
    public JSONObject queryUserRole(String username){
        if (username.trim().isEmpty() || username.isEmpty()){
            return new JSONObject().set("status",Constants.NULL_USER);
        }
        else return this.userService.queryUserRole(username);
    }

    //删除用户
    @PostMapping("/delUserByName")
    @ResponseBody
    public JSONObject delUserByName(String username){
        if (username.trim().isEmpty() || username.isEmpty()){
            return new JSONObject().set("status",Constants.NULL_USER);
        }
        else return this.userService.delUserByName(username);
    }
}
