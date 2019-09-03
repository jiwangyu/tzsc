package com.jwy.bjtumidas.model;

public class AdminGoods implements Comparable<AdminGoods> {
	private int gId;// 商品编号
	private String gTitle;// 标题
	private String uInfo;// 发布人
	private String gType;// 分类
	private int gPrice;// 售价
	private String gPublishType;// 发布类型
	private String gDesc;// 商品信息/简介
	private int gMaxPrice;// 发布人地址
	private byte gBrowCount;// 浏览量
	private String gTime;// 商品发布时间

	public int getgId() {
		return gId;
	}

	public void setgId(int gId) {
		this.gId = gId;
	}

	public String getgTitle() {
		return gTitle;
	}

	public void setgTitle(String gTitle) {
		this.gTitle = gTitle;
	}

	public String getuInfo() {
		return uInfo;
	}

	public void setuInfo(String uInfo) {
		this.uInfo = uInfo;
	}

	public String getgType() {
		return gType;
	}

	public void setgType(String gType) {
		this.gType = gType;
	}

	public int getgPrice() {
		return gPrice;
	}

	public void setgPrice(int gPrice) {
		this.gPrice = gPrice;
	}

	public String getgPublishType() {
		return gPublishType;
	}

	public void setgPublishType(String gPublishType) {
		this.gPublishType = gPublishType;
	}

	public String getgDesc() {
		return gDesc;
	}

	public void setgDesc(String gDesc) {
		this.gDesc = gDesc;
	}

	public int getgMaxPrice() {
		return gMaxPrice;
	}

	public void setgMaxPrice(int gMaxPrice) {
		this.gMaxPrice = gMaxPrice;
	}

	public byte getgBrowCount() {
		return gBrowCount;
	}

	public void setgBrowCount(byte gBrowCount) {
		this.gBrowCount = gBrowCount;
	}

	public String getgTime() {
		return gTime;
	}

	public void setgTime(String gTime) {
		this.gTime = gTime;
	}

	public AdminGoods(int gId, String gTitle, String uInfo, String gType,
			int gPrice, String gPublishType, String gDesc, int gAddress,
			byte gBrowCount, String gTime) {
		super();
		this.gId = gId;
		this.gTitle = gTitle;
		this.uInfo = uInfo;
		this.gType = gType;
		this.gPrice = gPrice;
		this.gPublishType = gPublishType;
		this.gDesc = gDesc;
		this.gMaxPrice = gAddress;
		this.gBrowCount = gBrowCount;
		this.gTime = gTime;
	}

	public AdminGoods() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public int compareTo(AdminGoods goods) {

		return goods.getuInfo().compareTo(this.uInfo);
	}

}
