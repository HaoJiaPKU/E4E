package backup;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * Servlet implementation class DownloadsServlet
 */
public class TrainingRoad extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TrainingRoad() {
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
		//if(!(sourceGrade.equals(targetGrade) && sourceJob.equals(targetJob)))
		ret = buildRoadMap(sourceGrade, sourceJob, targetGrade, targetJob, planType);
		response.setCharacterEncoding("UTF-8");		
		try {
			response.getWriter().write(new Gson().toJson((ret)));
			printInfo(new Gson().toJson((ret)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String buildRoadMap(String sourceGrade, String sourceJob, String targetGrade
			, String targetJob, String planType) throws IOException{
		
		String [] job = {};
		String [] grade = {};
		String [][] courseNames = new String [grade.length][job.length];
		
		String fileName = Path.courseNamePath;
		File file = new File(fileName);
		if(!file.exists())
			return "File Not Found";
		try {
			FileInputStream fis = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(fis, "utf-8");
			BufferedReader reader = new BufferedReader(isr);
			String line = null;
			line = reader.readLine();
			job = line.trim().split("	");
			line = reader.readLine();
			grade = line.trim().split("	");
			courseNames = new String [grade.length][job.length];
			
			int gradeIndex = 0, jobIndex = 0;
			while((line = reader.readLine()) != null){
				courseNames[gradeIndex][jobIndex] = line.trim();
				jobIndex ++;
				if(jobIndex >= job.length){
					jobIndex = 0;
					gradeIndex ++;
				}
			}
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		File filew = new File(fileName + ".mysql.txt");
//		try {
//			FileOutputStream fis = new FileOutputStream(filew);
//			OutputStreamWriter isr = new OutputStreamWriter(fis, "utf-8");
//			BufferedWriter reader = new BufferedWriter(isr);
//			for(int i = 0; i < job.length; i ++){
//				for(int j = 0; j < grade.length; j ++){
//					String [] temp = courseNames[j][i].trim().split(" +");
//					for(int k = 0; k < temp.length; k ++){
//						reader.write(job[i] + "	" + grade[j] + "	" + temp[k]);
//						reader.newLine();
//					}
//				}
//			}
//			reader.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		HashMap <String, String> courseRelation = new HashMap <String, String> ();
		
		fileName = Path.courseInfoPath;
		file = new File(fileName);
		if(!file.exists())
			return "File Not Found";
		try {
			FileInputStream fis = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(fis, "utf-8");
			BufferedReader reader = new BufferedReader(isr);
			String line = null;
			while((line = reader.readLine()) != null){
				String [] temp = line.trim().split("	");
				if(temp.length > 3 && temp[3].length() > 0)
				courseRelation.put(temp[0], temp[3]);
			}
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int sg = 0, sj = 0, tg = 0, tj = 0;
		for(int i = 0; i < grade.length; i ++){
			if(sourceGrade.equals(grade[i]))
				sg = i;
			if(targetGrade.equals(grade[i]))
				tg = i;
		}
		for(int i = 0; i < job.length; i ++){
			if(sourceJob.equals(job[i]))
				sj = i;
			if(targetJob.equals(job[i]))
				tj = i;
		}
		
		if(sj == tj && sg > tg)
			return null;
		
		HashMap <String, Coordinate> courseLoc = new HashMap <String, Coordinate>();
		int XStart = 20, YStart = 20, XInc = 200, YInc = 20, fontSize = 15;
		ArrayList <String> sourceCourses = new ArrayList <String> ();
		ArrayList <String> targetCourses = new ArrayList <String> ();
				
		//不能更换顺序！//修改目标课程的位置
		String [] temp;
		for(int i = tg; i <= tg; i ++){
			temp = courseNames[i][tj].split(" ");
			for(int j = 0; j < temp.length; j ++){
				if(!targetCourses.contains(temp[j]))
					targetCourses.add(new String(temp[j]));
//				if(i != tg && !sourceCourses.contains(temp[j]))
//					sourceCourses.add(new String(temp[j]));
			}
		}
		//因为后写的起始Courses ^_^
		temp = courseNames[sg][sj].split(" ");
		for(int i = 0; i < temp.length; i ++)
			sourceCourses.add(new String(temp[i]));
		
		CourseGraph.loadCourseGraph();
		CourseGraph.BFS(sourceCourses, targetCourses, planType);
		
		String ret = "var jg = new jsGraphics(\"list\");"
				+ "jg.setFont(\"Georgia\", \"" + fontSize + "px\", Font.Bold);"
				+ "jg.setStroke(1);";
		int x = XStart, y = YStart;
		for(int i = 0; i < temp.length; i ++){
			ret += "jg.drawStringRect(\"" + temp[i] + "\", " + x + ", " + y + ");";
			//记录当前课程的坐标
			courseLoc.put(temp[i], new Coordinate(x + temp[i].length() * fontSize, y + 9));
			y = y + YInc;
			sourceCourses.add(new String(temp[i]));
		}
		
		for(int i = sg; i <= tg; i ++){
			temp = courseNames[i][tj].split(" ");
			x = x + XInc;
			y = YStart;
			boolean XChange = false;
			for(int j = 0; j < temp.length; j ++){
				//去除重复的课程
				if(courseLoc.containsKey(temp[j]))
					continue;
				XChange = true;
				ret += "jg.setColor(\"#000000\");";
				ret += "jg.drawStringRect(\"" + temp[j] + "\", " + x + ", " + y + ");";
				if(courseRelation.containsKey(temp[j])){
					String preCourse [] = courseRelation.get(temp[j]).split(" ");
					for(int k = 0; k < preCourse.length; k ++){
						if(courseLoc.containsKey(preCourse[k])){
							Coordinate tempCoordinate = courseLoc.get(preCourse[k]);
							ret += "jg.setColor(\"#000000\");";
							for(int l = 0; l < CourseGraph.recommendation.size(); l ++)
								if(CourseGraph.recommendation.get(l).contains(preCourse[k] + " " + temp[j]))
									ret += "jg.setColor(\"#ff0000\");";				
							ret += "jg.drawLine(" + tempCoordinate.x + ", " + tempCoordinate.y + ", " + x + ", " + (y + 9)+ ");";
						}
					}
				}
				courseLoc.put(temp[j], new Coordinate(x + temp[j].length() * fontSize, y + 9));
				y = y + YInc;
			}
			if(!XChange)
				x = x - XInc;
		}
		ret += "jg.paint();";
		return ret;
//		}
	}
	
	private void printInfo(String str){
		System.out.println("TrainingRoad INFO: " + str);
	}
}

class Coordinate{
	int x;
	int y;
	
	public Coordinate(int x, int y){
		this.x = x;
		this.y = y;
	}
}
