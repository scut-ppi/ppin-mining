package com.ppi.tools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class Dbsaveppi {
	
	//原始材料
	String PMID;
	ArrayList<ArrayList<OneRelation>> ppi;
	ArrayList<Integer> haveNoRelation;
	HashMap<String, String> keywordMap;
	ArrayList<String> originSen;
	ArrayList<HashMap<String, String>> proteinName;
	
	//数据库的连接对象
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
	
	public void savePPI(){
		
		try{
			sqlstr = "insert relation set pmid=?,pa=?,pb=?,ita=?,sen=?;";
			
			ps = dbconnect.getPreparedStatement(sqlstr);
			
			for(int i=0; i<originSen.size(); i++){
				boolean check = true;
				HashMap<String, String> tmpHash = proteinName.get(i);
				ArrayList<OneRelation> tmpPPI = ppi.get(i);
				
				for( int cc=0; cc<haveNoRelation.size(); cc++){
					if(i == haveNoRelation.get(cc).intValue() ){
						check = false;
						break;
					}
				}
				if(check){
					for(int j=0; j<tmpPPI.size(); j++){
						OneRelation tmpOR = tmpPPI.get(j);
						try{
							ps.setString(1, PMID);
							ps.setString(2, tmpHash.get(tmpOR.getProteinA()));
							ps.setString(3, tmpHash.get(tmpOR.getProteinB()));
							ps.setString(4, keywordMap.get(tmpOR.getInteract()));
							ps.setString(5, originSen.get(i).toString());
							ps.executeUpdate();
						}catch(Exception e){
							e.printStackTrace();
						}
//						System.out.println("Relation : ");
//						System.out.println("PubMed ID: " + PMID);
//						System.out.println("Protein A: " + tmpHash.get(tmpOR.getProteinA()));
//						System.out.println("Protein B: " + tmpHash.get(tmpOR.getProteinB()));
//						System.out.println("Interact: " + keywordMap.get(tmpOR.getInteract()));
//						System.out.println(originSen.get(i).toString());
					}
					
				}
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		
	}
	
	/*************************** 判断当前挖掘的文章是不是已经存在数据库了 ********************************/
	public boolean checkArticle(String PMID){
		try{
			sqlstr = "select * from relation where pmid ="+PMID;
			ps = ct.prepareStatement(sqlstr);
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				System.out.println("This article has already saved.");
				return true;  //真 ： 表示已存在这篇文章
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;  //假 ： 表示还没有这篇文章
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
	
	/*************************** set和get函数 *****************************/
	public String getPMID() {
		return PMID;
	}

	public void setPMID(String pMID) {
		PMID = pMID;
	}

	public ArrayList<ArrayList<OneRelation>> getPpi() {
		return ppi;
	}

	public void setPpi(ArrayList<ArrayList<OneRelation>> ppi) {
		this.ppi = ppi;
	}

	public ArrayList<Integer> getHaveNoRelation() {
		return haveNoRelation;
	}

	public void setHaveNoRelation(ArrayList<Integer> haveNoRelation) {
		this.haveNoRelation = haveNoRelation;
	}

	public HashMap<String, String> getKeywordMap() {
		return keywordMap;
	}

	public void setKeywordMap(HashMap<String, String> keywordMap) {
		this.keywordMap = keywordMap;
	}

	public ArrayList<String> getOriginSen() {
		return originSen;
	}

	public void setOriginSen(ArrayList<String> originSen) {
		this.originSen = originSen;
	}

	public ArrayList<HashMap<String, String>> getProteinName() {
		return proteinName;
	}

	public void setProteinName(ArrayList<HashMap<String, String>> proteinName) {
		this.proteinName = proteinName;
	}
	
}
