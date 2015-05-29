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
 * �������ÿһ����� �� ÿһ�� �ļ�ֵ�� �ǲ��ǵ����� ���� �໥���õĹؼ���
 * ��Ϊ����������ù�ϵ��RealtionCheck�ࣩ �ṩ����
 */

public class Relation {
	TregexPattern p = null;
	TregexMatcher m = null;
	String rule = null;
	Tree t = null;
	HashMap<String, String> keywordMap = null;
	ArrayList<String> kw_list = null; //�������е��������õĹؼ���
	ArrayList<OneItem> rl = null;     //����ÿ������� ÿһ�� �ļ�ֵ��
	RelationCheck rc = null;		  //�������ÿ���������ù�ϵ�Ƿ���ڵ���
	ArrayList<ArrayList<OneRelation>> ppi = null;
	
	public Relation(){
		rc = new RelationCheck();
		ppi = new ArrayList<ArrayList<OneRelation>>();
		
		/*
		 * ��ȡ Relation Dictionary
		 */
		RelationDictionary r = new RelationDictionary();
		kw_list = r.getKW();
		keywordMap = r.getKWMap();
	}
	
	public void setTree(Tree origin){	//���� ���ӵ��﷨�� Ȼ��� ����ÿһ��ļ�飨�ǲ��ǵ����ʣ��ǲ������õĹؼ��ʣ�
		rl = new ArrayList<OneItem>();
		rule = "NNP | /VB.?/ | JJ | NN | NNS | RB";
		t = origin;
		p = TregexPattern.compile(rule);
		TregexMatcher m = p.matcher(t);
	    while(m.find()){
//			System.out.println(m.getMatch().toString());
	    	
			checkWord(m.getMatch().toString());
		}
	    
	    rc.setRl(rl);  //��ÿһ�仰�� ��ֵ�� �����
	    rc.checkPattern(); //ͨ��������ƥ�� �ǲ��Ǵ����໥����
	    ppi.add(rc.getOneSentence());
	    
	}
	
	public void checkWord(String phrase){  //����ÿһ��ļ�飨�ǲ��ǵ����ʣ��ǲ������õĹؼ��ʣ�
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
			//�ж��ǲ��� ��ϵ�Ĺؼ���
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

	public boolean checkKeyword(String word){   //��� �໥���ù�ϵ �Ĺؼ���

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
