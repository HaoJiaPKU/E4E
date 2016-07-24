package bfs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;

import database.ConnectionPara;

public class CourseGraph {

	public static Hashtable <String, Course> courses = new Hashtable <String, Course> ();
	public static ArrayList <String> recommendation = new ArrayList <String> ();

	public static String loadCourseGraph(ArrayList<String> courseNames) throws SQLException{
		courses.clear();
		
		try {
			Class.forName(ConnectionPara.DBDRIVER);
			Connection conn = DriverManager.getConnection(ConnectionPara.DBNAME
					+ "?user=" + ConnectionPara.USERNAME
					+ "&password=" + ConnectionPara.PASSWORD
					+ "&useUnicode=true&characterEncoding=utf8");
			Statement stmt = conn.createStatement();
			String sql;
			ResultSet rs;
			for(int i = 0; i < courseNames.size(); i ++){
				sql = "select hour, project_num from course_info where name = '" + courseNames.get(i) + "';";
				rs = stmt.executeQuery(sql);
				if(rs.next()){
					System.out.println(courseNames.get(i));
					Course course = new Course();
					course.courseName = courseNames.get(i);
					course.courseHour = rs.getFloat(1);
					course.projectNum = rs.getInt(2);
					sql = "select * from course_relation where course_name = '" + courseNames.get(i) + "';";
					rs = stmt.executeQuery(sql);
					if(rs.next()){
						rs.previous();
						while(rs.next()){
							course.preCourses.add(rs.getString(2));
						}
					}
					courses.put(new String(courseNames.get(i)), new Course(course));
				}
			}
			conn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
//		for(String str : courses.keySet()){
//			Course course = courses.get(str);
//			System.out.println(str + " " + course.courseHour + " " + course.projectNum + " ");
//			for(int i = 0; i < course.preCourses.size(); i ++)
//				System.out.print(course.preCourses.get(i) + " ");
//			System.out.println();
//		}
		return "course graph complete";
	}
	
	public static void bfs(ArrayList <String> sourceCourses, ArrayList <String> targetCourses, String planType)
	{
		System.out.println("CourseGraph******************");
		for(String str : courses.keySet())
			System.out.println(str);
		System.out.println("CourseGraph******************");
		recommendation.clear();
		if(!targetCourses.isEmpty())
		{
//			for(int i = 0; i < sourceCourses.size(); i ++){
//				System.out.print(sourceCourses.get(i) + " ");
//			}
//			System.out.println("!!!!!!!!!!");
//			for(int i = 0; i < targetCourses.size(); i ++){
//				System.out.print(targetCourses.get(i) + " ");
//			}
//			System.out.println("!!!!!!!!!!");
			
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
				
//				System.out.println(courseNode.courseName);
//				for(int j = 0; j < courseNode.course.preCourses.size(); j ++){
//					System.out.print(courseNode.course.preCourses.get(j));
//				}
//				System.out.println();
//				System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
					
				//记录最佳路径的端点
				CourseNode resultCourseNode = null;
				//算法开始
				while(!open.isEmpty())
				{
					CourseNode courseNodeX = open.poll();
//					System.out.println("bfs start: " + courseNodeX.courseName);
//					for(int preNum = 0; preNum < courseNodeX.course.preCourses.size(); preNum ++){
//						System.out.print(courseNodeX.course.preCourses.get(preNum) + " ");
//					}
//					System.out.println("))))))))))))))))))))))))))))");
					if(courseNodeX.course.preCourses.isEmpty() || matchCourse(courseNodeX.courseName, sourceCourses))
					{
//						System.out.println("break condition: " + courseNodeX.courseName + " " + courseNodeX.course.preCourses.size());
						resultCourseNode = new CourseNode(courseNodeX);
						break;//成功
					}
					else
					{
						//生成先修课程子节点
						for(int j = 0; j < courseNodeX.course.preCourses.size(); j ++)
						{
							if(!courses.containsKey(courseNodeX.course.preCourses.get(j)))
								continue;
							CourseNode preCourseNode = new CourseNode(new String(courseNodeX.course.preCourses.get(j)),
								new Course(courses.get(courseNodeX.course.preCourses.get(j))));
//							if(open.contains(preCourseNode))
//							{
//								System.out.println("contains : " + preCourseNode.courseName);
//							}
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
//								System.out.println("*******************************");
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
//					System.out.println("result " + resultCourseNode.courseName);
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
