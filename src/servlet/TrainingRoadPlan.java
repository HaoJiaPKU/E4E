package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;

import database.ConnectionPara;
import database.FieldIndex;

/**
 * Servlet implementation class DownloadsServlet
 */
public class TrainingRoadPlan extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static HashSet <String> projects = new HashSet <String> ();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TrainingRoadPlan() {
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
		projects.clear();
		String ret = new String();
		
		Elements trs = Jsoup.parse("<table class=\"table table-striped table-hover\">" + request.getParameter("table") + "</table>").select("tr");
		int trCounter = 0;
		HashSet<String> unique = new HashSet<String> ();
		for(Element tr : trs){
			if(trCounter == 0){
				trCounter ++;
				continue;
			}
			String courseName = tr.select("td").first().text().toString();
			try {
				ret += "<div class=\"plan\" style=\"margin:15px;\">"
						+ readContent(courseName, unique) + "</div>\n<hr>\n";
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		response.setCharacterEncoding("UTF-8");		
		try {
			response.getWriter().write(new Gson().toJson((ret)));
			printInfo(new Gson().toJson((ret)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String readContent(String courseName, HashSet<String> unique) throws SQLException {
		String ret = new String();
		try {
			Class.forName(ConnectionPara.DBDRIVER);
			Connection conn = DriverManager.getConnection(ConnectionPara.DBNAME
					+ "?user=" + ConnectionPara.USERNAME
					+ "&password=" + ConnectionPara.PASSWORD
					+ "&useUnicode=true&characterEncoding=utf8");
			Statement stmt = conn.createStatement();
			String sql;
			ResultSet rs;
			sql = "select * from course_info where name = '" + courseName + "';";
			rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				ret = "<span style=\"font-weight:bold; font-size:20px;\">"
						+ "** " + courseName + " **</span><hr>\n"
						+ "<table class=\"table table-striped table-hover\">\n"
						+ "<tr><th width=\"60px\">課程名稱</th><td>" + rs.getString(FieldIndex.COURSE_INFO_NAME) + "</td></tr>\n"
						+ "<tr><th width=\"60px\">課程時數</th><td>" + rs.getString(FieldIndex.COURSE_INFO_HOUR) + "</td></tr>\n"
						+ "<tr><th width=\"60px\">受訓對象</th><td>" + rs.getString(FieldIndex.COURSE_INFO_POPULATION) + "</td></tr>\n"
						+ "<tr><th width=\"60px\">講師</th><td>" + rs.getString(FieldIndex.COURSE_INFO_TEACHER) + "</td></tr>\n"
						+ "<tr><th width=\"60px\">訓練目的</th><td>" + rs.getString(FieldIndex.COURSE_INFO_GOAL) + "</td></tr>\n"
						+ "<tr><th width=\"60px\">課程限制</th><td>" + rs.getString(FieldIndex.COURSE_INFO_LIMITATION) + "</td></tr>\n"
						+ "</table>\n<br/>\n";
				
				sql = "select project_info.* from course_project join project_info "
						+ "on course_project.project_id = project_info.id "
						+ "where course_project.course_name = '" + courseName + "';";
				rs = stmt.executeQuery(sql);
				
				if(rs.next()){
					rs.previous();
					ret += "<table class=\"table table-striped table-hover\">\n"
							+ "<tr><th width=\"60px\">項目(#)</th>"
							+ "<th>訓練目標(goal)</th>"
							+ "<th>課程大綱(outline)</th>"
							+ "<th>課程活動(method)</th>"
							+ "<th>教學媒體及教材(media/supporting materials)</th>"
							+ "<th>時數(min)</th>"
							+ "<th>說明 (Description)</th></tr>\n";
					int projectNum = 1;
					while(rs.next()){
						if(unique.contains(rs.getString(FieldIndex.PROJECT_INFO_ID)))
							continue;
						unique.add(rs.getString(FieldIndex.PROJECT_INFO_ID));
						ret += "<tr>"
							+ "<td>" + (projectNum ++) + "</td>"
							+ "<td>" + rs.getString(FieldIndex.PROJECT_INFO_GOAL) + "</td>"
							+ "<td>" + rs.getString(FieldIndex.PROJECT_INFO_OUTLINE) + "</td>"
							+ "<td>" + rs.getString(FieldIndex.PROJECT_INFO_METHOD) + "</td>"
							+ "<td>" + rs.getString(FieldIndex.PROJECT_INFO_MEDIA) + "</td>"
							+ "<td>" + rs.getString(FieldIndex.PROJECT_INFO_MINUTE) + "</td>"
							+ "<td>" + rs.getString(FieldIndex.PROJECT_INFO_DESCRIPTION) + "</td>"
							+ "</tr>\n";
					}
					ret += "</table>\n<br/>\n";
				}
			}
			conn.close();
			return ret;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return "File Not Found";
	}

	private void printInfo(String str){
		System.out.println("TrainingPlan INFO: " + str);
	}
}
