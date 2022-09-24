package com.cos.blog.config;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class DB {
	// DB 연결
	public static Connection getConnection() {
		
		try {
			Context initContext = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			DataSource ds = (DataSource)envContext.lookup("jdbc/TestDB");
			Connection conn = ds.getConnection();	 // pulling 기술이라서 conn이라는 connection은 데이터베이스를 100개 가지고 있다.(근거: context.xml의 maxTotal="100")
			//그래서 필요한거마다 connection 객체를 가져와서 그걸로 데이터를 주거나 받으면 된다. 
			return conn;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	// DB 연결 끊기
	public static void close(Connection conn, PreparedStatement pstmt) {
		try {
			conn.close();
			pstmt.close();
		} catch (Exception e) { 
			e.printStackTrace();
		}
	}
	
	// DB 연결 끊기
		public static void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
			try {
				conn.close();
				pstmt.close();
				rs.close();
			} catch (Exception e) { 
				e.printStackTrace();
			}
		}
}
