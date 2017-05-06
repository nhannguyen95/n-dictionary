package com.egovy.crawler;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SpiderLeg {
	
	 // We'll use a fake USER_AGENT so the web server thinks the robot is a normal web browser.
    private static final String USER_AGENT =
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";
    
	private List<String> links = new LinkedList<String>();
	private Document htmlDocument;
	private int word_radius;
	
	public SpiderLeg(int word_radius) {
		this.word_radius = word_radius;
	}
	
	public boolean crawl(String url) {
		try {
			Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
			Document htmlDocument = connection.get();
			this.htmlDocument = htmlDocument;
			if (connection.response().statusCode() == 200) {
				// 200 is the HTTP OK status code
                // indicating that everything is great.
//				System.out.println("Received web page at " + url);
			}
			
			if (!connection.response().contentType().contains("text/html")) {
//				System.out.println("**Failure** Retrieved something other than HTML");
                return false;
			}
			Elements linksOnPage = htmlDocument.select("a[href]");
//			System.out.println("Found (" + linksOnPage.size() + ") links");
			
			for(Element link : linksOnPage)
				this.links.add(link.absUrl("href"));
			
			return true;
		} catch(IOException ioe) {
            System.out.println("Error in out HTTP request " + ioe);
            return false;
		}
	}
	
	public String searchForWord(String word) {
		if (this.htmlDocument == null) {
			System.out.println("ERROR! Call crawl() before performing analysis on the document");
            return "";
		}
//		System.out.println("Searching for the word " + word + "...");
		String bodyText = this.htmlDocument.body().text();
		String paragraph = "";
		int occur_id = bodyText.toLowerCase().indexOf(word.toLowerCase());
		if (occur_id != -1) {
			String prefix = bodyText.substring(0, occur_id);
			String suffix = bodyText.substring(occur_id + word.length(), bodyText.length());
			
			int cntSpace = 0;
			int pos = 0;
			for(int i = prefix.length() - 1; i >= 0; i--)
				if (prefix.charAt(i) == ' ') { 
					cntSpace++;
					pos = i + 1;
					if (cntSpace == this.word_radius) break;
				}
			prefix = prefix.substring(pos, prefix.length());
			
			cntSpace = 0;
			pos = suffix.length();
			for(int i = 0; i < suffix.length(); i++)
				if (suffix.charAt(i) == ' ') { 
					cntSpace++;
					pos = i;
					if (cntSpace == this.word_radius) break;
				}
			suffix = suffix.substring(0, pos);
			
			paragraph = "..." + prefix + word + suffix + "...";
			
		}
		return paragraph;
	}
	
	public List<String> getLinks() {
		return this.links;
	}
}
