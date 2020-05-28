package com.happystudy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
	@RequestMapping("/studentAdmin")
	public String index() {
		return "student_admin";
	}
	
	@RequestMapping("/login")
	public String login() {
		System.out.println("---------------------------------------------进来了");
		return "login";
	}
}
