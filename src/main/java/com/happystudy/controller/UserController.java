package com.happystudy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/happy-study/user")
public class UserController {
	
	@RequestMapping("")
	public String index() {
		return "user_man";
	}
	
	@RequestMapping("propertyMan")
	public String gotoPropMan() {
		return "property_man";
	}
}
