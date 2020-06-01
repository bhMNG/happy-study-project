package com.happystudy.model;

public class Grade {

	private Integer id;
	private String gScore;
	private String gCourseFk;
	private String gStudentFk;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getgScore() {
		return gScore;
	}
	public void setgScore(String gScore) {
		this.gScore = gScore;
	}
	public String getgCourseFk() {
		return gCourseFk;
	}
	public void setgCourseFk(String gCourseFk) {
		this.gCourseFk = gCourseFk;
	}
	public String getgStudentFk() {
		return gStudentFk;
	}
	public void setgStudentFk(String gStudentFk) {
		this.gStudentFk = gStudentFk;
	}
	
}
