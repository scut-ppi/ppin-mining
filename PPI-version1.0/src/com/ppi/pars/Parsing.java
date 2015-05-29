package com.ppi.pars;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import edu.stanford.nlp.process.Tokenizer;
import edu.stanford.nlp.process.TokenizerFactory;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.trees.*;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;

public class Parsing {
	
	String modelName ="edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz";
	LexicalizedParser lp = null;
	String sent = null;
	Tree parseTree = null;
	ArrayList<Tree> treeSet = null;
	
	public Parsing(){
		lp = LexicalizedParser.loadModel(modelName);
		treeSet = new ArrayList<Tree>();
	}
	
	public void setSentence(String sen){
		sent = sen;
	}
	
	public String getSentence(){
		return sent;
	}
	
	public void parseSentence(){
		TokenizerFactory<CoreLabel> tokenizerFactory = PTBTokenizer.factory(new CoreLabelTokenFactory(), "");
	    Tokenizer<CoreLabel> tok = tokenizerFactory.getTokenizer(new StringReader(sent));
	    List<CoreLabel> rawWords2 = tok.tokenize();
	    parseTree = lp.apply(rawWords2);
	    
	    treeSet.add(parseTree);
	    
//	    parseTree.pennPrint();
	    
	}
	
	public ArrayList<Tree> getAllTree(){
		return treeSet;
	}
	
}
