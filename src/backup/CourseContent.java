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
public class CourseContent extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CourseContent() {
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

		String ret = readContent(courseName + ".txt");
		response.setCharacterEncoding("UTF-8");		
		try {
			response.getWriter().write(new Gson().toJson((ret)));
			printInfo(new Gson().toJson((ret)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String readContent(String fileName) {
		fileName = Path.contentPath + fileName;
		File file = new File(fileName);
		if(!file.exists())
			return "File Not Found";
		String content = new String();
		try {
			FileInputStream fis = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(fis, "utf-8");
			BufferedReader reader = new BufferedReader(isr);
			String line = null;
			while((line = reader.readLine()) != null)
				content += line + "	";
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String contentToken [] = content.trim().split("	+");
		String ret = "<span style=\"font-weight:bold; font-size:20px;\">"
				+ "** " + contentToken[1] + " **</span><hr>\n"
				+ "<table class=\"table table-striped table-hover\">\n"
				+ "<tr><th width=\"60px\">課程名稱</th><td>" + contentToken[1] + "</td></tr>\n"
				+ "<tr><th width=\"60px\">課程時數</th><td>" + contentToken[3] + "</td></tr>\n"
				+ "<tr><th width=\"60px\">受訓對象</th><td>" + contentToken[5] + "</td></tr>\n"
				+ "<tr><th width=\"60px\">講師</th><td>" + contentToken[7] + "</td></tr>\n"
				+ "<tr><th width=\"60px\">訓練目的</th><td>" + contentToken[9] + "</td></tr>\n"
				+ "<tr><th width=\"60px\">課程限制</th><td>" + contentToken[11] + "</td></tr>\n"
				+ "</table>\n<br/>\n";
		ret += "<table class=\"table table-striped table-hover\">\n"
				+ "<tr><th width=\"60px\">項目(#)</th>"
				+ "<th>訓練目標(goal)</th>"
				+ "<th>課程大綱(outline)</th>"
				+ "<th>課程活動(method)</th>"
				+ "<th>教學媒體及教材(media/supporting materials)</th>"
				+ "<th>時數(min)</th>"
				+ "<th>說明 (Description)</th></tr>\n";
		int startIndex = 19;
		for(int i = 0; i < contentToken.length; i ++)
			if(contentToken[i].equals("說明  (Description)")){
				startIndex = i + 1;
				break;
			}
		for(int i = startIndex; i < contentToken.length; i = i + 7){
			try {
				Integer.parseInt(contentToken[i]);
				String temp = "<tr><td>" + contentToken[i] + "</td>"
					+ "<td>" + contentToken[i + 1] + "</td>"
					+ "<td>" + contentToken[i + 2] + "</td>"
					+ "<td>" + contentToken[i + 3] + "</td>"
					+ "<td>" + contentToken[i + 4] + "</td>"
					+ "<td>" + contentToken[i + 5] + "</td>";
				if(contentToken[i + 6].length() == 1){
					temp += "<td></td></tr>\n";
					i --;
				}
				else
					temp += "<td>" + contentToken[i + 6] + "</td></tr>\n";
				ret += temp;
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				break;
			}
		}
		ret += "</table>\n";
		return ret;
	}

	private void printInfo(String str){
		System.out.println("CourseContent INFO: " + str);
	}
}
