package com.axonivy.utils.captcha.internal;

import static org.apache.commons.lang3.ObjectUtils.allNotNull;

import java.util.Locale;
import com.axonivy.utils.captcha.CaptchaService;
import com.axonivy.utils.captcha.ImageCaptchaBuilder;
import net.logicsquad.nanocaptcha.audio.AudioCaptcha;
import net.logicsquad.nanocaptcha.audio.noise.RandomNoiseProducer;
import net.logicsquad.nanocaptcha.audio.producer.RandomNumberVoiceProducer;
import net.logicsquad.nanocaptcha.image.ImageCaptcha;

public class AdvancedCaptchaService implements CaptchaService {

	@Override
	public ImageCaptcha generateImageCaptcha(ImageCaptchaBuilder imageCaptchaBuilder) {
		return imageCaptchaBuilder.build();
	}

	@Override
	public AudioCaptcha generateVoiceCaptcha(boolean soundNoise) {
		AudioCaptcha.Builder builder = new AudioCaptcha.Builder().addContent()
				.addVoice(new RandomNumberVoiceProducer(Locale.US));

		if (soundNoise) {
			builder.addNoise(new RandomNoiseProducer());
		}
		return builder.build();
	}

	@Override
	public boolean validateCaptcha(String input, String captcha) {

		if (allNotNull(input, captcha) && captcha.contains(input) && !input.isEmpty()) {
			return true;
		}
		return false;
	}
}
