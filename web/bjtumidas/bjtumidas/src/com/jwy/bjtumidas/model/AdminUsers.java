package com.jwy.bjtumidas.model;

public class AdminUsers implements Comparable<AdminUsers> {
	private int uId;// 用户编号
	private String uNo;// 用户学号
	private String uNickName;// 用户昵称
	private String uPhone;// 用户手机号码
	private String uSex;// 用户性别
	private String uTime;// 用户注册时间
	private String uGrade;// 年级

	public int getuId() {
		return uId;
	}

	public void setuId(int uId) {
		this.uId = uId;
	}

	public String getuNo() {
		return uNo;
	}

	public void setuNo(String uNo) {
		this.uNo = uNo;
	}

	public String getuNickName() {
		return uNickName;
	}

	public void setuNickName(String uNickName) {
		this.uNickName = uNickName;
	}

	public String getuPhone() {
		return uPhone;
	}

	public void setuPhone(String uPhone) {
		this.uPhone = uPhone;
	}

	public String getuSex() {
		return uSex;
	}

	public void setuSex(String uSex) {
		this.uSex = uSex;
	}

	public String getuTime() {
		return uTime;
	}

	public void setuTime(String uTime) {
		this.uTime = uTime;
	}

	public String getuGrade() {
		return uGrade;
	}

	public void setuGrade(String uGrade) {
		this.uGrade = uGrade;
	}

	public AdminUsers(int uId, String uNo, String uNickName, String uPhone,
			String uSex, String uTime, String uGrade) {
		super();
		this.uId = uId;
		this.uNo = uNo;
		this.uNickName = uNickName;
		this.uPhone = uPhone;
		this.uSex = uSex;
		this.uTime = uTime;
		this.uGrade = uGrade;
	}

	public AdminUsers() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public int compareTo(AdminUsers aUsers) {
		return aUsers.uGrade.compareTo(this.uGrade);
	}

}
