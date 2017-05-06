package com.egovy.sementic;

import vn.hus.nlp.tokenizer.VietTokenizer;

public class SearchAnalyser {
	 private String s = "";
	    private String target = "";
	    public boolean askAboutSynonym = false;
	    public boolean askAboutAntonym = false;
	    public boolean askAboutWordClass = false;
	    public boolean askAboutNounForm = false;
	    public boolean askAboutVerbForm = false;
	    public boolean askAboutAdjForm = false;
	    public boolean askAboutAdvForm = false;
	    public boolean askAboutExample = false;
	    public boolean askAboutPast = false;
	    public boolean askAboutPresent = false;
	    public boolean askAboutFuture = false;
	    public boolean askAboutGeneral = false;
	    private String[] tokens;
	    
	    public SearchAnalyser(String s) {
	        VietTokenizer vietTokenizer = new VietTokenizer();
	        this.s = vietTokenizer.segment(s.toLowerCase());
	        this.tokens = this.s.split(" ");
	        analyse();
	    }
	    
	    private void analyse() {
	        analyseSynonym();
	        if (!askAboutSynonym) analyseAntonym();
	        if (!askAboutAntonym) analyseWordClass();
	        if (!askAboutWordClass) analyseNounForm();
	        if (!askAboutNounForm) analyseVerbForm();
	        if (!askAboutVerbForm) analyseAdjForm();
	        if (!askAboutAdjForm) analyseAdvForm();
	        if (!askAboutAdvForm) analyseExample(); // past simple
	        if (askAboutExample) {
	            askAboutPast = s.contains("quá_khứ");
	            if (!askAboutPast) askAboutPresent = s.contains("hiện_tại");
	            if (!askAboutPresent) askAboutFuture = s.contains("tương_lai");
	            if (!askAboutFuture) askAboutGeneral = true;
	        }
	    }
	    
	    // đồng nghĩa, đồng nghĩa với, đồng nghĩa của 
	    private void analyseSynonym() {
	        for(int i = 0; i < tokens.length - 1; i++) {
	            String curw = tokens[i];
	            if (curw.compareTo("đồng_nghĩa") == 0) {
	                askAboutSynonym = true;
	                if (tokens[i+1].compareTo("của") != 0 && tokens[i+1].compareTo("với") != 0) 
	                	target = tokens[i+1];
	                else target = tokens[i+2];
	                return;
	            }
	        }
	    }
	    
	    // trái nghĩa, trái nghĩa với, trái nghĩa của 
	    private void analyseAntonym() {
	        for(int i = 0; i < tokens.length - 1; i++) {
	            String curw = tokens[i];
	            if (curw.compareTo("trái_nghĩa") == 0) {
	            	askAboutAntonym = true;
	                if (tokens[i+1].compareTo("của") != 0 && tokens[i+1].compareTo("với") != 0) 
	                	target = tokens[i+1];
	                else target = tokens[i+2];
	                return;
	            }
	        }
	    }
	    
	    // từ loại, từ loại của  
	    private void analyseWordClass() {
	        for(int i = 0; i < tokens.length - 1; i++) {
	            String curw = tokens[i];
	            if (curw.compareTo("từ_loại") == 0 ) {
	            	askAboutWordClass = true;
	                if (tokens[i+1].compareTo("của") != 0) 
	                	target = tokens[i+1];
	                else target = tokens[i+2];
	                return;
	            }
	        }
	    }
	    
	    // danh từ, danh từ của 
	    private void analyseNounForm() {
	        for(int i = 0; i < tokens.length - 1; i++) {
	            String curw = tokens[i];
	            if (curw.compareTo("danh_từ") == 0 ) {
	            	askAboutNounForm = true;
	                if (tokens[i+1].compareTo("của") != 0) 
	                	target = tokens[i+1];
	                else target = tokens[i+2];
	                return;
	            }
	        }
	    }
	    
	    // động từ, động từ của 
	    private void analyseVerbForm() {
	        for(int i = 0; i < tokens.length - 1; i++) {
	            String curw = tokens[i];
	            if (curw.compareTo("động_từ") == 0 ) {
	            	askAboutVerbForm = true;
	                if (tokens[i+1].compareTo("của") != 0) 
	                	target = tokens[i+1];
	                else target = tokens[i+2];
	                return;
	            }
	        }
	    }
	    
	    // tính từ, tính từ của 
	    private void analyseAdjForm() {
	        for(int i = 0; i < tokens.length - 1; i++) {
	            String curw = tokens[i];
	            if (curw.compareTo("tính_từ") == 0 ) {
	            	askAboutAdjForm = true;
	                if (tokens[i+1].compareTo("của") != 0) 
	                	target = tokens[i+1];
	                else target = tokens[i+2];
	                return;
	            }
	        }
	    }
	    
	    // trạng từ, trạng từ  
	    private void analyseAdvForm() {
	        for(int i = 0; i < tokens.length - 1; i++) {
	            String curw = tokens[i];
	            if (curw.compareTo("trạng_từ") == 0 ) {
	            	askAboutAdvForm = true;
	                if (tokens[i+1].compareTo("của") != 0) 
	                	target = tokens[i+1];
	                else target = tokens[i+2];
	                return;
	            }
	        }
	    }
	    
	    // ví dụ, ví dụ của từ, ví dụ về từ, ví dụ cho từ 
	    private void analyseExample() {
	        for(int i = 0; i < tokens.length - 1; i++) {
	            String curw = tokens[i];
	            if (curw.compareTo("ví_dụ") == 0 ) {
	            	askAboutExample = true;
	            	int j = i + 1;
	            	while(tokens[j].compareTo("của") == 0 || tokens[j].compareTo("về") == 0
	            			|| tokens[j].compareTo("cho") == 0 || tokens[j].compareTo("từ") == 0)
	            		j++;
	            	target = tokens[j];
	                return;
	            }
	        }
	    }
	    
	    public String getTarget() { return this.target.trim(); }
	    
	    public boolean cannotHanlde() {
	        return !askAboutSynonym &
	                !askAboutAntonym &
	                !askAboutWordClass &
	                !askAboutNounForm &
	                !askAboutVerbForm &
	                !askAboutAdjForm &
	                !askAboutAdvForm &
	                !askAboutExample &
	                !askAboutPast &
	                !askAboutPresent &
	                !askAboutFuture &
	                !askAboutGeneral;
	    }
}
