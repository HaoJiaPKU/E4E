package backup;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;

/**
 * Servlet implementation class DownloadsServlet
 */
public class TrainingPlan extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static HashSet <String> projects = new HashSet <String> ();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TrainingPlan() {
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
		for(Element tr : trs){
			if(trCounter == 0){
				trCounter ++;
				continue;
			}
			String courseName = tr.select("td").first().text().toString();
			ret += "<div class=\"plan\" style=\"margin:15px;\">"
					+ "<span style=\"font-weight:bold; font-size:20px;\">"
					+ "** " + courseName + " **</span><hr>\n"
					+ readContent(courseName + ".txt") + "</div>\n<hr>\n";
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
		String ret = "<table class=\"table table-striped table-hover\">\n"
				+ "<tr><th width=\"60px\">課程名稱</th><td>" + contentToken[1] + "</td></tr>\n"
				+ "<tr><th width=\"60px\">課程時數</th><td>" + contentToken[3] + "</td></tr>\n"
				+ "<tr><th width=\"60px\">受訓對象</th><td>" + contentToken[5] + "</td></tr>\n"
				+ "<tr><th width=\"60px\">講師</th><td>" + contentToken[7] + "</td></tr>\n"
				+ "<tr><th width=\"60px\">訓練目的</th><td>" + contentToken[9] + "</td></tr>\n"
				+ "<tr><th width=\"60px\">課程限制</th><td>" + contentToken[11] + "</td></tr>\n"
				+ "</table>\n<br/>\n";
		ret += "<table class=\"table table-striped table-hover\">\n"
				+ "<tr><th width=\"60px\">項目(#)</th><th>訓練目標(goal)</th><th>課程大綱(outline)</th><th>課程活動(method)</th><th>教學媒體及教材(media/supporting materials)</th><th>時數(min)</th><th>說明 (Description)</th></tr>\n";
		int startIndex = 19;
		for(int i = 0; i < contentToken.length; i ++)
			if(contentToken[i].equals("說明  (Description)")){
				startIndex = i + 1;
				break;
			}
		int projectNum = 1;
		for(int i = startIndex; i < contentToken.length; i = i + 7){
			try {
				Integer.parseInt(contentToken[i]);
				String temp = "<td>" + contentToken[i + 1] + "</td>"
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
				
				if(!projects.contains(temp))
					projects.add(temp);
				else{
					temp = temp.replaceAll("<td>", "<td><del>");
					temp = temp.replaceAll("</td>", "</del></td>");
					
				}
				ret += "<tr><td>" + (projectNum ++) + "</td>" + temp;
				
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				break;
			}
		}
		ret += "</table>\n<br/>\n";
		return ret;
	}

	private void printInfo(String str){
		System.out.println("TrainingPlan INFO: " + str);
	}
}
