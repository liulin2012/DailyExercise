package com.whu.dailyexercise.weatherinfo;

/**
 * 解析从webxml里获得的天气数据
 * @author darktemple9
 *
 */
public class ParseWeather {

	/**
	 * @param args
	 */
	private String city;//城市
	private String time;//时间
	private String nowDayTemperature;//温度
	private String nowDayWeather;//天气
	private String nowDayWind;//风力
    private String clothingNumber;//穿衣指数
	private String exerciseNumber;//锻炼指数
	private String uvIndex;//紫外线指数
	private String temperature;//精确温度
	private String newTemperature;//只含有数字的温度
	
	
	public String getTemperature() {
		return temperature;
	}
	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}
	public String getNewTemperature() {
		return newTemperature;
	}
	public void setNewTemperature(String newTemperature) {
		this.newTemperature = newTemperature;
	}
	//这两个变量主要是为了方便clothingNumber、exerciseNumber、uvIndex的截取
	private String specificInfo;
	private String specificInfo2;
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getNowDayTemperature() {
		return nowDayTemperature;
	}
	public void setNowDayTemperature(String nowDayTemperature) {
		this.nowDayTemperature = nowDayTemperature;
	}
	public String getNowDayWeather() {
		return nowDayWeather;
	}
	public void setNowDayWeather(String nowDayWeather) {
		this.nowDayWeather = nowDayWeather;
	}
	public String getNowDayWind() {
		return nowDayWind;
	}
	public void setNowDayWind(String nowDayWind) {
		this.nowDayWind = nowDayWind;
	}
	public String getClothingNumber() {
		return clothingNumber;
	}
	public void setClothingNumber(String clothingNumber) {
		this.clothingNumber = clothingNumber;
	}
	public String getExerciseNumber() {
		return exerciseNumber;
	}
	public void setExerciseNumber(String exerciseNumber) {
		this.exerciseNumber = exerciseNumber;
	}
	public String getUvIndex() {
		return uvIndex;
	}
	public void setUvIndex(String uvIndex) {
		this.uvIndex = uvIndex;
	}
	public String getSpecificInfo() {
		return specificInfo;
	}
	public void setSpecificInfo(String specificInfo) {
		this.specificInfo = specificInfo;
	}
	public String getSpecificInfo2() {
		return specificInfo2;
	}
	public void setSpecificInfo2(String specificInfo2) {
		this.specificInfo2 = specificInfo2;
	}
	public ParseWeather(String result)
	{
		super();
		if(result!=null)
		{
		String []results=result.replace("string=", "").split(";");
		String weather=results[6];
		String[] myweather=weather.split(" ");
		
		this.city=results[1];
		this.time=myweather[1];
		this.nowDayWeather=myweather[2];
		
		this.nowDayTemperature=results[5];
		
		this.nowDayWind=results[7];
		this.specificInfo=results[10];
		if(specificInfo.indexOf("紫外线强度：")<0)
		{
			this.uvIndex="数据暂无";
		}
		else
		{
			this.uvIndex=specificInfo.substring(specificInfo.indexOf("紫外线强度："));
		}
		if(specificInfo.indexOf("气温：")<0)
		{
			this.temperature="暂无数据";
		}
		else
		{
			this.temperature=specificInfo.substring(specificInfo.indexOf("气温："),specificInfo.indexOf("；"));
			String []a=temperature.split("：");
			this.newTemperature=a[1];
		}
		
		
		this.specificInfo2=results[11];
		if(specificInfo2.indexOf("穿衣指数：")<0)
		{
			this.clothingNumber="数据暂无";
		}
		else
		{
			this.clothingNumber=specificInfo2.substring(specificInfo2.indexOf("穿衣指数："),specificInfo2.indexOf("过敏指数："));
			
		}
		if(specificInfo2.indexOf("运动指数：")<0)
		{
			this.exerciseNumber="数据暂无";
		}
		else
		{
			this.exerciseNumber=specificInfo2.substring(specificInfo2.indexOf("运动指数："),specificInfo2.indexOf("洗车指数："));
		}	
		}
		else
		{
			this.city="N/A";
			this.nowDayTemperature="N/A";
			this.nowDayWeather="N/A";
			this.nowDayWind="N/A";
			this.uvIndex="N/A";
			this.newTemperature="N/A";
			
		}
	}

}

