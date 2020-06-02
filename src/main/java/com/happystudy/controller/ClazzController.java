package com.happystudy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/happy-study/clazz")
public class ClazzController {

	@RequestMapping("")
	public String index() {
		return "clazz_man";
	}
}
