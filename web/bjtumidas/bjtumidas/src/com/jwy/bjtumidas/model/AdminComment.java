package com.jwy.bjtumidas.model;

/**
 * 管理评论
 * 
 * @author HDL
 * 
 */
public class AdminComment implements Comparable<AdminComment> {
	private int cId;// 评论编号
	private String cContent;// 评论内容
	private String uInfo;// 谁评论的
	private int gId;// 对哪条商品的评论
	private String cTime;// 评论的时间

	public int getcId() {
		return cId;
	}

	public void setcId(int cId) {
		this.cId = cId;
	}

	public String getcContent() {
		return cContent;
	}

	public void setcContent(String cContent) {
		this.cContent = cContent;
	}

	public String getuInfo() {
		return uInfo;
	}

	public void setuInfo(String uInfo) {
		this.uInfo = uInfo;
	}

	public int getgId() {
		return gId;
	}

	public void setgId(int gId) {
		this.gId = gId;
	}

	public String getcTime() {
		return cTime;
	}

	public void setcTime(String cTime) {
		this.cTime = cTime;
	}

	public AdminComment(int cId, String cContent, String uInfo, int gId,
			String cTime) {
		super();
		this.cId = cId;
		this.cContent = cContent;
		this.uInfo = uInfo;
		this.gId = gId;
		this.cTime = cTime;
	}

	public AdminComment() {
		super();
	}

	@Override
	public int compareTo(AdminComment comment) {
		return comment.gId > this.gId ? 1 : -1;
	}

}
