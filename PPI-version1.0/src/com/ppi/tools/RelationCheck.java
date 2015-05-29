package com.ppi.tools;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * 检查每个语句的相互作用关系  找出：  P I P  这一类关系
 */

public class RelationCheck {
	ArrayList<OneItem> rl = null;  //每个句子的关系
	StringBuffer sb = null;
	String tmp = null;
	ArrayList<OneRelation> oneSentence = null;
	ArrayList<Integer> haveNoRelation;   //记录下不含有蛋白质相互作用的句子 的 index
	int count; //记录当前处理的语句 的 index
	
	public RelationCheck(){
		count = 0;
		haveNoRelation = new ArrayList<Integer>();
	}
	
	public void setRl(ArrayList<OneItem> r){
		rl =r;
		oneSentence = new ArrayList<OneRelation>();
	}
	
	public void checkPattern(){
		sb = new StringBuffer();
		
		for(int i=0; i<rl.size(); i++){
			sb.append(rl.get(i).getAtt());
		}
		
		tmp = sb.toString();
		
		String thePattern[] ={"P{1,}I{1}P{1,}","P{1,}I{1}P{1,}I","P{1,}N{1}I{1}P{1,}","I{1}P{1,}I{1}P{1,}","I{1}P{1,}"
				             ,"P{1,}I{1}P{1,}N{1}I{1}P{1,}","P{1}I{1}P{1,}I{1}P{1,}"};
		Pattern p ;
		Matcher m ;
		
		for(int i=0; i<thePattern.length; i++){
			p = Pattern.compile(thePattern[i]);
			m = p.matcher(tmp);
			if(m.matches()){
//				System.out.println(thePattern[i]+" : "+m.matches());
//				System.out.println(tmp.indexOf("I"));
				generateRelation(i,tmp.indexOf("I"),tmp.length(), tmp);
			}
			
		}
		
		count++; //下一个句子的index
		
	}
	
	public void generateRelation(int type, int POI, int length, String tmp){  //POI: position of interact word
		switch (type) {
		case 0:
//			System.out.println("position of interact: " + POI);
//			System.out.println("length:" + length);
//			System.out.println("size:" + rl.size());
			for(int i=0; i<POI; i++){
				for(int j=POI+1 ; j<length; j++){
					System.out.println(rl.get(i).getName() +" "+ rl.get(POI).getName() +" "+rl.get(j).getName());
					oneSentence.add(new OneRelation(rl.get(i).getName(), rl.get(j).getName(), rl.get(POI).getName()));
				}
			}
			break;
		case 1:
			System.out.println("position of interact: " + POI);
			for(int i=0; i<POI; i++){
				for(int j=POI+1 ; j<length-1; j++){
					System.out.println(rl.get(i).getName() +" "+ rl.get(POI).getName() +" "+rl.get(j).getName());
					oneSentence.add(new OneRelation(rl.get(i).getName(), rl.get(j).getName(), rl.get(POI).getName()));
				}
			}
			break;
		case 2:
			haveNoRelation.add(count);
			break;
		case 3:
			POI = tmp.indexOf("I", 1);
			System.out.println("position of interaction: "+POI);
			for(int i=1; i<POI; i++){
				for(int j=POI+1 ; j<length; j++){
					System.out.println(rl.get(i).getName() +" "+ rl.get(POI).getName() +" "+rl.get(j).getName());
					oneSentence.add(new OneRelation(rl.get(i).getName(), rl.get(j).getName(), rl.get(POI).getName()));
				}
			}			
			break;
		case 4:
			System.out.println("position of interaction: "+POI);
			for(int i=1; i<length; i++){
				for(int j=i+1 ; j<length; j++){
					System.out.println(rl.get(i).getName() +" "+ rl.get(POI).getName() +" "+rl.get(j).getName());
					oneSentence.add(new OneRelation(rl.get(i).getName(), rl.get(j).getName(), rl.get(POI).getName()));
				}
			}			
			break;
		case 5:
			System.out.println("position of interaction: "+POI);
			int not = tmp.indexOf("N");
			for(int i=0; i<POI; i++){
				for(int j=POI+1 ; j<not; j++){
					System.out.println(rl.get(i).getName() +" "+ rl.get(POI).getName() +" "+rl.get(j).getName());
					oneSentence.add(new OneRelation(rl.get(i).getName(), rl.get(j).getName(), rl.get(POI).getName()));
				}
			}
			break;
		case 6:
			int I2 = tmp.indexOf("I", POI+1);
			for(int i=POI+1; i<I2; i++){
				System.out.println(rl.get(0).getName() +" "+ rl.get(POI).getName() +" "+rl.get(i).getName());
				oneSentence.add(new OneRelation(rl.get(0).getName(), rl.get(i).getName(), rl.get(POI).getName()));
			}
			for(int i=I2+1; i<length; i++){
				System.out.println(rl.get(0).getName() +" "+ rl.get(I2).getName() +" "+rl.get(i).getName());
				oneSentence.add(new OneRelation(rl.get(0).getName(), rl.get(i).getName(), rl.get(I2).getName()));
			}
			break;
		default:
			break;
		}
	}
	
	public ArrayList<Integer> getNRSentence(){
		return haveNoRelation;
	}
	
	public ArrayList<OneRelation> getOneSentence(){
		return oneSentence;
	}
	
}
