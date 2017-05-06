package com.egovy.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.egovy.domain.Word;

public interface WordRepository extends CrudRepository<Word, Integer> {
	
	List<Word> findByWord(String word); 
}
