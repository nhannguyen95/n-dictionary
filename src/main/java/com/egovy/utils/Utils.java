package com.egovy.utils;

import java.util.ArrayList;

import com.egovy.wiki.WikiData;

public class Utils {
	
	private static final String PREFIX = "<C><F><I><N><Q>";
	private static final String SUFFIX = "</Q></N></I></F></C>";
	
	public static String getTranscription(String wdetail) {
		String t = wdetail.substring(PREFIX.length(), wdetail.indexOf("<br />"));
		if (t.contains("/")) return "/" + t.split("/")[1] + "/";
		return "";
	}
	
	public static String htmlFormat(String wdetail) {
		
		wdetail = wdetail.substring(wdetail.indexOf("<br />") + "<br />".length());
		
		// </Q></N></I></F></C>
		wdetail = wdetail.substring(0, wdetail.length() - SUFFIX.length());
		
		String[] lines = wdetail.split("<br />");
		
		ArrayList<String> o_lines = new ArrayList<String>();
		boolean withPreMeaning = false;
		for(String line : lines) {
			if (line.length() == 0) continue;
			
			if (line.charAt(0) == '*') {
				line = line.substring(2);
				o_lines.add("<div class=\"bg-grey bold font-large m-top20\"><span>" + line + "</span></div>");
				withPreMeaning = false;
			}
			else if (line.charAt(0) == '-') {
				line = line.substring(2);
				if (!withPreMeaning) 
					o_lines.add("<div class=\"green bold margin25 m-top15\">" + line + "</div>");
				else 
					o_lines.add("<div class=\"grey bold margin25 m-top15\">" + line + "</div>");
			}
			else if (line.charAt(0) == '=') {
				line = line.substring(1);
				String[] ev = line.split("\\+");
				o_lines.add("<div class=\"color-light-blue margin25 m-top15\">" + ev[0] + "</div>");
				if (ev.length == 2) {
					ev[1] = ev[1].substring(1);
					o_lines.add("<div class=\"margin25\">" + ev[1] + "</div>");
				}
			}
			else if (line.charAt(0) == '!') {
				line = line.substring(1);
				o_lines.add("<div class=\"bold dot-blue m-top15\">" + line + "</div>");
				withPreMeaning = true;
			}
			
			else o_lines.add(line);
		}
		
		
		String output = "";
		for(String line : o_lines) output += line;
		
		return output;
	}
	
	public static String htmlCrawlerFormat(ArrayList<String> result, String word) {
		String output = "<table style=\"width:600px;\"><tbody>";
		word = word.toLowerCase();
		
		if (result.size() == 0) {
			output = "<tr><td>No result found!</td></tr>";
			return output;
		}
		
		for(String r : result) {
			int i = r.indexOf("...");
			String source = r.substring(0, i);
			String example = r.substring(i, r.length());
			
			output += "<tr><td>";
			
			int j = example.toLowerCase().indexOf(word);
			String prefix_e = example.substring(0, j);
			prefix_e += "<span style=\"background-color:#fffcb0;\">";
			String suffix_e = example.substring(j + word.length(), example.length());
			suffix_e = "</span>" + suffix_e;
			example = prefix_e + word + suffix_e;
			output = output + "<div>" + example + "</div>";
			
			output += "<div>";
			output += "<img width=\"12\" height=\"12\" src=\"https://cdn4.iconfinder.com/data/icons/web-links/512/41-512.png\"/ alt=\"\" style=\"vertical-align:middle;margin-bottom:2px;\"/>";
			output = output + "<a href=\"" + source + "\" style=\"font-size:11px;font-style:italic;color:#777;\" target=\"_blank\"> " 
					+ source + "</a>";
			output += "</div>";
			output += "</td></tr>";
			
		}
		
		output += "</tbody></table>";
		return output;
	}
	
	public static String htmlWikiDataFormat(WikiData wikiData) {
		if (wikiData.articleNames.size() == 0) return "Not found!";
		
		String output = "<div class=\"wikipedia_result\">";
		
		for(int i = 0; i < wikiData.articleNames.size(); i++) {
			String articleName = wikiData.articleNames.get(i);
			String introduction = wikiData.introduction.get(i);
			String link = wikiData.links.get(i);
			
			output += "<a target=\"_blank\" href=\"" + link + "\">";
			output += "<span class=\"color-blue\">" + articleName + "</span>";
			output += "<br>";
			output += "<span>" + introduction + "</span>";
			output += "</a><br><br>";
		}
		
		return output + "</div>";
	}
	
    static final long MOD = 1000000000 + 7;
    static final long BASE = 26;
    
    public static long getHashCode(String s) {
        long hashCode = new Long(0);
        for(int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            hashCode = hashCode * BASE + (c - 'a' + 1);
            hashCode %= MOD;
        }
        return hashCode;
    }
}
