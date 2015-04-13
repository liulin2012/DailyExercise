package com.whu.dailyexercise.exerciseplan;

public class StaticPlanEntity {

	//planname,methodid,planintroduction,pictureurl,plantype,planflag
	private int prepareplanid;
	private String planname;
	private String methodid;
	private String planintroduction;
	private String pictureurl;
	private int plantype;
	
	public int getPrepareplanid() {
		return prepareplanid;
	}
	public void setPrepareplanid(int prepareplanid) {
		this.prepareplanid = prepareplanid;
	}
	public String getPlanname() {
		return planname;
	}
	public void setPlanname(String planname) {
		this.planname = planname;
	}
	public String getMethodid() {
		return methodid;
	}
	public void setMethodid(String methodid) {
		this.methodid = methodid;
	}
	public String getPlanintroduction() {
		return planintroduction;
	}
	public void setPlanintroduction(String planintroduction) {
		this.planintroduction = planintroduction;
	}
	public String getPictureurl() {
		return pictureurl;
	}
	public void setPictureurl(String pictureurl) {
		this.pictureurl = pictureurl;
	}
	public int getPlantype() {
		return plantype;
	}
	public void setPlantype(int plantype) {
		this.plantype = plantype;
	}
	public String getPlanflag() {
		return planflag;
	}
	public void setPlanflag(String planflag) {
		this.planflag = planflag;
	}
	private String planflag;

}
