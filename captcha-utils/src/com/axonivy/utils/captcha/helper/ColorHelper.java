package com.axonivy.utils.captcha.helper;

import static java.util.Optional.empty;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import java.awt.Color;
import java.util.Objects;
import java.util.Optional;

public class ColorHelper {

	public static Optional<Color> hexToColor(String value) {
		String hex = Objects.toString(value, EMPTY).replace("#", "");
		
		if (hex.length() == 8) {
			int r = Integer.valueOf(hex.substring(0, 2), 16);
			int g = Integer.valueOf(hex.substring(2, 4), 16);
			int b = Integer.valueOf(hex.substring(4, 6), 16);
			int a = Integer.valueOf(hex.substring(6, 8), 16);
			return Optional.of(new Color(r, g, b, a));
		}
		return empty();
	}
}
