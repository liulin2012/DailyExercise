package com.whu.dailyexercise.weatherinfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ParseAQI {
	private String city;
	private String quality;
	private String primary_pollutant;
	private String pm2_5;
	private String no2;
	private String co;
	private String so2;
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getQuality() {
		return quality;
	}
	public void setQuality(String quality) {
		this.quality = quality;
	}
	public String getPrimary_pollutant() {
		return primary_pollutant;
	}
	public void setPrimary_pollutant(String primary_pollutant) {
		this.primary_pollutant = primary_pollutant;
	}
	public String getPm2_5() {
		return pm2_5;
	}
	public void setPm2_5(String pm2_5) {
		this.pm2_5 = pm2_5;
	}
	public String getNo2() {
		return no2;
	}
	public void setNo2(String no2) {
		this.no2 = no2;
	}
	public String getCo() {
		return co;
	}
	public void setCo(String co) {
		this.co = co;
	}
	public String getSo2() {
		return so2;
	}
	public void setSo2(String so2) {
		this.so2 = so2;
	}
	//将获取到天气质量的json数组进行解析
	public ParseAQI(String result)
	{
		if(result!=null)
		{
		try {
			JSONArray a=new JSONArray(result);
			JSONObject b=a.getJSONObject(0);
			this.city=b.getString("area");
			this.quality=b.getString("quality");
			this.primary_pollutant=b.getString("primary_pollutant");
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		else
		{
			this.quality="N/A";
		}
	}
}
