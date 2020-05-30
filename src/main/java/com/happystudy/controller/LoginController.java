package com.happystudy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.hutool.json.JSONObject;

@Controller
@RequestMapping("/login")
public class LoginController {
	
	@RequestMapping("")
	public String index() {
		return "login";
	}
	
	@PostMapping("/validate")
	@ResponseBody
	public JSONObject validate(String username, String password) {
		
		return null;
	}
}
