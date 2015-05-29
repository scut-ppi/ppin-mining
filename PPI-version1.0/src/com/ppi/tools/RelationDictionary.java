package com.ppi.tools;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import jxl.*;

//获取EXCEL当中的Relation Dictionary
public class RelationDictionary {
	int i;
    Sheet sheet;
	Workbook book;
	String cell, keyword;
	ArrayList<String> kw_list;
	HashMap<String, String> keywordMap;
	
	public RelationDictionary(){
		generateList();
	
	}
	
	public void generateList(){
		
		try{
			book = Workbook.getWorkbook(new File("F:\\relation_keyword.xls"));
			
			//获得第一个工作表对象(ecxel中sheet的编号从0开始,0,1,2,3,....)
            sheet=book.getSheet(0); 
            
            kw_list = new ArrayList<String>();
            keywordMap = new HashMap<String, String>();
            
            for(int count = 1; count<89; count++){
            	cell=sheet.getCell(1,count).getContents();
            	keyword = sheet.getCell(0,count).getContents();
            	
                //System.out.println(cell);
                String kw[] = cell.split(", ");

                for(int i=0; i<kw.length; i++){
                	kw_list.add(kw[i]);
                	keywordMap.put(kw[i], keyword);
                }
            }
            
//            for(int i=0; i<kw_list.size(); i++){
//            	System.out.println(kw_list.get(i).toString());
//            }
                        
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void generateIList(){
		
	}
	
	public HashMap<String, String> getKWMap(){
		return keywordMap;
	}
	
	
	public ArrayList<String> getKW(){
		return kw_list;
	}
	
}
