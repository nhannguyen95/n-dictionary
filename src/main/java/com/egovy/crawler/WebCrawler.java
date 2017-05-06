package com.egovy.crawler;

import java.util.ArrayList;
import java.util.Map;

public class WebCrawler {
	
	public static final String[] SEARCH_LIST 
	= {  "http://reuters.com/", 
		 "http://edition.cnn.com/", 
		 "http://foxnews.com/",
		 "http://washingtonpost.com/",
		 "http://nbcnews.com/",
		 "http://theguardian.com/international/",
		 "http://abcnews.go.com/",
		 "http://bbc.com/news",
		 "http://yahoo.com/news/"
		 };
	public static final int NR_OF_SEARCH_RESULT = 6;
	public static final int MAX_PAGES_TO_REACH = 7;
	public static final int WORD_RADIUS = 22;
	public static final String NOT_FOUND = "No result found!";
	
	private String searchWord;
	
	public WebCrawler(String word) {
		this.searchWord = word;
	}
	
	public ArrayList<String> search() {
		ArrayList<String> result = new ArrayList<String>();
		
		Spider spider = new Spider(MAX_PAGES_TO_REACH, WORD_RADIUS);
		for(String site : SEARCH_LIST) {
			if (spider.results.size() >= NR_OF_SEARCH_RESULT) break;
			spider.search(site, this.searchWord);
		}
		
		if (spider.results.size() == 0) System.out.println(NOT_FOUND);
		else {
			for (Map.Entry<String, String> entry : spider.results.entrySet()) {
			    String example = entry.getKey();
			    String url = entry.getValue();
			    result.add(url + example);
			}
		}
		
		return result;
	}
}
