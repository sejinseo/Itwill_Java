package edu.java.jdbc01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import oracle.jdbc.OracleDriver;

import static edu.java.jdbc.ojdbc.OracleJdbc.*;

public class JdbcMain01 {

	public static void main(String[] args) {
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			// 3.O
			DriverManager.registerDriver(new OracleDriver());
			
			// 4. 
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			System.out.println("DB 연결 성공");
			
			// 5. statement 객체 생성
			stmt = conn.createStatement();
			System.out.println("Statement 객체 생성 성공");
			
			String sqlSelect = "select * from blogs"; // 전체 검색 SQL 문장
			// (주의)Java에서는 SQL 문장 끝에 세미콜론(;)을 사용하지 않음!
			
			// 6. Statement 객체를 사용해서 SQL 문장을 실행시키고, 그 결과를 저장
			rs = stmt.executeQuery(sqlSelect);
			System.out.println("쿼리 실행 성공");
			
			// 7. DB가 보내준 결과 처리
			while (rs.next()) { // ResultSet에 레코드(행, row)가 있으면 
				int no = rs.getInt(COL_BLOG_NO);
				String title = rs.getString(COL_TITLE);
				String content = rs.getString(COL_CONTENT);
				Date date = rs.getTimestamp(COL_UPDATE_TIME);
				
				String row = no + " | " + title + " | " + content + " | " + date;
				System.out.println(row);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				// 리소스 생성 순서: Connection -> Statement -> ResultSet
				// 리소스 해제 순서: ResultSet -> Statement -> Connection
				// 객체들이 생성된 순서와 반대로 리소스들을 해제해야 함.
				rs.close(); // ResultSet 리소스 해제
				stmt.close(); // Statement 리소스 해제
				conn.close(); // DB connection 해제
				System.out.println("DB 연결 끊기 성공");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		

	}

}
