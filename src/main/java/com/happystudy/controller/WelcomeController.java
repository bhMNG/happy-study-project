package com.happystudy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.happystudy.constants.FinalData;
import com.happystudy.util.XMLReader;

import cn.hutool.json.JSONObject;

@Controller
@RequestMapping("/happy-study")
public class WelcomeController {
	@RequestMapping("/welcome")
	public String index() {
		return "welcome";
	}
	
	@RequestMapping("/welcome/quickQuery")
	public String toQuicklyQuery() {
		return "quicklyQuery";
	}
	
	@RequestMapping("/welcome/applyList")
	public String toApplyListPane() {
		return "applyListPane";
	}
	
	@PostMapping("/getMenu")
	@ResponseBody
	public JSONObject getMenu() {
		XMLReader.initMenuXML();
		JSONObject result = new JSONObject();
		result.set("menu", FinalData.MENU);
		return result;
	}
	
	
}
