package com.jwy.bjtumidas.model;

public class VersionBean {
	private int vCode;
	private String downloadURL;
	private String time;

	public int getvCode() {
		return vCode;
	}

	public void setvCode(int vCode) {
		this.vCode = vCode;
	}

	public String getDownloadURL() {
		return downloadURL;
	}

	public void setDownloadURL(String downloadURL) {
		this.downloadURL = downloadURL;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public VersionBean(int vCode, String downloadURL, String time) {
		super();
		this.vCode = vCode;
		this.downloadURL = downloadURL;
		this.time = time;
	}

	@Override
	public String toString() {
		return "VersionBean [vCode=" + vCode + ", downloadURL=" + downloadURL
				+ ", time=" + time + "]";
	}

	public VersionBean() {
		super();
		// TODO Auto-generated constructor stub
	}

}
