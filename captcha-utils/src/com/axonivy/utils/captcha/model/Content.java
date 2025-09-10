package com.axonivy.utils.captcha.model;

import static java.util.Optional.ofNullable;
import static org.apache.commons.collections4.ListUtils.defaultIfNull;
import static org.apache.commons.collections4.ListUtils.emptyIfNull;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.axonivy.utils.captcha.enums.ContentType;
import com.axonivy.utils.captcha.enums.FontStyle;
import com.axonivy.utils.captcha.helper.ColorHelper;

import net.logicsquad.nanocaptcha.content.ArabicContentProducer;
import net.logicsquad.nanocaptcha.content.ChineseContentProducer;
import net.logicsquad.nanocaptcha.content.ContentProducer;
import net.logicsquad.nanocaptcha.content.FiveLetterFirstNameContentProducer;
import net.logicsquad.nanocaptcha.content.LatinContentProducer;
import net.logicsquad.nanocaptcha.content.NumbersContentProducer;
import net.logicsquad.nanocaptcha.image.renderer.DefaultWordRenderer;
import net.logicsquad.nanocaptcha.image.renderer.WordRenderer;

public class Content {

	private ContentType type;
	private Integer length;
	private List<String> colors;
	private FontStyle style;
	private Integer fontSize;

	public Content() {
		this.colors = new ArrayList<>();
	}

	public ContentType getType() {
		return type;
	}

	public void setType(ContentType type) {
		this.type = type;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public List<String> getColors() {
		return colors;
	}

	public void setColors(List<String> colors) {
		this.colors = colors;
	}

	public FontStyle getStyle() {
		return style;
	}

	public void setStyle(FontStyle style) {
		this.style = style;
	}

	public Integer getFontSize() {
		return fontSize;
	}

	public void setFontSize(Integer fontSize) {
		this.fontSize = fontSize;
	}

	public ContentProducer getContentProducer() {
		int length = ofNullable(this.length).orElse(6);
		
		switch (type) {
			case LATIN:
				return new LatinContentProducer(length);
				
			case ARABIC:
				return new ArabicContentProducer(length);
				
			case NUMBERS:
				return new NumbersContentProducer(length);
				
			case CHINESE:
				return new ChineseContentProducer(length);
				
			case STATIC_LIST:
				return new FiveLetterFirstNameContentProducer();
				
			default:
				return new LatinContentProducer();
		}
	}

	public WordRenderer getDefaultWordRenderer() {
		int fontStyle = ofNullable(this.style).map(FontStyle::getStyle).orElse(Font.PLAIN);
		int fontSize = ofNullable(this.fontSize).orElse(26);
		
		List<Color> colors = emptyIfNull(this.colors).stream().map(ColorHelper::hexToColor)
				.filter(Optional::isPresent).map(Optional::get).toList();

		colors = defaultIfNull(colors, List.of(Color.WHITE));

		return new DefaultWordRenderer.Builder().randomColor(colors).font(new Font(Font.SERIF, fontStyle, fontSize))
				.build();
	}
}
