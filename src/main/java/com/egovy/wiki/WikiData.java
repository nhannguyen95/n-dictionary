package com.egovy.wiki;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

public class WikiData {
	
	private String target;
	public ArrayList<String> articleNames;
	public ArrayList<String> introduction;
	public ArrayList<String> links;
	
	public WikiData(String target) throws IOException {
		this.target = target.toLowerCase();
		this.articleNames = new ArrayList<String>();
		this.introduction = new ArrayList<String>();
		this.links = new ArrayList<String>();
	}
	
	public void search() throws IOException, JSONException {
		analyze();
		
		for(int i = 0; i < articleNames.size(); i++) {
			System.out.println(articleNames.get(i));
			System.out.println(links.get(i));
			System.out.println(introduction.get(i));
			System.out.println();
		}
	}
	
	private void analyze() throws IOException, JSONException {
		URL url = new URL("https://vi.wikipedia.org/w/api.php?action=opensearch&search=" + target.replaceAll(" ", "%20"));
		String text = "";
		try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream()))) {
		    String line = null;
		    while (null != (line = br.readLine())) {
		        line = line.trim();
		        if (true) {
		            text += line;
		        }
		    }
		}
		
		JSONArray jsonArray = new JSONArray(text);
		JSONArray jsonArray_articleName = (JSONArray) jsonArray.get(1);
		JSONArray jsonArray_introduction = (JSONArray) jsonArray.get(2);
		JSONArray jsonArray_links = (JSONArray) jsonArray.get(3);
		for(int i = 0; i < jsonArray_articleName.length(); i++) {
			String articleName = jsonArray_articleName.getString(i).toLowerCase();
			if (articleName.startsWith(this.target)) {
				articleNames.add(jsonArray_articleName.getString(i));
				introduction.add(jsonArray_introduction.getString(i));
				links.add(jsonArray_links.getString(i));
			}
		}
	}	
}
