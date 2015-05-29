package com.ppi.ner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import abner.Tagger;

/*
 * ���ڴ��ڵ����⣺
 * 1.��ȡ�����ĵ����ʿ��ܲ���׼ȷ������ ���� �� �����������"("
 */

public class Recognition {

	final int BIOCREATIVE = 1;
	
	Tagger tagger;
	String ab;
	ArrayList<String> allSen;   //���������ѱ�ǵľ��ӣ������������������ʵľ��� �� ���������ʵľ��ӣ����ӿ���ֻ����һ�������ʣ�
	ArrayList<String> allProtein; //�������� �ѱ�ǵ�: ���������ʵľ��� ������ֻ�����������ϵ����ʣ�
	ArrayList<String> replaceProtein; //�������� ��ProteinX ��ʽ�滻�˵����ʵľ��� ������ֻ�����������ϵ����ʣ�
	ArrayList<String> originSen;  //�������� ���������� ��δ��ǵľ��� ������ֻ�����������ϵ����ʣ�
	ArrayList<HashMap<String, String>> proteinName;  //����ÿһ�� ���������ʵľ��� ProteinX ��Ӧ�� ����������
	
	public Recognition(String artical) {
		// TODO Auto-generated constructor stub
		tagger = new Tagger(BIOCREATIVE);
		ab = artical;
		generateSentence();
	}
	
	public void generateSentence(){
		//�� ժҪ ���е����ʵı�ǣ����������о���
		allSen = new ArrayList<String>();
		
		System.out.println("\n################# generateSentence: All Sentence #####################");
		//System.out.println(tagger.tagSGML(ab));
		tagger.tagSGML(ab);
		
		Vector segs = tagger.getSegments(ab);
		StringBuffer tmp = new StringBuffer();
		
		for (int i=0; i<segs.size(); i++) {
			String[][] sen = (String[][])segs.get(i);
			for (int j=0; j<sen[0].length; j++) {
			if (sen[1][j].equals("O"))
				tmp.append(sen[0][j]+" ");
			else 
				tmp.append("<"+sen[1][j]+"> "+sen[0][j]+" </"+sen[1][j]+"> ");
			}
			allSen.add(tmp.toString());
			tmp.setLength(0);
		}
		
		for(int i=0; i<allSen.size(); i++){
			System.out.println("Sentence"+(i+1)+":\n"+allSen.get(i).toString());
		}

		removeNonProtein();
		replaceProtein();
	}
	
	//��ȡ���� PROTEIN��ǩ �ľ��ӣ�������
	public void removeNonProtein(){
		allProtein = new ArrayList<String>();
		originSen = new ArrayList<String>();
		String tmp;
		
		System.out.println("\n########################### removeNonProtein #####################################");
		
		for(int i=0; i<allSen.size(); i++){
			tmp = null;
			
//			int position;
//			position = allSen.get(i).toString().indexOf("<PROTEIN>");
			if( allSen.get(i).toString().contains("<PROTEIN>") ){
				/************ɾ��ֻ����һ��protein�ľ���**************/
				String sp[] = allSen.get(i).toString().split(" ");
				int pc = 0;
				for(int j=0; j<sp.length; j++){
					if(sp[j].equals("<PROTEIN>")){
						pc++;
					}
				}
				
				if(pc>1){
					String tmp2 = allSen.get(i).toString();
					if( tmp2.contains("n 't") ){
						tmp2 = tmp2.replace("n 't", "n't");
					}
					
					allProtein.add(tmp2);
					System.out.println("Sentence"+(i+1)+":\n"+tmp2);
					
					//�������� ���������� ��δ��ǵľ���
					tmp = tmp2.replace("<PROTEIN> ", "");
					tmp = tmp.replace(" </PROTEIN>", "");
					originSen.add(tmp);
				}
			}
		}
	}
	
	//���������滻��һ���̶����ȵ����� ProteinX �������������
	public void replaceProtein(){
		replaceProtein = new ArrayList<String>();
		proteinName = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> tHash = null;
		String name = null;
		
		System.out.println("\n########################### replaceProtein #####################################");
		
		String rule = "<PROTEIN>.+?</PROTEIN>";
		Pattern p = Pattern.compile(rule);
		Matcher m;
		
		char aI[] = {'A','B','C','D','E','F','G','H','I','J','K'}; 
				
		for(int i=0; i<allProtein.size(); i++){
			tHash = new HashMap<String, String>();
			
			m = p.matcher(allProtein.get(i).toString());
			ArrayList<String> proteinTag = new ArrayList<String>();
			
			while(m.find()){
				proteinTag.add(m.group(0));
			}
			
			System.out.println("\n################## proteinTag:");
			
			String tmp = allProtein.get(i).toString();
			
			for(int j=0; j<proteinTag.size(); j++){
				System.out.println(proteinTag.get(j).toString());
				tmp = tmp.replace(proteinTag.get(j).toString(), "Protein"+aI[j]);
				
				name = proteinTag.get(j).toString().replace("<PROTEIN> ", "");
				name = name.replace(" </PROTEIN>", "");
				tHash.put("Protein"+aI[j], name);
			}
			
			proteinName.add(tHash);
			replaceProtein.add(tmp);
			
			System.out.println("Sentence"+(i+1)+":\n"+replaceProtein.get(i).toString());
			
		}
		
	}
	
	public ArrayList<String> getAllSentence(){
		return allSen;
	}
	
	public ArrayList<String> getAllProtein(){
		return allProtein;
	}
	
	public ArrayList<String> getReplaceProtein(){
		return replaceProtein;
	}
	
	public ArrayList<String> getOriginSentence(){
		return originSen;
	}
	
	public ArrayList<HashMap<String, String>> getProteinName(){
		return proteinName;
	}
	
	public void printProterin(){
		char aI[] = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S'}; 
		
		System.out.println("\n########################### Protein Name Map #####################################");
		
		for(int i=0; i<proteinName.size();i++){
			for(int j=0; j<proteinName.get(i).size(); j++){
				System.out.println( "Protein"+aI[j] +"  "+ proteinName.get(i).get("Protein"+aI[j]).toString());
			}
		}
	}
	
}
