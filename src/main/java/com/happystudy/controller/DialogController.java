package com.happystudy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.happystudy.constants.Constants;
import com.happystudy.service.DialogService;

import cn.hutool.json.JSONObject;

@Controller
@RequestMapping("/dialog")
public class DialogController {
	@Autowired
	DialogService dialogService;
	
	@GetMapping("/queryDialog")
	@ResponseBody
	public JSONObject queryDialog(String keyword) {
		if (keyword == null) {
			keyword = "";
		}
		return dialogService.queryDialog(keyword);
	}
	
	@PostMapping("/deleteDialog")
	@ResponseBody
	public JSONObject deleteDialog(Integer id) {
		if (id==null) {
			return new JSONObject().set("status", Constants.NULL_PARAM_ERROR);
		}
		return dialogService.deleteDialog(id);
	}
}
