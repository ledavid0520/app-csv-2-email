package com.csvemail.project.model;

import java.util.List;

public class Email {
	
	private String from;
	private List<String> to;
	private String subject;
	private String body;
	
	public Email() {
		super();
	}

	public Email(String from, List<String> to, String subject, String body) {
		super();
		this.from = from;
		this.to = to;
		this.subject = subject;
		this.body = body;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public List<String> getTo() {
		return to;
	}

	public void setTo(List<String> to) {
		this.to = to;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
	
}
