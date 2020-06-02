package com.happystudy.controller;

import com.happystudy.constants.Constants;
import com.happystudy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.HashMap;
import java.util.Map;

import cn.hutool.json.JSONObject;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/login")
public class LoginController {
	
	@RequestMapping("")
	public String index() {
		return "login";
	}
	@Autowired
	UserService userService;

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
