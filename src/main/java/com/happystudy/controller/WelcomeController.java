package com.happystudy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.hutool.json.JSONObject;

@Controller
@RequestMapping("happy-study")
public class WelcomeController {
	@RequestMapping("welcome")
	public String index() {
		return "welcome";
	}
	
	@GetMapping("getMenu")
	@ResponseBody
	public JSONObject getMenu() {
		return null;
	}
}
