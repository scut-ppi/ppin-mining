package com.ppi.tools;

import java.sql.*;

public class DatabaseConnect {

	private Connection ct = null;
	private Statement sm =null;
	private PreparedStatement ps = null;
	
	public DatabaseConnect(){
		
		try{
			Class.forName("com.mysql.jdbc.Driver");		
			
			ct = DriverManager.getConnection("jdbc:mysql://localhost:3306/ppi","root","123456");
			
		}catch(Exception  e){
			e.printStackTrace();
		}		
	}
	
	public Connection getConnect(){
		return ct;
	}
	
	public Statement getStatement(){
		
		try{
			sm = ct.createStatement();
		}catch(Exception e){
			e.printStackTrace();
		}
		return sm;
	}
	
	public PreparedStatement getPreparedStatement(String sqlstr){
		
		try{
			ps = ct.prepareStatement(sqlstr);
		}catch(Exception e){
			e.printStackTrace();
		}
		return ps;
	}
	
	public void close(){
		try{
			if (sm != null) {
				sm.close();
			}
			if (ct != null) {
				ct.close();
			}
		}catch(Exception  e){
			e.printStackTrace();
		}
	}
	
	
}
