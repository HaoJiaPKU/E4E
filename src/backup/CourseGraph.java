package backup;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Hashtable;

public class CourseGraph {

	public static Hashtable <String, Course> courses = new Hashtable <String, Course> ();
	public static ArrayList <String> recommendation = new ArrayList <String> ();

	public static String loadCourseGraph(){
		courses.clear();
		
		File file = new File(Path.courseInfoPath);
		if(!file.exists())
			return "File Not Found";
		try {
			FileInputStream fis = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(fis, "utf-8");
			BufferedReader reader = new BufferedReader(isr);
			String line = null;
			while((line = reader.readLine()) != null){
				String [] temp = line.trim().split("	");
				Course course = new Course();
				course.courseName = temp[0];
				course.courseHour = Float.parseFloat(temp[1]);
				course.projectNum = Integer.parseInt(temp[2]);
				if(temp.length > 3 && temp[3].length() > 0){
					String [] preCourses = temp[3].trim().split(" ");
					for(int i = 0; i < preCourses.length; i ++)
						course.preCourses.add(preCourses[i]);
				}
				courses.put(new String(temp[0]), new Course(course));
			}
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		for(String str : courses.keySet()){
//			Course course = courses.get(str);
//			System.out.println(str + " " + course.courseHour + " " + course.projectNum + " ");
//			for(int i = 0; i < course.preCourses.size(); i ++)
//				System.out.println(course.preCourses.get(i) + " ");
//			System.out.println();
//		}
		return "course graph complete";
	}
	
	public static void BFS(ArrayList <String> sourceCourses, ArrayList <String> targetCourses, String planType)
	{
		recommendation.clear();
		if(!targetCourses.isEmpty())
		{
			for(int i = 0; i < targetCourses.size(); i ++)
			{
//				System.out.println(targetCourses.get(i));
				//closedset := the empty set      //已经被估算的节点集合
				//openset := set containing the initial node //将要被估算的节点集合
				CoursePriorityQueue open = new CoursePriorityQueue ();
				CoursePriorityQueue closed = new CoursePriorityQueue();
				
				//g_score[start] := 0                        //g(n)
				//h_score[start] := heuristic_estimate_of_distance(start, goal)    //h(n)
				//f_score[start] := h_score[start]            //f(n)=h(n)+g(n)，由于g(n)=0
				CourseNode courseNode = new CourseNode(new String(targetCourses.get(i)),
						new Course(courses.get(targetCourses.get(i))));
				if(planType.equals("time"))
					courseNode.g_n = courseNode.course.courseHour;
				else if(planType.equals("project"))
					courseNode.g_n = courseNode.course.projectNum;
				courseNode.h_n = 0.0;
				courseNode.f_n = courseNode.h_n;
				open.add(courseNode);
					
				//记录最佳路径的端点
				CourseNode resultCourseNode = new CourseNode();
				//算法开始
				while(!open.isEmpty())
				{
					CourseNode courseNodeX = open.poll();
					if(courseNodeX.course.preCourses.isEmpty() || matchCourse(courseNodeX.courseName, sourceCourses))
					{
						resultCourseNode = new CourseNode(courseNodeX);
						break;//成功
					}
					else
					{
						//生成先修课程子节点
						for(int j = 0; j < courseNodeX.course.preCourses.size(); j ++)
						{
							CourseNode preCourseNode = new CourseNode(new String(courseNodeX.course.preCourses.get(j)),
								new Course(courses.get(courseNodeX.course.preCourses.get(j))));
							if(!open.contains(preCourseNode))
							{
//								if(matchCourse(preCourseNode.courseName, sourceCourses))
//								{
//									preCourseNode.g_n = 0.0;
//									preCourseNode.h_n = 0.0;
//									preCourseNode.f_n = 0.0;
//								}
//								else
								{
									if(planType.equals("time"))
										preCourseNode.g_n = courseNodeX.g_n + preCourseNode.course.courseHour;
									else if(planType.equals("project"))
										preCourseNode.g_n = courseNodeX.g_n + preCourseNode.course.projectNum;
									preCourseNode.h_n = courseNodeX.h_n;
									preCourseNode.f_n = preCourseNode.g_n + preCourseNode.f_n;
								}
								preCourseNode.preNode = new String(courseNodeX.courseName);
								open.add(new CourseNode(preCourseNode));
								
//								System.out.println(preCourseNode.courseName);
//								System.out.println(preCourseNode.g_n);
//								System.out.println(preCourseNode.h_n);
//								System.out.println(preCourseNode.f_n);
//								System.out.println(preCourseNode.preNode);
//								System.out.println();
							}
						}
						closed.add(new CourseNode(courseNodeX));
					}
				}
				
//				System.out.println(resultCourseNode.courseName);
				String temp = new String ();
//				temp.add(new String(targetCourses.get(i)));
				while(resultCourseNode != null)
				{
//					System.out.println(resultCourseNode.courseName);
					temp = temp + new String(resultCourseNode.courseName) + " ";
					resultCourseNode = closed.getCourseNode(resultCourseNode.preNode);
				}
				System.out.println(temp);
				recommendation.add(temp);
			}
		}
	}
	
	public static boolean matchCourse(String courseName, ArrayList <String> sourceCourses)
	{
		for(int i = 0; i < sourceCourses.size(); i ++)
		{
			if(courseName.equals(sourceCourses.get(i)))
			{
				return true;
			}
		}
		return false;
	}
}
