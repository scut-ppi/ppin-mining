package com.ppi.process;

import java.util.ArrayList;
import java.util.HashMap;

import com.ppi.ner.Recognition;
import com.ppi.pars.Parsing;
import com.ppi.preprocess.Preprocessor;
import com.ppi.rel.Relation;
import com.ppi.tools.Dbgetppi;
import com.ppi.tools.Dbsaveppi;
import com.ppi.tools.OneSentence;

import edu.stanford.nlp.trees.Tree;


public class Processor {
	String PMID;
	String AB;
	
	Preprocessor pre = null;
	Dbsaveppi saveppi = null;
	Dbgetppi getPPI = null;
	
	public Processor(String text){
		saveppi = new Dbsaveppi();
		saveppi.open();
		
		pre = new Preprocessor(text);
		
		PMID = pre.getPMID();
		AB = pre.getAB();
		
		if(null == PMID){
			System.out.println("PMID is null. Input data has wrong format.");
			saveppi.close();
			return;
		}else if(AB == ""){
			System.out.println("AB is empty. Input data has wrong format.");
			PMID = null;
			saveppi.close();
			return;
		}
		
		//判断该文章在数据库中是否存在   若已存在，则不需要进一步挖掘关系和保存关系
		if(saveppi.checkArticle(PMID)){
			System.out.println("This article is already in database.");
			saveppi.close();
			return;
		}
		
		processPPI();
		saveppi.close();
		
	}
	
	public void processPPI(){
		/*
		 * 实现NER，并把每一个句子提取出来。
		 */
		Recognition reg = new Recognition(AB);
		ArrayList<String> replaceProtein = reg.getReplaceProtein();
		
		ArrayList<String> originSen = reg.getOriginSentence();
		ArrayList<HashMap<String, String>> proteinName = reg.getProteinName();
		
		/*
		 * 用parser进行语法树的构建
		 */
		System.out.println("\n############################# Parser ############################");
		Parsing pr = new Parsing();
		
		//对每一句话进行语法解析
		for(int i=0; i<replaceProtein.size(); i++){
			pr.setSentence(replaceProtein.get(i).toString());
//			System.out.println("\nTree"+(i+1)+" :");
			pr.parseSentence();
		}
		
		/*
		 * 用Tregex对语法树进行 规则匹配提取
		 */
		System.out.println("\n########################## Tregex ###############################");
		ArrayList<Tree> treeSet = pr.getAllTree();   //获取所有含有蛋白质单词的句子的语法树
		Relation rel = new Relation();
		for(int i=0; i<treeSet.size(); i++){
			System.out.println("\nTregex"+(i+1)+" :");
			rel.setTree(treeSet.get(i));
		}
		
		/*
		 * 通过使用Dbsaveppi对象进行PPI的保存
		 */
		saveppi.setPMID(PMID);
		saveppi.setOriginSen(originSen);
		saveppi.setProteinName(proteinName);
		saveppi.setKeywordMap(rel.getKeywordMap());
		saveppi.setHaveNoRelation(rel.getNoRelationSentence());
		saveppi.setPpi(rel.getPPI());
		
		saveppi.savePPI();

	}
	
	public ArrayList<OneSentence> getResult(){
		if(null == PMID){
			System.out.println("PMID is empty. Couln't get PPI.");
			saveppi.close();
			return null;
		}
		
		getPPI = new Dbgetppi();
		getPPI.open();
		
		ArrayList<OneSentence> array = getPPI.getPPIRecord(PMID);
		
		getPPI.close();
		
		return array;
	}
	
}
