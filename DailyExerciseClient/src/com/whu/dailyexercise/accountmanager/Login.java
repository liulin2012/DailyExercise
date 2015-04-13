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

public class Login {
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
    
    public boolean isLogin(String userid, String password)
	{
		boolean isLogin=false;
		//传递参数
		List<NameValuePair> login=new ArrayList<NameValuePair>();
		login.add(new BasicNameValuePair("userid",userid));
		login.add(new BasicNameValuePair("password",password));
		UrlEncodedFormEntity uefe;
		try {
			uefe=new UrlEncodedFormEntity(login, "UTF-8");
			String url="http://"+CommonField.ServerIp+":8080/DailyExerciseServer/login.action";
			HttpPost httppost=new HttpPost(url);
			httppost.setEntity(uefe);
			HttpClient httpclient=getHttpClient();
			try{
				HttpResponse response=httpclient.execute(httppost);
				if(response.getStatusLine().getStatusCode()==200)
				{
					isLogin=true;
					//获得响应信息
					result=EntityUtils.toString(response.getEntity());  
				}
				else
				{
					isLogin=false;
				}
			}catch(ClientProtocolException e)
			{
				e.printStackTrace();
			}
		}catch(IOException e)
		{
			e.printStackTrace();
		}
		return isLogin;
			
	}
}
