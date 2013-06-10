package com.jcommerce.core.model;

public class AdPosition extends ModelObject {

	private String positionName;
	private int adWidth;
	private int adHeight;
	private String positionDescription;
	private String positionStyle;

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public int getAdWidth() {
		return adWidth;
	}

	public void setAdWidth(int adWidth) {
		this.adWidth = adWidth;
	}

	public int getAdHeight() {
		return adHeight;
	}

	public void setAdHeight(int adHeight) {
		this.adHeight = adHeight;
	}

	public String getpositionDescription() {
		return positionDescription;
	}

	public void setpositionDescription(String positionDescription) {
		this.positionDescription = positionDescription;
	}

	public String getPositionStyle() {
		return positionStyle;
	}

	public void setPositionStyle(String positionStyle) {
		this.positionStyle = positionStyle;
	}

}
