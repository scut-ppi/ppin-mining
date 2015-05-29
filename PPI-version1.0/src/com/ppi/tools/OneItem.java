package com.ppi.tools;

/*
 * 从句子中提取出来的每一个 键值对   如： NNP ProteinA  
 */

public class OneItem {

	public char att;
	public String name;
	
	public OneItem(String at, String word){
		setAtt(at);
		setName(word);
	}
	
	public void setAtt(String at){
		if(at.equals("p")){
			att = 'P';
		}else if(at.equals("i")){
			att ='I';
		}else if(at.equals("n")){
			att ='N';
		}
	}
	
	public void setName(String word){
		name = word;
	}
	
	
	public char getAtt(){
		return att;
	}
	
	public String getName(){
		return name;
	}
	
}
