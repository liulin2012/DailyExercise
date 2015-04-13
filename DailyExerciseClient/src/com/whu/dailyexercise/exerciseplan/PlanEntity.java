package com.whu.dailyexercise.exerciseplan;

public class PlanEntity {
	private int planid;
	private String planname;
	private String methodid;
	private String completepercent;
	private int isoverdue;
	private String userid;
	private String iscomplete;
	private String date;
	private float heataccount;
	private String begindate;
	private int weeknumber;
	private String weekday;
	
	public String getBegindate() {
		return begindate;
	}
	public void setBegindate(String begindate) {
		this.begindate = begindate;
	}
	public int getWeeknumber() {
		return weeknumber;
	}
	public void setWeeknumber(int weeknumber) {
		this.weeknumber = weeknumber;
	}
	public String getWeekday() {
		return weekday;
	}
	public void setWeekday(String weekday) {
		this.weekday = weekday;
	}
	public String getIscomplete() {
		return iscomplete;
	}
	public void setIscomplete(String iscomplete) {
		this.iscomplete = iscomplete;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public float getHeataccount() {
		return heataccount;
	}
	public void setHeataccount(float heataccount) {
		this.heataccount = heataccount;
	}
	public int getPlanid() {
		return planid;
	}
	public void setPlanid(int planid) {
		this.planid = planid;
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
	public String getCompletepercent() {
		return completepercent;
	}
	public void setCompletepercent(String completepercent) {
		this.completepercent = completepercent;
	}
	public int getIsoverdue() {
		return isoverdue;
	}
	public void setIsoverdue(int isoverdue) {
		this.isoverdue = isoverdue;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
}
