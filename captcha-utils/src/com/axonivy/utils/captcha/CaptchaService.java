package com.axonivy.utils.captcha;

import net.logicsquad.nanocaptcha.audio.AudioCaptcha;
import net.logicsquad.nanocaptcha.image.ImageCaptcha;

public interface CaptchaService {

	ImageCaptcha generateImageCaptcha(ImageCaptchaBuilder imageCaptchaBuilder);
	
	AudioCaptcha generateVoiceCaptcha(boolean soundNoise);

	boolean validateCaptcha(String input, String captcha);

}
