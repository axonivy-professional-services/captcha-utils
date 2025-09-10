package com.axonivy.utils.captcha;

import com.axonivy.utils.captcha.enums.BackgroundType;
import com.axonivy.utils.captcha.enums.ContentType;
import com.axonivy.utils.captcha.enums.NoiseType;
import com.axonivy.utils.captcha.model.Background;
import com.axonivy.utils.captcha.model.Content;
import com.axonivy.utils.captcha.model.Noise;

import net.logicsquad.nanocaptcha.image.ImageCaptcha;

public class ImageCaptchaBuilder {

	private int width = 0;
	private int height = 0;
	private Content content = new Content();
	private Noise noise = new Noise();
	private Background background = new Background();

	public ImageCaptchaBuilder() {
		// default value
		width = 150;
		height = 50;
		background.setType(BackgroundType.FLATCOLOR);
		noise.setType(NoiseType.CURVEDLINE);
		content.setType(ContentType.NUMBERS);
	}

	public ImageCaptchaBuilder withDimensions(int width, int height) {
		this.width = width;
		this.height = height;
		return this;
	}

	public ImageCaptchaBuilder withContent(Content content) {
		this.content = content;
		return this;
	}

	public ImageCaptchaBuilder withNoise(Noise noise) {
		this.noise = noise;
		return this;
	}

	public ImageCaptchaBuilder withBackground(Background background) {
		this.background = background;
		return this;
	}

	public ImageCaptcha build() {
		return new ImageCaptcha.Builder(width, height)
				.addContent(content.getContentProducer(), content.getDefaultWordRenderer())
				.addBackground(background.getBackgroundProducer())
				.addNoise(noise.getNoiseProducer())
				.build();
	}
}
