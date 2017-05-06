package com.egovy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egovy.domain.Word;
import com.egovy.repository.WordRepository;

@Service
public class WordServiceImpl implements WordService {

	@Autowired
	private WordRepository wordRepository;
	
	@Override
	public List<Word> search(String word) {
		return wordRepository.findByWord(word);
	}

}
