package com.ppi.preprocess;

public class Preprocessor {
	String PMID;
	String TI;
	String AB;
		
	public Preprocessor(String t){
		processText(t);
	}
	
	public void processText(String t){
		String test[] = t.split("\r\n");
		System.out.println("Process:");
		int turn = 0; //PMID ： 0 ; TI : 1 ; AB ： 2
		boolean turnForTI = false, turnForAB = false; 
		StringBuffer TIbuffer = new StringBuffer();
		StringBuffer ABbuffer = new StringBuffer();
		
		for(int i=0; i<test.length; i++){
			//遇到空的跳过
			if(test[i].equals("")) continue;
			//turn=0 -> 要搜索出PMID
			if(turn == 0){
				if(test[i].startsWith("PMID")){
					if(test[i].length() == 14){
						PMID = test[i].substring(6, 13);
					}
					turn++;
				}
			}else if(turn == 1){
				if(test[i].startsWith("TI")){
					TIbuffer.append(test[i].substring(6));
					turnForTI = true;
					continue;
				}
				if(turnForTI){
					if(test[i].startsWith("      ")){
						TIbuffer.append(test[i].substring(5));
					}else{
						turn++;
					}
				}
			}else if(turn == 2){
				if(test[i].startsWith("AB")){
					ABbuffer.append(test[i].substring(6));
					turnForAB = true;
					continue;
				}
				if(turnForAB){
					if(test[i].startsWith("      ")){
						ABbuffer.append(test[i].substring(5));
					}else{
						turn++;
					}
				}
			}else {
				break;
			}
			
		}

		if(null == PMID){
			return;
		}
		
		TI = TIbuffer.toString();
		AB = ABbuffer.toString();
		
		if(TI=="" | AB==""){
			return;
		}
		
		System.out.println(PMID);
		System.out.println(TI);
		System.out.println(AB);
		
	}
		
	public String getPMID(){
		return PMID;
	}
	
	public String getAB(){
		return AB;
	}
	
	
}
