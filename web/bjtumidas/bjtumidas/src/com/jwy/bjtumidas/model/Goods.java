package com.jwy.bjtumidas.model;

import java.util.List;

public class Goods {
	private int gId;// 商品编号
	private String gTitle;// 标题
	private int uId;// 发布人
	private String uNickName;// 发布人的昵称
	private int gClassify;// 分类
	private List<String> gImgUrls;// 商品图片地址
	private int gPrice;// 售价
	private int gOldPrice;// 原价
	private byte gPublishType;// 发布类型
	private boolean gIsSaled;// 是否卖出---对应g_state
	private String gDesc;// 商品信息/简介
	private boolean isPinkage;// 是否包邮
	private boolean isUrgent;// 是否紧急出售
	private String gAddress;// 发布人地址
	private byte gBrowCount;// 浏览量
	private byte gNice;// 点赞数量
	private long gTime;// 商品发布时间
	private int gCommentCount;// 评论条数
	

	public int getgCommentCount() {
		return gCommentCount;
	}

	public void setgCommentCount(int gCommentCount) {
		this.gCommentCount = gCommentCount;
	}

	public int getgId() {
		return gId;
	}

	public String getuNickName() {
		return uNickName;
	}

	public void setuNickName(String uNickName) {
		this.uNickName = uNickName;
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

	public int getuId() {
		return uId;
	}

	public void setuId(int uId) {
		this.uId = uId;
	}

	public int getgClassify() {
		return gClassify;
	}

	public void setgClassify(int gClassify) {
		this.gClassify = gClassify;
	}

	public List<String> getgImgUrls() {
		return gImgUrls;
	}

	public void setgImgUrls(List<String> gImgUrls) {
		this.gImgUrls = gImgUrls;
	}

	public int getgPrice() {
		return gPrice;
	}

	public void setgPrice(int gPrice) {
		this.gPrice = gPrice;
	}

	public int getgOldPrice() {
		return gOldPrice;
	}

	public void setgOldPrice(int gOldPrice) {
		this.gOldPrice = gOldPrice;
	}

	public byte getgPublishType() {
		return gPublishType;
	}

	public void setgPublishType(byte gPublishType) {
		this.gPublishType = gPublishType;
	}

	public boolean isgIsSaled() {
		return gIsSaled;
	}

	public void setgIsSaled(boolean gIsSaled) {
		this.gIsSaled = gIsSaled;
	}

	public String getgDesc() {
		return gDesc;
	}

	public void setgDesc(String gDesc) {
		this.gDesc = gDesc;
	}

	public boolean isPinkage() {
		return isPinkage;
	}

	public void setPinkage(boolean isPinkage) {
		this.isPinkage = isPinkage;
	}

	public boolean isUrgent() {
		return isUrgent;
	}

	public void setUrgent(boolean isUrgent) {
		this.isUrgent = isUrgent;
	}

	public String getgAddress() {
		return gAddress;
	}

	public void setgAddress(String gAddress) {
		this.gAddress = gAddress;
	}

	public byte getgBrowCount() {
		return gBrowCount;
	}

	public void setgBrowCount(byte gBrowCount) {
		this.gBrowCount = gBrowCount;
	}

	public byte getgNice() {
		return gNice;
	}

	public void setgNice(byte gNice) {
		this.gNice = gNice;
	}

	public long getgTime() {
		return gTime;
	}

	public void setgTime(long gTime) {
		this.gTime = gTime;
	}

	@Override
	public String toString() {
		return "Goods [gId=" + gId + ", gTitle=" + gTitle + ", uId=" + uId
				+ ", uNickName=" + uNickName + ", gClassify=" + gClassify
				+ ", gImgUrls=" + gImgUrls + ", gPrice=" + gPrice
				+ ", gOldPrice=" + gOldPrice + ", gPublishType=" + gPublishType
				+ ", gIsSaled=" + gIsSaled + ", gDesc=" + gDesc
				+ ", isPinkage=" + isPinkage + ", isUrgent=" + isUrgent
				+ ", gAddress=" + gAddress + ", gBrowCount=" + gBrowCount
				+ ", gNice=" + gNice + ", gTime=" + gTime + "]";
	}

	public Goods() {
	}

}
