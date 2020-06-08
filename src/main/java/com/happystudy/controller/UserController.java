package com.happystudy.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.happystudy.constants.Constants;
import com.happystudy.model.UserInfo;
import com.happystudy.service.impl.UserServiceImpl;

import cn.hutool.json.JSONObject;

@Controller
@RequestMapping("/happy-study/user")
public class UserController {
	@Autowired
    UserServiceImpl userService;
	
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
        if (username==null||oldPassword==null||newPassword==null||username.trim().isEmpty()||oldPassword.trim().isEmpty()||newPassword.trim().isEmpty()){
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
        if (username==null||phonenum==null||phonenum.trim().isEmpty()||username.trim().isEmpty()){
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
        httpServletRequest.getSession().removeAttribute(Constants.CURRENT_USER_KEY);
        httpServletRequest.getSession().invalidate();
    }

    //修改用户个人信息
    @PostMapping("/updateUserInfo")
    @ResponseBody
    public JSONObject updateUserInfo(String username, String key, String value)
    {
        if (username==null || username.equals("")) return new JSONObject().set("status",Constants.NULL_USER);
        if (key == null || key.equals("")) return new JSONObject().set("status", Constants.NULL_PARAM_ERROR);
        else {
            return this.userService.updateUserInfo(username, key, value);
        }
    }

    //查询已存在用户(5个参数)
    @PostMapping("/queryUser")
    @ResponseBody
    public JSONObject queryUser(String keyword, String orderBy, String orderWay, Integer pageNo, Integer pageSize){
        Map<String,Object> newParam=new HashMap<String,Object>();
        if (keyword==null){
            keyword = "";
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
        if (username==null||username.trim().isEmpty()){
            return new JSONObject().set("status",Constants.NULL_USER);
        }
        else return this.userService.queryUserInfo(username);
    }

    //查询用户角色名称
    @PostMapping("/queryUserRole")
    @ResponseBody
    public JSONObject queryUserRole(String username){
        if (username==null||username.trim().isEmpty()){
            return new JSONObject().set("status",Constants.NULL_USER);
        }
        else return this.userService.queryUserRole(username);
    }

    //删除用户
    @PostMapping("/delUserByName")
    @ResponseBody
    public JSONObject delUserByName(String username){
        if (username==null||username.trim().isEmpty() ){
            return new JSONObject().set("status",Constants.NULL_USER);
        }
        else return this.userService.delUserByName(username);
    }
  //获取教师选课
//  @PostMapping("/changeTeacherClass")
//  @ResponseBody
//  public JSONObject changeTeacherClass(String tNo, String coNo){
//      if (this.userService.validateTeacherCourse(tNo).get("status")==0) {
//          if (this.userService.validateCourseStatus(coNo).get("status")==0){
//              return  this.userService.doTeacherChooseCourse(tNo,coNo);
//          }
//          else return this.userService.validateCourseStatus(coNo).get("status");
//      }
//      else return this.userService.validateTeacherCourse(tNo).get("status");
//  }
    
    //查找用户的课程
    @PostMapping("/queryUserCourse")
    @ResponseBody
    public JSONObject queryUserCourse(String username, String orderBy, String orderWay) {
    	if (username==null || username==""){
            return new JSONObject().set("status", Constants.NULL_PARAM_ERROR);
        }

        if (orderBy==null || orderBy==""){
            orderBy = "co_no";
        }

        if (orderWay==null || orderWay==""){
            orderWay="asc";
        }

        
        return this.userService.queryUserCourse(username, orderBy, orderWay);
    }
}
