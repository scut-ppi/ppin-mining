package com.ppi.tools;

public class OneRelation {
	String p1;
	String p2;
	String i;
	
	public OneRelation(String pa, String pb, String interact){
		setProteinA(pa);
		setProteinB(pb);
		setInteraction(interact);
	}
	
	public void setProteinA(String pa){
		p1 = pa;
	}
	
	public void setProteinB(String pb){
		p2 = pb;
	}
	
	public void setInteraction(String interact){
		i = interact;
	}
	
	public String getProteinA(){
		return p1;
	}
	
	public String getProteinB(){
		return p2;
	}
	
	public String getInteract(){
		return i;
	}
	
}
