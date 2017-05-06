package com.egovy.service;

import java.util.List;

import com.egovy.domain.Word;

public interface WordService {
	
	List<Word> search(String word);
	
}
