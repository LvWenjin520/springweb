package org.lwj.app.course.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwj.app.course.entity.Course;
import org.lwj.app.course.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

//课程页面的控制器
@Controller
@RequestMapping("course")
public class CourseController {

	
	@Autowired
	@Qualifier("courseService")
	CourseService courseService;
	
	//获取所有的课程
	@GetMapping("courses")
	@ResponseBody
	public Map<String,Object> getAllCourses(
			@RequestParam("pageNo") int pageNo
			){
		
		//课程总数
		int count = courseService.queryCoursesCount();
		
		//总页数
		int totalPage = count / 6;// 计算总页数
		if (count % 6 != 0) {// 不满一页的数据按一页计算
			totalPage++;
		}
		
		// 当页数大于总页数，直接返回，就是没有的时候
		if (pageNo > totalPage) {
			return null;
		}
			
		
		//从0开始取，每次取6条
		int limitPageNo = (pageNo-1)*6;
		
		List<Course> courses = courseService.queryCoursesLimitByPageNo(limitPageNo);
		
		
		Map<String,Object> result = new HashMap<>();
		result.put("courses", courses);
		result.put("pageNo", pageNo);  //当前页码
		result.put("totalPage", totalPage);  //总页数
		
		return result;
	}
	
	
	
	//去到课程详情页面
	@GetMapping("course/{courseName}")
	public String toCoursePage(
			@PathVariable("courseName") String courseName,
			Model model
			)  {
		//在这里渲染页面
		model.addAttribute("name",courseName);
		return "course/course";
	}
	
	//去到写文章页面
	@GetMapping("article/{articleType}")
	public String toWriteArticlePage(
			@PathVariable("articleType") String articleType
			) {
		return "writeArticle";
	}
}
