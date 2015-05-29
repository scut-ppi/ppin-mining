package com.ppi.tools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Dbgetppi {
	
	private DatabaseConnect dbconnect = null;
	private Connection ct = null;
	private Statement sm =null;
	private ResultSet rs =null;
	private PreparedStatement ps = null;
	String sqlstr = null;
	
	/**********************获取与数据库的连接 BY LZJ ***************************/
	public void open(){
		try{
			
			dbconnect = new DatabaseConnect();
			
			ct = dbconnect.getConnect();
			
			sm = dbconnect.getStatement();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/****************************** 获取特定PMID对应文章的PPI ***********************************/
	public ArrayList<OneSentence> getPPIRecord(String PMID){
		ArrayList<OneSentence> array = new ArrayList<OneSentence>();
		try{
			
			OneSentence os = null;
			
			sqlstr = "select * from relation where pmid ="+PMID;
			ps = ct.prepareStatement(sqlstr);
			
			rs = ps.executeQuery();
			
			while(rs.next()){
				os = new OneSentence();
				os.setPMID(rs.getString(1));
				os.setPA(rs.getString(2));
				os.setPB(rs.getString(3));
				os.setITA(rs.getString(4));
				os.setSEN(rs.getString(5));
				
//				System.out.println(os.getPMID());
//				System.out.println(os.getPA());
//				System.out.println(os.getPB());
//				System.out.println(os.getITA());
//				System.out.println(os.getSEN());
				
				array.add(os);
			}
			
			return array;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	/**********************切断与数据库的连接  BY LZJ ***************************/
	public void close(){
		
		try{
			if (rs != null) {
				rs.close();
			}
			if (sm != null) {
				sm.close();
			}
			if (ct != null) {
				ct.close();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	
}
