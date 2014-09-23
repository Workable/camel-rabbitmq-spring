package com.workable.camel.component.vo;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class TestMessage implements Serializable{

	@JsonProperty
	private String message;
	@JsonProperty
	private Long timestamp;

	public TestMessage(String message, Long timestamp) {
		this.message = message;
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
}
