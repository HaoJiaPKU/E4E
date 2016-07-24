package bfs;

import java.util.ArrayList;

public class Course {
	
	String courseName;
	float courseHour;
	int projectNum;
	public ArrayList <String> preCourses;

	public Course(){
		this.courseName = new String();
		this.courseHour = 0;
		this.projectNum = 0;
		this.preCourses = new ArrayList <String> ();
	}
	
	@SuppressWarnings("unchecked")
	public Course(Course course){
		this.courseName = course.courseName;
		this.courseHour = course.courseHour;
		this.projectNum = course.projectNum;
		this.preCourses = (ArrayList<String>) course.preCourses.clone();
	}
}
