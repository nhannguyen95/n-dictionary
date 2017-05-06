package com.egovy.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_edict")
public class Word implements Serializable {
	
	@Id
	@Column(name = "idx")
	private int id;
	
	@Column(name = "word")
	private String word;
	
	@Column(name = "detail")
	private String detail;
	
	private String transcription = "";
	
	public Word() {
		super();
	}
	
	public Word(int id, String word, String detail) {
		this.id = id;
		this.word = word;
		this.detail = detail;
	}
	
	public int getId() { return id; }

	public String getWord() { return word; }
	
	public String getDetail() { return detail; }
	
	public String getTranscription() { return transcription; }
	
	public void setDetail(String detail) {
		this.detail = detail;
	}
	
	public void setTranscription(String transcription) {
		this.transcription = transcription;
	}
	
	public void setWord(String word) {
		this.word = word;
	}
}
