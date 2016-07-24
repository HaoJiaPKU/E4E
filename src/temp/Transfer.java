package temp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import database.ConnectionPara;

public class Transfer {
	public static void main(String args[]) throws SQLException{
		
		String toDBNAME = "jdbc:mysql://162.105.30.30:3306/delta";
		String toUSERNAME = "root";
		String toPASSWORD = "seke1726";
		
		Connection conn = null;
		try {
			Class.forName(ConnectionPara.DBDRIVER);
			String sql;
			ResultSet rs;
			Statement stmt;
			ArrayList<ArrayList<String>> temp;

			//course_info
			conn = DriverManager.getConnection(ConnectionPara.DBNAME,
					ConnectionPara.USERNAME, ConnectionPara.PASSWORD);
			stmt = conn.createStatement();	
			sql = "select * from course_info;";
			rs = stmt.executeQuery(sql);
			temp = new ArrayList<ArrayList<String>> ();
			while(rs.next()) {
				ArrayList<String> t = new ArrayList<String>();
				for(int i = 1; i < 8; i ++)
					t.add(rs.getString(i));
				temp.add(t);
			}
			conn.close();
			
			conn = DriverManager.getConnection(toDBNAME,
					toUSERNAME, toPASSWORD);
			stmt = conn.createStatement();
			for(int i = 0; i < temp.size(); i ++) {
				for(int j = 0; j < temp.get(i).size(); j ++)
					System.out.print(temp.get(i).get(j) + " ");
				System.out.println();
				String s = new String();
				s = "'" + temp.get(i).get(0) + "'";
				s += "," + Double.parseDouble(temp.get(i).get(1));
				s += "," + Double.parseDouble(temp.get(i).get(2));
				s += ",'" + temp.get(i).get(3) + "'";
				s += ",'" + temp.get(i).get(4) + "'";
				s += ",'" + temp.get(i).get(5) + "'";
				s += ",'" + temp.get(i).get(6) + "'";
				sql = "insert into course_info values(" + s + ");";
				System.out.println(sql);
				stmt.execute(sql);
			}
			conn.close();
			
			//grade
			conn = DriverManager.getConnection(ConnectionPara.DBNAME,
					ConnectionPara.USERNAME, ConnectionPara.PASSWORD);
			stmt = conn.createStatement();	
			sql = "select * from grade;";
			rs = stmt.executeQuery(sql);
			temp = new ArrayList<ArrayList<String>> ();
			while(rs.next()) {
				ArrayList<String> t = new ArrayList<String>();
				for(int i = 1; i < 3; i ++) {
					t.add(rs.getString(i));
				}
				temp.add(t);
			}
			conn.close();
			
			conn = DriverManager.getConnection(toDBNAME,
					toUSERNAME, toPASSWORD);
			stmt = conn.createStatement();
			for(int i = 0; i < temp.size(); i ++) {
				for(int j = 0; j < temp.get(i).size(); j ++)
					System.out.print(temp.get(i).get(j) + " ");
				System.out.println();
				String s = new String();
				s = "'" + temp.get(i).get(0) + "'";
				s += ",'" + temp.get(i).get(1) + "'";
				sql = "insert into grade values(" + s + ");";
				System.out.println(sql);
				stmt.execute(sql);
			}
			conn.close();
			
			//course_project
			conn = DriverManager.getConnection(ConnectionPara.DBNAME,
					ConnectionPara.USERNAME, ConnectionPara.PASSWORD);
			stmt = conn.createStatement();	
			sql = "select * from course_project;";
			rs = stmt.executeQuery(sql);
			temp = new ArrayList<ArrayList<String>> ();
			while(rs.next()) {
				ArrayList<String> t = new ArrayList<String>();
				for(int i = 1; i < 3; i ++) {
					t.add(rs.getString(i));
				}
				temp.add(t);
			}
			conn.close();
			
			conn = DriverManager.getConnection(toDBNAME,
					toUSERNAME, toPASSWORD);
			stmt = conn.createStatement();
			for(int i = 0; i < temp.size(); i ++) {
				for(int j = 0; j < temp.get(i).size(); j ++)
					System.out.print(temp.get(i).get(j) + " ");
				System.out.println();
				String s = new String();
				s = "'" + temp.get(i).get(0) + "'";
				s += "," + Integer.parseInt(temp.get(i).get(1)) + "";
				sql = "insert into course_project values(" + s + ");";
				System.out.println(sql);
				stmt.execute(sql);
			}
			conn.close();
			
			//course_relation
			conn = DriverManager.getConnection(ConnectionPara.DBNAME,
					ConnectionPara.USERNAME, ConnectionPara.PASSWORD);
			stmt = conn.createStatement();	
			sql = "select * from course_relation;";
			rs = stmt.executeQuery(sql);
			temp = new ArrayList<ArrayList<String>> ();
			while(rs.next()) {
				ArrayList<String> t = new ArrayList<String>();
				for(int i = 1; i < 3; i ++) {
					t.add(rs.getString(i));
				}
				temp.add(t);
			}
			conn.close();
			
			conn = DriverManager.getConnection(toDBNAME,
					toUSERNAME, toPASSWORD);
			stmt = conn.createStatement();
			for(int i = 0; i < temp.size(); i ++) {
				for(int j = 0; j < temp.get(i).size(); j ++)
					System.out.print(temp.get(i).get(j) + " ");
				System.out.println();
				String s = new String();
				s = "'" + temp.get(i).get(0) + "'";
				s += ",'" + temp.get(i).get(1) + "'";
				sql = "insert into course_relation values(" + s + ");";
				System.out.println(sql);
				stmt.execute(sql);
			}
			conn.close();
			
			//job
			conn = DriverManager.getConnection(ConnectionPara.DBNAME,
					ConnectionPara.USERNAME, ConnectionPara.PASSWORD);
			stmt = conn.createStatement();	
			sql = "select * from job;";
			rs = stmt.executeQuery(sql);
			temp = new ArrayList<ArrayList<String>> ();
			while(rs.next()) {
				ArrayList<String> t = new ArrayList<String>();
				for(int i = 1; i < 2; i ++) {
					t.add(rs.getString(i));
				}
				temp.add(t);
			}
			conn.close();
			
			conn = DriverManager.getConnection(toDBNAME,
					toUSERNAME, toPASSWORD);
			stmt = conn.createStatement();
			for(int i = 0; i < temp.size(); i ++) {
				for(int j = 0; j < temp.get(i).size(); j ++)
					System.out.print(temp.get(i).get(j) + " ");
				System.out.println();
				String s = new String();
				s = "'" + temp.get(i).get(0) + "'";
				sql = "insert into job values(" + s + ");";
				System.out.println(sql);
				stmt.execute(sql);
			}
			conn.close();
			
			//job_grade_course
			conn = DriverManager.getConnection(ConnectionPara.DBNAME,
					ConnectionPara.USERNAME, ConnectionPara.PASSWORD);
			stmt = conn.createStatement();	
			sql = "select * from job_grade_course;";
			rs = stmt.executeQuery(sql);
			temp = new ArrayList<ArrayList<String>> ();
			while(rs.next()) {
				ArrayList<String> t = new ArrayList<String>();
				for(int i = 1; i < 4; i ++) {
					t.add(rs.getString(i));
				}
				temp.add(t);
			}
			conn.close();
			
			conn = DriverManager.getConnection(toDBNAME,
					toUSERNAME, toPASSWORD);
			stmt = conn.createStatement();
			for(int i = 0; i < temp.size(); i ++) {
				for(int j = 0; j < temp.get(i).size(); j ++)
					System.out.print(temp.get(i).get(j) + " ");
				System.out.println();
				String s = new String();
				s = "'" + temp.get(i).get(0) + "'";
				s += ",'" + temp.get(i).get(1) + "'";
				s += ",'" + temp.get(i).get(2) + "'";
				sql = "insert into job_grade_course values(" + s + ");";
				System.out.println(sql);
				stmt.execute(sql);
			}
			conn.close();
			
			//project_info
			conn = DriverManager.getConnection(ConnectionPara.DBNAME,
					ConnectionPara.USERNAME, ConnectionPara.PASSWORD);
			stmt = conn.createStatement();	
			sql = "select * from project_info;";
			rs = stmt.executeQuery(sql);
			temp = new ArrayList<ArrayList<String>> ();
			while(rs.next()) {
				ArrayList<String> t = new ArrayList<String>();
				for(int i = 1; i < 8; i ++) {
					t.add(rs.getString(i));
				}
				temp.add(t);
			}
			conn.close();
			
			conn = DriverManager.getConnection(toDBNAME,
					toUSERNAME, toPASSWORD);
			stmt = conn.createStatement();
			for(int i = 0; i < temp.size(); i ++) {
				for(int j = 0; j < temp.get(i).size(); j ++)
					System.out.print(temp.get(i).get(j) + " ");
				System.out.println();
				String s = new String();
				s = "'" + Integer.parseInt(temp.get(i).get(0)) + "'";
				s += ",'" + temp.get(i).get(1) + "'";
				s += ",'" + temp.get(i).get(2) + "'";
				s += ",'" + temp.get(i).get(3) + "'";
				s += ",'" + temp.get(i).get(4) + "'";
				s += ",'" + Integer.parseInt(temp.get(i).get(5)) + "'";
				s += ",'" + temp.get(i).get(6) + "'";
				sql = "insert into project_info values(" + s + ");";
				System.out.println(sql);
				stmt.execute(sql);
			}
			conn.close();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}		
	}
}
