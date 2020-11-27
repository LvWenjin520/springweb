package org.lwj.app.school.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("school")
public class SchoolController {
	
	
	//获取school页面
	@GetMapping("school")
	public String toSchoolPage(Model model) {
		//model.addAttribute("name","lwj");
		return "school";
	}
	
	//获取页面信息
	
	
	
	
	
	
}
