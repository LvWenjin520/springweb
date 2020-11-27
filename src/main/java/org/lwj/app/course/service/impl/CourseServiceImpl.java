package org.lwj.app.course.service.impl;

import java.util.List;

import org.lwj.app.course.dao.CourseDao;
import org.lwj.app.course.entity.Course;
import org.lwj.app.course.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
@Service("courseService")
public class CourseServiceImpl implements CourseService{

	@Autowired
	@Qualifier("courseDao")
	CourseDao courseDao;
	
	
	@Override
	public List<Course> queryCoursesLimitByPageNo(int pageNo) {
		
		List<Course> courses = courseDao.queryCoursesLimitByPageNo(pageNo);
		
		return courses;
	}


	@Override
	public int queryCoursesCount() {
		int count = courseDao.queryCoursesCount();
		return count;
	}


	@Override
	public int queryCourseIdByCourseName(String courseName) {
		// TODO Auto-generated method stub
		return courseDao.queryCourseIdByCourseName(courseName);
	}
}
