package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import database.ConnectionPara;

/**
 * Servlet implementation class DownloadsServlet
 */
public class CourseBriefInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CourseBriefInfo() {
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
		String courseName = request.getParameter("courseName");
		printInfo(courseName);
		String ret = new String();
		try {
			ret = readInfo(courseName);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
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
	
	private String readInfo(String courseName) throws SQLException {		
		try {
			Class.forName(ConnectionPara.DBDRIVER);
			Connection conn = DriverManager.getConnection(ConnectionPara.DBNAME
					+ "?user=" + ConnectionPara.USERNAME
					+ "&password=" + ConnectionPara.PASSWORD
					+ "&useUnicode=true&characterEncoding=utf8");
			Statement stmt = conn.createStatement();
			String sql;
			ResultSet rs;
			sql = "select hour, project_num from course_info where name = '" + courseName + "';";
			rs = stmt.executeQuery(sql);
			if(rs.next()){
				return "<td>" + courseName + "</td>"
						+ "<td>" + rs.getString(1) + "</td>"
						+ "<td>" + rs.getString(2) + "</td>"
						+ "<td><button class=\"btn\" onclick=\"deleteCourse()\">删除</button></td>";
			}
			
			conn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return "File Not Found";
	}

	private void printInfo(String str){
		System.out.println("CourseTime INFO: " + str);
	}
}
