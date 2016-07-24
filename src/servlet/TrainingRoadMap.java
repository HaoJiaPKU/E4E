package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import bfs.CourseGraph;
import database.ConnectionPara;

/**
 * Servlet implementation class DownloadsServlet
 */
public class TrainingRoadMap extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TrainingRoadMap() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}

	private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String sourceGrade = request.getParameter("sourceGrade");
		String sourceJob = request.getParameter("sourceJob");
		String targetGrade = request.getParameter("targetGrade");
		String targetJob = request.getParameter("targetJob");
		String planType = request.getParameter("planType");

		printInfo(sourceGrade + " " + sourceJob + " " + targetGrade + " " + targetJob);
		String ret = new String();
		try {
			courseGraphItems.clear();
			ret = buildRoadMap(sourceGrade, sourceJob, targetGrade, targetJob, planType);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.setCharacterEncoding("UTF-8");		
		try {
			response.getWriter().print(courseGraphItems.toJson(ret));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private String buildRoadMap(String sourceGrade, String sourceJob, String targetGrade
			, String targetJob, String planType) throws SQLException{
		
		ArrayList<String> grade = new ArrayList<String>();
		ArrayList<String> courseNames = new ArrayList<String>();
		ArrayList<ArrayList<String>> gradeCourse = new ArrayList<ArrayList<String>>();
		HashMap <String, ArrayList<String>> courseRelation = new HashMap <String, ArrayList<String>> ();
		
		try {
			Class.forName(ConnectionPara.DBDRIVER);
			Connection conn = DriverManager.getConnection(ConnectionPara.DBNAME
					+ "?user=" + ConnectionPara.USERNAME
					+ "&password=" + ConnectionPara.PASSWORD
					+ "&useUnicode=true&characterEncoding=utf8");
			Statement stmt = conn.createStatement();
			String sql;
			ResultSet rs;

			//load grade
			if(!sourceJob.equals(targetJob)){
				grade.add(sourceGrade);
				courseGraphItems.grade.add(sourceGrade + " " + sourceJob);
			}
			grade.add(sourceGrade);
			courseGraphItems.grade.add(sourceGrade + " " + targetJob);
			String curGrade = sourceGrade;
			while(curGrade != null && !curGrade.equals(targetGrade)){
				sql = "select next_grade from grade where grade = '" + curGrade + "';";
				rs = stmt.executeQuery(sql);
				rs.next();
				curGrade = rs.getString(1);
				grade.add(curGrade);
				courseGraphItems.grade.add(curGrade + " " + targetJob);
			}
//			for(int i = 0; i < grade.size(); i ++)
//				System.out.println(grade.get(i));
//			System.out.println("**********************");
			
			//load gradeCourse
			int jobEqual = 0;
//			System.out.println(sourceJob +  " "  + sourceGrade);
			if(!sourceJob.equals(targetJob)){
				sql = "select course_name from job_grade_course where job = '" + sourceJob + "' and grade = '" + sourceGrade + "';";
				rs = stmt.executeQuery(sql);
				ArrayList<String> temp = new ArrayList<String>();
				while(rs.next()){
//					System.out.println("next");
					if(rs.getString(1).length() > 0 && !courseNames.contains(rs.getString(1))){
						temp.add(rs.getString(1));
						courseNames.add(rs.getString(1));
					}
				}
				gradeCourse.add((ArrayList<String>) temp.clone());
				courseGraphItems.gradeCourse.add((ArrayList<String>) temp.clone());
				jobEqual = 1;
			}
			for(int i = jobEqual; i < grade.size(); i ++){
				sql = "select course_name from job_grade_course where job = '" + targetJob + "' and grade = '" + grade.get(i) + "';";
				rs = stmt.executeQuery(sql);
				ArrayList<String> temp = new ArrayList<String>();
				while(rs.next()){
					if(rs.getString(1).length() > 0 && !courseNames.contains(rs.getString(1))){
						temp.add(rs.getString(1));
						courseNames.add(rs.getString(1));
					}
				}
				gradeCourse.add((ArrayList<String>) temp.clone());
				courseGraphItems.gradeCourse.add((ArrayList<String>) temp.clone());
			}
			
//			for(int i = 0; i < grade.size(); i ++){
//				ArrayList<String> temp = gradeCourse.get(i);
//				for(int j = 0; j < temp.size(); j ++){
//					System.out.print(temp.get(j) + " ");
//				}
//				System.out.println();
//			}
//			System.out.println("**********************");
			
			//load courseRelation
			for(int i = 1; i < gradeCourse.size(); i ++){
				ArrayList<String> tempGradeCourse = gradeCourse.get(i);
				for(int j = 0; j < tempGradeCourse.size(); j ++){
					ArrayList<String> temp = new ArrayList<String>();
					sql = "select pre_course_name from course_relation where course_name = '" + tempGradeCourse.get(j) + "';";
					rs = stmt.executeQuery(sql);
					while(rs.next()){
						if(rs.getString(1).length() > 0){
							temp.add(rs.getString(1));
						}
					}
					if(temp.size() > 0){
						courseRelation.put(new String(tempGradeCourse.get(j)), (ArrayList<String>) temp.clone());
					}
				}
			}
			
//			for(String str : courseRelation.keySet()){
//				ArrayList<String> temp = courseRelation.get(str);
//				System.out.print(str + " : ");
//				for(int j = 0; j < temp.size(); j ++){
//					System.out.print(temp.get(j) + " ");
//				}
//				System.out.println();
//			}
			
			conn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		HashMap <String, Coordinate> courseLoc = new HashMap <String, Coordinate>();
		int XStart = 20, YStart = 20, XInc = 200, YInc = 20, fontSize = 15;
		ArrayList <String> sourceCourses = (ArrayList<String>) gradeCourse.get(0).clone();
		ArrayList <String> targetCourses = new ArrayList <String> ();
		for(int i = gradeCourse.size() - 1; i >= 0; i --){
			if(gradeCourse.get(i).size() > 0){
				targetCourses = (ArrayList<String>) gradeCourse.get(i).clone();
				break;
			}
		}

		System.out.println("source");
		for(int i = 0; i < sourceCourses.size(); i ++){
			System.out.print(sourceCourses.get(i) + " ");
		}
		System.out.println();
		System.out.println("target");
		for(int i = 0; i < targetCourses.size(); i ++){
			System.out.print(targetCourses.get(i) + " ");
		}
		System.out.println();
		System.out.println("this is training road function");
		
		CourseGraph.loadCourseGraph(courseNames);
		CourseGraph.bfs(sourceCourses, targetCourses, planType);
		
		String ret = "var jg = new jsGraphics(\"roadMap\");"
				+ "jg.setFont(\"Georgia\", \"" + fontSize + "px\", Font.Bold);"
				+ "jg.setStroke(1);";
		int x = XStart, y = YStart;
		for(int i = 0; i < grade.size(); i ++){
			ArrayList<String> courses = gradeCourse.get(i);
			y = YStart;
			boolean XChange = false;
			for(int j = 0; j < courses.size(); j ++){
				//去除重复的课程
				if(courseLoc.containsKey(courses.get(j)))
					continue;
				XChange = true;
				ret += "jg.setColor(\"#000000\");";
				ret += "jg.drawStringRect(\"" + courses.get(j) + "\", " + x + ", " + y + ");";
				if(courseRelation.containsKey(courses.get(j))){
					ArrayList<String> preCourses = courseRelation.get(courses.get(j));
					for(int k = 0; k < preCourses.size(); k ++){
						if(courseLoc.containsKey(preCourses.get(k))){
							Coordinate tempCoordinate = courseLoc.get(preCourses.get(k));
							ArrayList<String> coursePairItem = new ArrayList<String>();
							coursePairItem.add("1");
							ret += "jg.setColor(\"#000000\");";
							for(int l = 0; l < CourseGraph.recommendation.size(); l ++){
								if(CourseGraph.recommendation.get(l).contains(preCourses.get(k) + " " + courses.get(j))){
									ret += "jg.setColor(\"#ff0000\");";
									coursePairItem.set(0, "5");
									break;
								}
							}
							coursePairItem.add(preCourses.get(k));
							coursePairItem.add(courses.get(j));
							courseGraphItems.coursePair.add(coursePairItem);
							ret += "jg.drawLine(" + tempCoordinate.x + ", " + tempCoordinate.y + ", " + x + ", " + (y + 9)+ ");";
						}
					}
				}
				courseLoc.put(courses.get(j), new Coordinate(x + courses.get(j).length() * fontSize, y + 9));
				y = y + YInc;
			}
			if(XChange)
				x = x + XInc;
		}
		ret += "jg.paint();";
		
		return ret;
	}
	
	private void printInfo(String str){
		System.out.println("TrainingRoad INFO: " + str);
	}
}

class Coordinate{
	public int x;
	public int y;
	
	public Coordinate(int x, int y){
		this.x = x;
		this.y = y;
	}
}

class courseGraphItems{
	public static ArrayList<String> grade = new ArrayList<String>();
	public static ArrayList<ArrayList<String>> gradeCourse = new ArrayList<ArrayList<String>>();
	public static ArrayList<ArrayList<String>> coursePair = new ArrayList<ArrayList<String>>();
	
	public static void clear(){
		grade.clear();
		gradeCourse.clear();
		coursePair.clear();
	}
	
	public static JSONObject toJson(String alterPara){
		JSONObject jsonObject = new JSONObject();
		
		JSONArray jsonGrade = JSONArray.fromObject(grade);
		jsonObject.put("grade", jsonGrade);
		
		JSONArray jsonGradeCourse = new JSONArray();
		for(int i = 0; i < gradeCourse.size(); i ++){
			jsonGradeCourse.add(JSONArray.fromObject(gradeCourse.get(i)));
		}
		jsonObject.put("gradeCourse", jsonGradeCourse);
		
		JSONArray jsonCoursePair = new JSONArray();
		for(int i = 0; i < coursePair.size(); i ++){
			jsonCoursePair.add(JSONArray.fromObject(coursePair.get(i)));
		}
		jsonObject.put("coursePair", jsonCoursePair);
		
		jsonObject.put("script", alterPara);

		return jsonObject;
	}
}
