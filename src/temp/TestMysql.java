package temp;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import database.ConnectionPara;



public class TestMysql {
	public static void main(String args[]) throws SQLException{
		Connection conn = null;
		try {
			Class.forName(ConnectionPara.DBDRIVER);
			conn = DriverManager.getConnection(ConnectionPara.DBNAME
					+ "?user=" + ConnectionPara.USERNAME
					+ "&password=" + ConnectionPara.PASSWORD
					+ "&useUnicode=true&characterEncoding=utf8");
			Statement stmt = conn.createStatement();
//			String sql = "select * from course_project join project_info "
//					+ "on course_project.project_id = project_info.id "
//					+ "where course_project.course_name = '專案管理';";
			String sql;
			ResultSet rs;
//			sql = "insert into job value ('仙剑');";
//			stmt.execute(sql);
			
			sql = "select * from course_info where name = 'CRM系統使用' ;";
			rs = stmt.executeQuery(sql);
			while(rs.next())
				System.out.println(rs.getString(1));
//			System.out.println(rs.next());
//			System.out.println(rs.getString(1));
			System.out.println("连接成功");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}		
	}
}
