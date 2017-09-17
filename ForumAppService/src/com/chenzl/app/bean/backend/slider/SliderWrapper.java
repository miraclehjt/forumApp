package com.chenzl.app.bean.backend.slider;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class SliderWrapper {
	
	private String sliderId;

	private String sliderImg;

	private String sliderLink;

	private String state;
	
	private String userId;

	private String sliderName;

	private CommonsMultipartFile[] iconFile;
	
	private String sortId;

	public String getSliderId() {
		return sliderId;
	}

	public void setSliderId(String sliderId) {
		this.sliderId = sliderId;
	}

	public String getSliderImg() {
		return sliderImg;
	}

	public void setSliderImg(String sliderImg) {
		this.sliderImg = sliderImg;
	}

	public String getSliderLink() {
		return sliderLink;
	}

	public void setSliderLink(String sliderLink) {
		this.sliderLink = sliderLink;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	

	public String getSliderName() {
		return sliderName;
	}

	public void setSliderName(String sliderName) {
		this.sliderName = sliderName;
	}

	public CommonsMultipartFile[] getIconFile() {
		return iconFile;
	}

	public void setIconFile(CommonsMultipartFile[] iconFile) {
		this.iconFile = iconFile;
	}



	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSortId() {
		return sortId;
	}

	public void setSortId(String sortId) {
		this.sortId = sortId;
	}

}
