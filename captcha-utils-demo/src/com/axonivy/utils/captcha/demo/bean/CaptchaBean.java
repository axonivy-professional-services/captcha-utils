package com.axonivy.utils.captcha.demo.bean;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.SPACE;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.UUID;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.axonivy.utils.captcha.CaptchaService;
import com.axonivy.utils.captcha.ImageCaptchaBuilder;
import com.axonivy.utils.captcha.demo.constant.Constants;
import com.axonivy.utils.captcha.internal.AdvancedCaptchaService;
import ch.ivyteam.ivy.environment.Ivy;
import ch.ivyteam.ivy.scripting.objects.File;
import net.logicsquad.nanocaptcha.audio.AudioCaptcha;
import net.logicsquad.nanocaptcha.audio.noise.RandomNoiseProducer;
import net.logicsquad.nanocaptcha.audio.producer.RandomNumberVoiceProducer;
import net.logicsquad.nanocaptcha.image.ImageCaptcha;

public class CaptchaBean {

	private CaptchaService captchaService;

	private String firstName;
	private String lastName;

	private StreamedContent captcha;
	private File captchaAudio;

	private String captchaValue;
	private String audioCaptchaValue;

	private ImageCaptcha imageCaptcha;
	private AudioCaptcha audioCaptcha;

	private String captchaType;

	public void init() {
		this.firstName = EMPTY;
		this.lastName = EMPTY;
		this.captchaType = EMPTY;
		this.captchaService = new AdvancedCaptchaService();
		onGenerateCaptcha();
		onGenerateAudioCaptcha();
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public StreamedContent getCaptcha() {
		return captcha;
	}

	public void setCaptcha(StreamedContent captcha) {
		this.captcha = captcha;
	}

	public File getCaptchaAudio() {
		return captchaAudio;
	}

	public void setCaptchaAudio(File captchaAudio) {
		this.captchaAudio = captchaAudio;
	}

	public String getCaptchaValue() {
		return captchaValue;
	}

	public void setCaptchaValue(String captchaValue) {
		this.captchaValue = captchaValue;
	}

	public String getAudioCaptchaValue() {
		return audioCaptchaValue;
	}

	public void setAudioCaptchaValue(String audioCaptchaValue) {
		this.audioCaptchaValue = audioCaptchaValue;
	}

	public ImageCaptcha getImageCaptcha() {
		return imageCaptcha;
	}

	public void setImageCaptcha(ImageCaptcha imageCaptcha) {
		this.imageCaptcha = imageCaptcha;
	}

	public AudioCaptcha getAudioCaptcha() {
		return audioCaptcha;
	}

	public void setAudioCaptcha(AudioCaptcha audioCaptcha) {
		this.audioCaptcha = audioCaptcha;
	}

	public String getCaptchaType() {
		return captchaType;
	}

	public void setCaptchaType(String captchaType) {
		this.captchaType = captchaType;
	}

	public void onGenerateCaptcha() {
		ImageCaptchaBuilder imageCaptchaBuilder = new ImageCaptchaBuilder();
		this.imageCaptcha = captchaService.generateImageCaptcha(imageCaptchaBuilder);
		this.captcha = getCaptchaData(imageCaptcha);
	}

	public void onGenerateAudioCaptcha() {
		this.audioCaptcha = generateVoiceCaptcha(true);
		this.captchaAudio = getAudioCaptchaFile(audioCaptcha);
	}

	public void onCheckCaptchaValue(String info, String value) {
		String imageCaptcha = this.imageCaptcha.getContent();
		boolean isCaptchaValid = captchaService.validateCaptcha(value, imageCaptcha);
		addMessage(info, isCaptchaValid);
	}

	public void onCheckAudioCaptchaValue(String info, String value) {
		String audioCaptcha = this.audioCaptcha.getContent();
		boolean isCaptchaValid = captchaService.validateCaptcha(value, audioCaptcha);
		addMessage(info, isCaptchaValid);
	}

	private void addMessage(String info, boolean isCaptchaValid) {
		FacesMessage msg = null;

		if (isCaptchaValid) {
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, info, null);
		} else {
			msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Capcha is wrong !", null);
		}

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	public void checkCaptchaValue() {
		String hello = "Hello " + firstName + SPACE + lastName;
		
		if(captchaType.equals("Image")){
		  onCheckCaptchaValue(hello, captchaValue);
		} else {
		  onCheckAudioCaptchaValue(hello, audioCaptchaValue);
		}

		resetValue();
	}

	public String getAudioLink() {
		if (this.captchaAudio == null) {
			return null;
		}

		return Ivy.html().fileLink(captchaAudio).toAbsoluteUri().toString();
	}

	private StreamedContent getCaptchaData(ImageCaptcha imageCaptcha) {
		return DefaultStreamedContent.builder().contentType(Constants.CONTENT_TYPE_IMAGE).stream(() -> {
			try {
				ByteArrayOutputStream os = new ByteArrayOutputStream();
				ImageIO.write(imageCaptcha.getImage(), Constants.FORMAT_NAME_PNG, os);
				return new ByteArrayInputStream(os.toByteArray());
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}).build();
	}

	private AudioCaptcha generateVoiceCaptcha(boolean soundNoise) {
		AudioCaptcha.Builder builder = new AudioCaptcha.Builder().addContent()
				.addVoice(new RandomNumberVoiceProducer(Locale.US));
		if (soundNoise) {
			builder.addNoise(new RandomNoiseProducer());
		}

		return builder.build();
	}

	private File getAudioCaptchaFile(AudioCaptcha audioCaptcha) {
		try {
			File file = new File(UUID.randomUUID().toString() + Constants.FILE_MP3_EXTENSION);
			AudioSystem.write(audioCaptcha.getAudio().getAudioInputStream(), AudioFileFormat.Type.WAVE,
					file.getJavaFile());

			return file;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private void resetValue() {
		this.captchaValue = EMPTY;
		this.audioCaptchaValue = EMPTY;
		this.firstName = EMPTY;
		this.lastName = EMPTY;
		onGenerateCaptcha();
		onGenerateAudioCaptcha();
	}
}
