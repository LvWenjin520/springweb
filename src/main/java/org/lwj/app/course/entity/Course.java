package org.lwj.app.course.entity;

//课程的实体类
public class Course {
	private int courseId;
	private String courseName;
	private int userId;
	private String userName;
	private String courseInfo;
	
	
	public Course() {
	}
	
	public Course(String courseName, int userId, String courseInfo) {
		this.courseName = courseName;
		this.userId = userId;
		this.courseInfo = courseInfo;
	}


	public Course(String courseName, int userId, String userName, String courseInfo) {
		this.courseName = courseName;
		this.userId = userId;
		this.userName = userName;
		this.courseInfo = courseInfo;
	}

	public Course(int courseId, String courseName, int userId, String userName, String courseInfo) {
		this.courseId = courseId;
		this.courseName = courseName;
		this.userId = userId;
		this.userName = userName;
		this.courseInfo = courseInfo;
	}

	public Course(int courseId, String courseName, int userId, String courseInfo) {
		this.courseId = courseId;
		this.courseName = courseName;
		this.userId = userId;
		this.courseInfo = courseInfo;
	}




	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getCourseId() {
		return courseId;
	}
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getCourseInfo() {
		return courseInfo;
	}
	public void setCourseInfo(String courseInfo) {
		this.courseInfo = courseInfo;
	}
	
	
	
	
}
