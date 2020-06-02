package com.happystudy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/happy-study/student")
public class StudentController {

	@RequestMapping("")
	public String index() {
		return "student_man";
	}
}
