package com.whu.dailyexercise.accountmanager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;

import com.whu.dailyexercise.util.CommonField;

public class Register {
	private static final int REQUEST_TIMEOUT = 10*1000;//设置请求超时10秒钟  
	private static final int SO_TIMEOUT = 20*1000;  //设置等待数据超时时间10秒钟  
	public String result="";
	
	//初始化HttpClient，并设置超时  
    public HttpClient getHttpClient()  
    {  
        BasicHttpParams httpParams = new BasicHttpParams();  
        HttpConnectionParams.setConnectionTimeout(httpParams, REQUEST_TIMEOUT);  
        HttpConnectionParams.setSoTimeout(httpParams, SO_TIMEOUT);  
        HttpClient client = new DefaultHttpClient(httpParams);  
        return client;  
    }
    
    public boolean isRegister(String userid, String password,String username, float height, float weight, String sex, int age)
    {
    	boolean isRegister=false;
    	List<NameValuePair> register=new ArrayList<NameValuePair>();
		register.add(new BasicNameValuePair("userid",userid));
		register.add(new BasicNameValuePair("password",password));
		register.add(new BasicNameValuePair("username",username));
		register.add(new BasicNameValuePair("height",Float.toString(height)));
		register.add(new BasicNameValuePair("weight",Float.toString(weight)));
		register.add(new BasicNameValuePair("sex",sex));
		register.add(new BasicNameValuePair("age",Integer.toString(age)));
		UrlEncodedFormEntity uefe;
		try {
			uefe=new UrlEncodedFormEntity(register, "UTF-8");
			String url="http://"+CommonField.ServerIp+":8080/DailyExerciseServer/register.action";
			HttpPost httppost=new HttpPost(url);
			httppost.setEntity(uefe);
			HttpClient httpclient=getHttpClient();
			try{
				HttpResponse response=httpclient.execute(httppost);
				if(response.getStatusLine().getStatusCode()==200)
				{
					isRegister=true;
					//获得响应信息
					result=EntityUtils.toString(response.getEntity()); 
					System.out.println(result);
				}
				else
				{
					isRegister=false;
				}
			}catch(ClientProtocolException e)
			{
				e.printStackTrace();
			}
		}catch(IOException e)
		{
			e.printStackTrace();
		}
    	return isRegister;
    }
}
