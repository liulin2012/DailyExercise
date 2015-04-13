package com.whu.dailyexercise.weatherinfo;

/**
 * ������webxml���õ���������
 * @author darktemple9
 *
 */
public class ParseWeather {

	/**
	 * @param args
	 */
	private String city;//����
	private String time;//ʱ��
	private String nowDayTemperature;//�¶�
	private String nowDayWeather;//����
	private String nowDayWind;//����
    private String clothingNumber;//����ָ��
	private String exerciseNumber;//����ָ��
	private String uvIndex;//������ָ��
	private String temperature;//��ȷ�¶�
	private String newTemperature;//ֻ�������ֵ��¶�
	
	
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
	//������������Ҫ��Ϊ�˷���clothingNumber��exerciseNumber��uvIndex�Ľ�ȡ
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
		if(specificInfo.indexOf("������ǿ�ȣ�")<0)
		{
			this.uvIndex="��������";
		}
		else
		{
			this.uvIndex=specificInfo.substring(specificInfo.indexOf("������ǿ�ȣ�"));
		}
		if(specificInfo.indexOf("���£�")<0)
		{
			this.temperature="��������";
		}
		else
		{
			this.temperature=specificInfo.substring(specificInfo.indexOf("���£�"),specificInfo.indexOf("��"));
			String []a=temperature.split("��");
			this.newTemperature=a[1];
		}
		
		
		this.specificInfo2=results[11];
		if(specificInfo2.indexOf("����ָ����")<0)
		{
			this.clothingNumber="��������";
		}
		else
		{
			this.clothingNumber=specificInfo2.substring(specificInfo2.indexOf("����ָ����"),specificInfo2.indexOf("����ָ����"));
			
		}
		if(specificInfo2.indexOf("�˶�ָ����")<0)
		{
			this.exerciseNumber="��������";
		}
		else
		{
			this.exerciseNumber=specificInfo2.substring(specificInfo2.indexOf("�˶�ָ����"),specificInfo2.indexOf("ϴ��ָ����"));
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

