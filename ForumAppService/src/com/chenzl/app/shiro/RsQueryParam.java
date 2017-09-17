package com.chenzl.app.shiro;

public class RsQueryParam {
	private boolean resize;//是否切分
	private boolean watermark;//是否水印
	private String resizeSizes;//切分尺寸
	private int wmPos;//水印位置（1:左上，2:上，3：右上，4，左中，5：中，6：右中，7：左下，8：下，9：右下）
	private String wmText;//水印文字
	private int wmColorRgb;//水印顏色
	private int wmFontSize;//水印文字尺寸
	private int wmPositionX;//文字水印X偏移
	private int wmPositionY;//文字水印Y偏移
	private int wmAlpha;//文字水印透明度 1-100，越小越透明
	private String wmImageFilePath;//圖片水印位置
	public boolean isResize() {
		return resize;
	}
	public void setResize(boolean resize) {
		this.resize = resize;
	}
	public boolean isWatermark() {
		return watermark;
	}
	public void setWatermark(boolean watermark) {
		this.watermark = watermark;
	}
	public String getResizeSizes() {
		return resizeSizes;
	}
	public void setResizeSizes(String resizeSizes) {
		this.resizeSizes = resizeSizes;
	}
	public int getWmPos() {
		return wmPos;
	}
	public void setWmPos(int wmPos) {
		this.wmPos = wmPos;
	}
	public String getWmText() {
		return wmText;
	}
	public void setWmText(String wmText) {
		this.wmText = wmText;
	}
	public int getWmColorRgb() {
		return wmColorRgb;
	}
	public void setWmColorRgb(int wmColorRgb) {
		this.wmColorRgb = wmColorRgb;
	}
	public int getWmFontSize() {
		return wmFontSize;
	}
	public void setWmFontSize(int wmFontSize) {
		this.wmFontSize = wmFontSize;
	}
	public int getWmAlpha() {
		return wmAlpha;
	}
	public void setWmAlpha(int wmAlpha) {
		this.wmAlpha = wmAlpha;
	}
	
	public String getWmImageFilePath() {
		return wmImageFilePath;
	}
	public void setWmImageFilePath(String wmImageFilePath) {
		this.wmImageFilePath = wmImageFilePath;
	}
	public int getWmPositionX() {
		return wmPositionX;
	}
	public void setWmPositionX(int wmPositionX) {
		this.wmPositionX = wmPositionX;
	}
	public int getWmPositionY() {
		return wmPositionY;
	}
	public void setWmPositionY(int wmPositionY) {
		this.wmPositionY = wmPositionY;
	}
}
