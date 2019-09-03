package com.jwy.bjtumidas.model;

public class GoodsType {
	private int tId;
	private String tName;

	public int gettId() {
		return tId;
	}

	public void settId(int tId) {
		this.tId = tId;
	}

	public String gettName() {
		return tName;
	}

	public void settName(String tName) {
		this.tName = tName;
	}

	public GoodsType(int tId, String tName) {
		super();
		this.tId = tId;
		this.tName = tName;
	}

	public GoodsType() {
		super();

	}

}
