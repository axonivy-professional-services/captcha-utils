package com.axonivy.utils.captcha.enums;

import java.awt.Font;

public enum FontStyle {
	PLAIN(Font.PLAIN), 
	BOLD(Font.BOLD), 
	ITALIC(Font.ITALIC);

	private final int style;

	public int getStyle() {
		return style;
	}

	private FontStyle(int style) {
		this.style = style;
	}
}
