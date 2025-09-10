package com.axonivy.utils.captcha.enums;

public enum NoiseType {
	STRAIGHTLINE("Straight line"), 
	CURVEDLINE("Curved line"), 
	SALTANDPEPPER("Salt and pepper"), 
	GAUSSIAN("Gaussian");

	private final String text;

	public String getText() {
		return text;
	}

	private NoiseType(String text) {
		this.text = text;
	}
}
