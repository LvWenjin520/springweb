package org.lwj.app.course.dao;

import java.util.List;

import org.lwj.app.course.entity.Course;
import org.springframework.stereotype.Repository;

@Repository("courseDao")
public interface CourseDao {
	
	List<Course> queryCoursesLimitByPageNo(int pageNo);
	
	//查询所有的文章的数量
	int queryCoursesCount();
	
	//根据课程名查询课程id
	int queryCourseIdByCourseName(String courseName);
}
