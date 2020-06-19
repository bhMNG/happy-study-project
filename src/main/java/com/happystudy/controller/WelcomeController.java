package com.happystudy.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.happystudy.constants.Constants;
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
	
	@RequestMapping("/loginOut")
	public String loginOut(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute(Constants.CURRENT_USER_KEY);
		return "login";
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
