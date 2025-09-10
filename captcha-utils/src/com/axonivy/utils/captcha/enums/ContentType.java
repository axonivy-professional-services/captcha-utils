package com.axonivy.utils.captcha.enums;

public enum ContentType {
	ARABIC("Arabic alphabet"), 
	LATIN("Latin alphabet"), 
	CHINESE("Chinese alphabet"), 
	NUMBERS("Series of numbers"),
	STATIC_LIST("Static list");

	private final String text;

	public String getText() {
		return text;
	}

	private ContentType(String text) {
		this.text = text;
	}
}
