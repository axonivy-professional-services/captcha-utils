package com.axonivy.utils.captcha.enums;

public enum BackgroundType {
	FLATCOLOR("Solid colour background"), 
	GRADIATED("Gradiated background"), 
	SQUIGGLES("Squiggles background"),
	TRANSPARENT("Transparent background");

	private final String text;

	public String getText() {
		return text;
	}

	private BackgroundType(String text) {
		this.text = text;
	}
}
