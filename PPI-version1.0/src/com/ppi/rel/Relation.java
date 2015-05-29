package com.ppi.rel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ppi.tools.OneItem;
import com.ppi.tools.OneRelation;
import com.ppi.tools.RelationCheck;
import com.ppi.tools.RelationDictionary;

import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.tregex.TregexMatcher;
import edu.stanford.nlp.trees.tregex.TregexPattern;

/*
 * 用来检查每一个语句 的 每一项 的键值对 是不是蛋白质 或者 相互作用的关键词
 * 并为检查语句的作用关系（RealtionCheck类） 提供材料
 */

public class Relation {
	TregexPattern p = null;
	TregexMatcher m = null;
	String rule = null;
	Tree t = null;
	HashMap<String, String> keywordMap = null;
	ArrayList<String> kw_list = null; //保存所有蛋白质作用的关键词
	ArrayList<OneItem> rl = null;     //保存每个语句中 每一项 的键值对
	RelationCheck rc = null;		  //拿来检查每个语句的作用关系是否存在的类
	ArrayList<ArrayList<OneRelation>> ppi = null;
	
	public Relation(){
		rc = new RelationCheck();
		ppi = new ArrayList<ArrayList<OneRelation>>();
		
		/*
		 * 获取 Relation Dictionary
		 */
		RelationDictionary r = new RelationDictionary();
		kw_list = r.getKW();
		keywordMap = r.getKWMap();
	}
	
	public void setTree(Tree origin){	//设置 句子的语法树 然后把 进行每一项的检查（是不是蛋白质，是不是作用的关键词）
		rl = new ArrayList<OneItem>();
		rule = "NNP | /VB.?/ | JJ | NN | NNS | RB";
		t = origin;
		p = TregexPattern.compile(rule);
		TregexMatcher m = p.matcher(t);
	    while(m.find()){
//			System.out.println(m.getMatch().toString());
	    	
			checkWord(m.getMatch().toString());
		}
	    
	    rc.setRl(rl);  //把每一句话的 键值对 保存好
	    rc.checkPattern(); //通过规则来匹配 是不是存在相互作用
	    ppi.add(rc.getOneSentence());
	    
	}
	
	public void checkWord(String phrase){  //进行每一项的检查（是不是蛋白质，是不是作用的关键词）
		String sPattern = "\\((.+) (.+)\\)";
		
		Pattern p = Pattern.compile(sPattern);
		Matcher m = p.matcher(phrase);
		m.matches();
		
		String att = m.group(1);
		String word = m.group(2);

		if(att.equals("NNP")){
			if(word.contains("Protein")){
				System.out.println(att +": "+ word);
				rl.add(new OneItem("p", word));
			}else{
				if(checkKeyword(word)){
					System.out.println(att +": "+ word);
					rl.add(new OneItem("i", word));
				}
			}
		}else if(att.contains("VB") | att.equals("JJ") | att.equals("NN") | att.equals("NNS") | att.equals("VBD")){
			//判断是不是 关系的关键词
			if(checkKeyword(word)){
				System.out.println(att +": "+ word);
				rl.add(new OneItem("i", word));
			}
		}else if(att.equals("RB")){
			if(word.equals("n't") | word.equalsIgnoreCase("not")){
				System.out.println(att +": "+ word);
				rl.add(new OneItem("n", word));
			}
		}
		
		
	}

	public boolean checkKeyword(String word){   //检查 相互作用关系 的关键词

		for(int i=0; i<kw_list.size(); i++){
			if(word.equalsIgnoreCase(kw_list.get(i).toString())){
				return true;
			}
		}
		
		return false;
	}
	
	public ArrayList<ArrayList<OneRelation>> getPPI(){
		
//		for(int i=0; i<ppi.size(); i++){
//			System.out.println("Sentence " + i +": ");
//			for(int j=0; j<ppi.get(i).size();j++){
//				System.out.println(ppi.get(i).get(j).getProteinA() + " " + ppi.get(i).get(j).getInteract() + " " + ppi.get(i).get(j).getProteinB());
//			}
//			
//			System.out.println();
//		}
		
		return ppi;
	}
	
	public ArrayList<Integer> getNoRelationSentence(){
		return rc.getNRSentence();
	}
	
	public HashMap<String, String> getKeywordMap(){
		return keywordMap;
	}
	
}
