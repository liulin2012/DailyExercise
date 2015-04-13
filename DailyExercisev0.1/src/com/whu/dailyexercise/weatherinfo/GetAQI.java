package com.whu.dailyexercise.weatherinfo;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
/**
 * 请求天气质量数据
 * @author darktemple9
 *
 */
public class GetAQI {
	
	public String getAQIDemo(String city)
	{
		if(city.contains("市"))
		{
			city=city.substring(0, city.indexOf("市"));
		}
		String result=null;
		UrlEncodedFormEntity uefe=null;
		try {
			
			String url="http://www.pm25.in/api/querys/aqi_details.json?city="+city+"&token=YZGfRKfjgzJE78xFEjxg";
			HttpGet httpGet=new HttpGet(url);
			
			HttpClient httpclient=new DefaultHttpClient();
			try{
				HttpResponse response=httpclient.execute(httpGet);
				if(response.getStatusLine().getStatusCode()==200)
				{
					 result=EntityUtils.toString(response.getEntity());  
				}
				else
				{
					result=null;
				}
			}catch(ClientProtocolException e)
			{
				e.printStackTrace();
			}
		}catch(IOException e)
		{
			e.printStackTrace();
		}
		return result;
	}
}
