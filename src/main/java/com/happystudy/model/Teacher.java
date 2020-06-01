package com.happystudy.model;

public class Teacher {

	private String tNo;
	private String tName;
	private String tSex;
	private String tDepartFk;
	private String tCourseFk;
	private Integer tRoleFk;
	
	private Integer id;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String gettNo() {
		return tNo;
	}
	public void settNo(String tNo) {
		this.tNo = tNo;
	}
	public String gettName() {
		return tName;
	}
	public void settName(String tName) {
		this.tName = tName;
	}
	public String gettSex() {
		return tSex;
	}
	public void settSex(String tSex) {
		this.tSex = tSex;
	}
	public String gettDepartFk() {
		return tDepartFk;
	}
	public void settDepartFk(String tDepartFk) {
		this.tDepartFk = tDepartFk;
	}
	public String gettCourseFk() {
		return tCourseFk;
	}
	public void settCourseFk(String tCourseFk) {
		this.tCourseFk = tCourseFk;
	}
	public Integer gettRoleFk() {
		return tRoleFk;
	}
	public void settRoleFk(Integer tRoleFk) {
		this.tRoleFk = tRoleFk;
	}
}
