package com.jwy.bjtumidas.model;

public class Users {
	private int uId;// 用户编号
	private String uNo;// 用户学号
	private String uNickName;// 用户昵称
	private String uPhone;// 用户手机号码
	private String uPwd;// 用户密码
	private boolean isMan;// 用户性别
	private Long uTime;// 用户注册时间

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

	public String getuPwd() {
		return uPwd;
	}

	public void setuPwd(String uPwd) {
		this.uPwd = uPwd;
	}

	public boolean isMan() {
		return isMan;
	}

	public void setMan(boolean isMan) {
		this.isMan = isMan;
	}

	public Long getuTime() {
		return uTime;
	}

	public void setuTime(Long uTime) {
		this.uTime = uTime;
	}

	@Override
	public String toString() {
		return "Users [uId=" + uId + ", uNo=" + uNo + ", uNickName="
				+ uNickName + ", uPhone=" + uPhone + ", uPwd=" + uPwd
				+ ", isMan=" + isMan + ", uTime=" + uTime + "]";
	}

	public Users(int uId, String uNo, String uNickName, String uPhone,
			String uPwd, boolean isMan, Long uTime) {
		super();
		this.uId = uId;
		this.uNo = uNo;
		this.uNickName = uNickName;
		this.uPhone = uPhone;
		this.uPwd = uPwd;
		this.isMan = isMan;
		this.uTime = uTime;
	}

	public Users() {
		super();
	}

}
