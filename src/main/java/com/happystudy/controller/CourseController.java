package com.happystudy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/happy-study/course")
public class CourseController {

	@RequestMapping("")
	public String index() {
		return "course_man";
	}
}
