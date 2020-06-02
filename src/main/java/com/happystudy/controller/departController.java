package com.happystudy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/happy-study/depart")
public class departController {
	
	@RequestMapping("")
	public String index() {
		return "depart_man";
	}
}
