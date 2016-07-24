package backup;

public class CourseNode {
	
	public String courseName;
	public Course course;
	public double f_n;
	public double g_n;
	public double h_n;
	public String preNode;
	
	public CourseNode(String courseName, Course course)
	{
		this.courseName = courseName;
		this.course = course;
		this.f_n = 0.0;
		this.g_n = 0.0;
		this.h_n = 0.0;
		this.preNode = null;
	}
	
	public CourseNode(CourseNode courseNode)
	{
		this.courseName = courseNode.courseName;
		this.course = courseNode.course;
		this.f_n = courseNode.f_n;
		this.g_n = courseNode.g_n;
		this.h_n = courseNode.h_n;
		this.preNode = courseNode.preNode;
	}
	
	public CourseNode()
	{
		this.courseName = null;
		this.course = null;
		this.f_n = 0.0;
		this.g_n = 0.0;
		this.h_n = 0.0;
		this.preNode = null;
	}
}
