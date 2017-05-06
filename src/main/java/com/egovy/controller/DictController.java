package com.egovy.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.egovy.crawler.WebCrawler;
import com.egovy.domain.Word;
import com.egovy.sementic.SPARQLGenerator;
import com.egovy.sementic.SearchAnalyser;
import com.egovy.service.WordService;
import com.egovy.utils.Utils;
import com.egovy.wiki.WikiData;
import com.egovy.sementic.Connector;

@Controller
public class DictController {
	
	@Autowired
	private WordService wordService;
	
	@GetMapping("/home")
	public String welcome() {
		return "home";
	}
	
	@GetMapping("/home/search")
	public String search(@RequestParam("q") String q, Model model) {
		if (q.equals("")) {
			return "redirect:/home";
		}
		
		System.out.println("QUERY: " + q);
		
		List<Word> words = wordService.search(q);
		if (words.size() > 0) {
			
			// from database
			words.get(0).setTranscription(Utils.getTranscription(words.get(0).getDetail()));
			words.get(0).setDetail(Utils.htmlFormat(words.get(0).getDetail()));
			model.addAttribute("word", words.get(0));
			
			// crawler
			WebCrawler webCrawler = new WebCrawler(words.get(0).getWord());
			ArrayList<String> result = webCrawler.search();
			String decorated_result = Utils.htmlCrawlerFormat(result, words.get(0).getWord());
			model.addAttribute("webcrawler", decorated_result);
			
		} else {
			
			// sementic
			SearchAnalyser searchAnalyser = new SearchAnalyser(q);
			SPARQLGenerator sparqlGenerator = new SPARQLGenerator(searchAnalyser);
			if (sparqlGenerator.b)
				model.addAttribute("ans", Connector.getInstance().getQueryResult(sparqlGenerator.getQuery()));
			else model.addAttribute("ans", "Not found!");
		}
		
		// wikidata
		try {
			WikiData wikiData = new WikiData(q);
			wikiData.search();
			String decorated_result = Utils.htmlWikiDataFormat(wikiData);
			model.addAttribute("wikidata", decorated_result);
		} catch (IOException | JSONException e) {
			e.printStackTrace();
		}
		
		
		return "lookup";
	}
}
