package backup;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * Servlet implementation class DownloadsServlet
 */
public class CourseInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CourseInfo() {
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
//		String ret = readContent(courseName + ".txt", 3);
		String ret = readInfo(courseName);
		response.setCharacterEncoding("UTF-8");		
		try {
			response.getWriter().write(new Gson().toJson((ret)));
			printInfo(new Gson().toJson((ret)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String readInfo(String fileName) {
		File file = new File(Path.courseInfoPath);
		if(!file.exists())
			return "File Not Found";
		try {
			FileInputStream fis = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(fis, "utf-8");
			@SuppressWarnings("resource")
			BufferedReader reader = new BufferedReader(isr);
			String line = null;
			while((line = reader.readLine()) != null){
				String [] temp = line.trim().split("	");
				if(temp[0].equals(fileName)){
//					System.out.println("<td>" + temp[1] + "</td><td>" + temp[2] + "</td>");
					return "<td>" + temp[0] + "</td>"
							+ "<td>" + temp[1] + "</td>"
							+ "<td>" + temp[2] + "</td>"
							+ "<td><button class=\"btn\" onclick=\"deleteCourse()\">É¾³ý</button></td>";
				}
			}
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "File Not Found";
	}

	private void printInfo(String str){
		System.out.println("CourseTime INFO: " + str);
	}
}
