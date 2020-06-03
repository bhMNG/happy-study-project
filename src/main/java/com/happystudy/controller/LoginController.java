package com.happystudy.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.happystudy.constants.Constants;
import com.happystudy.service.UserService;

import cn.hutool.json.JSONObject;

@Controller
@RequestMapping("/login")
public class LoginController {
	@Autowired
	UserService userService;
	
	@RequestMapping("")
	public String index() {
		return "login";
	}
	
	@PostMapping("/validate")
	@ResponseBody
	public JSONObject validate(String username, String password,HttpServletRequest httpServletRequest) {
	    JSONObject jsonObject=this.userService.login(username,password);
	    if (jsonObject.getInt("status")==0){

	        httpServletRequest.getSession().setAttribute(Constants.CURRENT_USER_KEY,jsonObject.get("user"));
	        return new JSONObject().set("status",Constants.SUCCESS);
        }
        else return jsonObject;
	}
}
