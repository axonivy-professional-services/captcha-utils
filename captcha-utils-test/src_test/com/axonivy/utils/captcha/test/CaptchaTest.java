package com.axonivy.utils.captcha.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.axonivy.utils.captcha.CaptchaService;
import com.axonivy.utils.captcha.ImageCaptchaBuilder;
import com.axonivy.utils.captcha.enums.ContentType;
import com.axonivy.utils.captcha.internal.AdvancedCaptchaService;
import com.axonivy.utils.captcha.model.Content;

import ch.ivyteam.ivy.environment.IvyTest;
import net.logicsquad.nanocaptcha.audio.AudioCaptcha;
import net.logicsquad.nanocaptcha.image.ImageCaptcha;

@IvyTest
public class CaptchaTest {

	private CaptchaService captchaService = new AdvancedCaptchaService();

	@Test
	public void shouldHaveSameDimensions() {
		// Create test data
		int width = 150;
		int height = 50;

		// Test
		ImageCaptchaBuilder imageCaptchaBuilder = new ImageCaptchaBuilder().withDimensions(150, 50);
		ImageCaptcha imageCaptcha = captchaService.generateImageCaptcha(imageCaptchaBuilder);

		// Assertions
		assertEquals(width, imageCaptcha.getImage().getWidth());
		assertEquals(height, imageCaptcha.getImage().getHeight());
	}

	@Test
	public void shouldHaveContentNotNull() {
		// Test
		ImageCaptchaBuilder imageCaptchaBuilder = new ImageCaptchaBuilder();
		ImageCaptcha imageCaptcha = captchaService.generateImageCaptcha(imageCaptchaBuilder);

		// Assertions
		assertNotNull(imageCaptcha.getContent());
	}

	@Test
	public void shouldHaveContentLength() {
		// Create test data
		int length = 6;

		// Test
		Content content = new Content();
		content.setType(ContentType.NUMBERS);
		content.setLength(6);

		ImageCaptchaBuilder imageCaptchaBuilder = new ImageCaptchaBuilder().withContent(content);
		ImageCaptcha imageCaptcha = captchaService.generateImageCaptcha(imageCaptchaBuilder);

		// Assertions
		assertEquals(length, imageCaptcha.getContent().length());
	}
	
	@Test
	public void shouldHaveContentNumber() {
		// Create test data
		Content content = new Content();
		content.setType(ContentType.NUMBERS);

		// Test
		ImageCaptchaBuilder imageCaptchaBuilder = new ImageCaptchaBuilder().withContent(content);
		ImageCaptcha imageCaptcha = captchaService.generateImageCaptcha(imageCaptchaBuilder);

		// Assertions
		assertEquals(true, imageCaptcha.getContent().matches("[0-9]+"));
	}
	
	@Test
	public void shouldVoiceCaptchaHaveCorrectDefaultLength() {
		// Create test data
		int length = 5;

		// Test
		AudioCaptcha audioCaptcha = captchaService.generateVoiceCaptcha(false);

		// Assertions
		assertEquals(length, audioCaptcha.getContent().length());
	}
	
	@Test
	public void shouldValidateCaptcha() {
		// Create test data
		String input = "123456";
		String captcha = "111111";

		// Test
		boolean isValid = captchaService.validateCaptcha(input, captcha);

		// Assertions
		assertEquals(false, isValid);
	}
}
