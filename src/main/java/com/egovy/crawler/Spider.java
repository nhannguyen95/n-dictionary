package com.egovy.crawler;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Spider {
	private Set<String> pagesVisited = new HashSet<String>();
	private List<String> pagesToVisit = new LinkedList<String>();
	
	public Map<String, String> results;
	private int max_pages_to_reach;
	private int word_radius;
	
	public Spider(int max_pages_to_reach, int word_radius) {
		this.results = new HashMap<String, String>();
		this.max_pages_to_reach = max_pages_to_reach;
		this.word_radius = word_radius;
	}
	
	public void search(String url, String searchWord) {
		
		pagesVisited.clear();
		pagesToVisit.clear();
		
		while(this.pagesVisited.size() < this.max_pages_to_reach) {
			String currentUrl;
			SpiderLeg leg = new SpiderLeg(this.word_radius);
			if (this.pagesToVisit.isEmpty()) {
				currentUrl = url;
				this.pagesVisited.add(url);
			}
			else {
				currentUrl = this.nextUrl();
			}
			leg.crawl(currentUrl);
			String paragraph = leg.searchForWord(searchWord);
			if (paragraph.length() != 0) {
//				System.out.println(currentUrl);
//				System.out.println(paragraph);
//				System.out.println();
				this.results.put(paragraph, currentUrl);
			}
			this.pagesToVisit.addAll(leg.getLinks());
//			System.out.println(String.format("**Done** Visited %s web page(s)", this.pagesVisited.size()));
//			System.out.println();
		}
	}
	
	private String nextUrl() {
		String nextUrl;
		do {
			nextUrl = this.pagesToVisit.remove(0);
		} while(pagesVisited.contains(nextUrl));
		this.pagesVisited.add(nextUrl);
		return nextUrl;
	}
	
}
