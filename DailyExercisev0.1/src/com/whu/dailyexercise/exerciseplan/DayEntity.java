package com.whu.dailyexercise.exerciseplan;

public class DayEntity {
	private int dayid;
	private String date;
	private String iscomplete;
	private float heataccount;
	private int planid;
	public int getDayid() {
		return dayid;
	}
	public void setDayid(int dayid) {
		this.dayid = dayid;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getIscomplete() {
		return iscomplete;
	}
	public void setIscomplete(String iscomplete) {
		this.iscomplete = iscomplete;
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
	
	
}
