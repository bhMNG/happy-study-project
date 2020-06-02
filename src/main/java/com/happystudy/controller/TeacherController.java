package com.happystudy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/happy-study/teacher")
public class TeacherController {

	@RequestMapping("")
	public String index() {
		return "teacher_man";
	}
}
