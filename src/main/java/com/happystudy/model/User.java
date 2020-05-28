package com.happystudy.model;

public class User {
	private Integer id;
	private String uUsername;
	private String uUserpass;
	private String uPhone;
	private Integer uRoleFk;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getuUsername() {
		return uUsername;
	}
	public void setuUsername(String uUsername) {
		this.uUsername = uUsername;
	}
	public String getuUserpass() {
		return uUserpass;
	}
	public void setuUserpass(String uUserpass) {
		this.uUserpass = uUserpass;
	}
	public String getuPhone() {
		return uPhone;
	}
	public void setuPhone(String uPhone) {
		this.uPhone = uPhone;
	}
	public Integer getuRoleFk() {
		return uRoleFk;
	}
	public void setuRoleFk(Integer uRoleFk) {
		this.uRoleFk = uRoleFk;
	}
	
	
}
