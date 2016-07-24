package database;

public class FieldIndex {
	
	//course_info
	public static final int COURSE_INFO_NAME = 1;
	public static final int COURSE_INFO_HOUR = 2;
	public static final int COURSE_INFO_PROJECT_NUM = 3;
	public static final int COURSE_INFO_POPULATION = 4;
	public static final int COURSE_INFO_TEACHER = 5;
	public static final int COURSE_INFO_GOAL = 6;
	public static final int COURSE_INFO_LIMITATION = 7;
	
	//course_project
	public static final int COURSE_PROJECT_COURSE_NAME = 1;
	public static final int COURSE_PROJECT_PROJECT_ID = 2;
	
	//course_relation
	public static final int COURSE_RELATION_COURSE_NAME = 1;
	public static final int COURSE_RELATION_PRE_COURSE_NAME = 2;
	
	//grade
	public static final int GRADE_GRADE = 1;
	public static final int GRADE_NEXT_GRADE = 2;
	
	//job
	public static final int JOB_JOB = 1;
	
	//job_grade_course
	public static final int JOB_GRADE_COURSE_JOB = 1;
	public static final int JOB_GRADE_COURSE_GRADE = 2;
	public static final int JOB_GRADE_COURSE_COURSE_NAME = 3;
	
	//project_info
	public static final int PROJECT_INFO_ID = 1;
	public static final int PROJECT_INFO_GOAL = 2;
	public static final int PROJECT_INFO_OUTLINE = 3;
	public static final int PROJECT_INFO_METHOD = 4;
	public static final int PROJECT_INFO_MEDIA = 5;
	public static final int PROJECT_INFO_MINUTE = 6;
	public static final int PROJECT_INFO_DESCRIPTION = 7;
}
