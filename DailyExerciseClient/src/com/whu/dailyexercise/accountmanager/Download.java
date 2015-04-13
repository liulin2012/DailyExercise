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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.whu.dailyexercise.exerciseplan.EditPlan;
import com.whu.dailyexercise.util.CommonField;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class Download {
	private static final int REQUEST_TIMEOUT = 10*1000;//设置请求超时10秒钟  
	private static final int SO_TIMEOUT = 20*1000;  //设置等待数据超时时间10秒钟  
	private static String result="";
	//初始化HttpClient，并设置超时  
    public static HttpClient getHttpClient()  
    {  
        BasicHttpParams httpParams = new BasicHttpParams();  
        HttpConnectionParams.setConnectionTimeout(httpParams, REQUEST_TIMEOUT);  
        HttpConnectionParams.setSoTimeout(httpParams, SO_TIMEOUT);  
        HttpClient client = new DefaultHttpClient(httpParams);  
        return client;  
    } 
    
    //下载计划信息
    public static boolean DownloadPlan(String userid, SQLiteDatabase sqlitedatabase)
    {
    	boolean isdownload=false;
    	HttpClient httpclient=getHttpClient();
    	List<NameValuePair> upload=new ArrayList<NameValuePair>();
    	upload.add(new BasicNameValuePair("userid",userid));
    	UrlEncodedFormEntity uefe;
    	String url="http://"+CommonField.ServerIp+":8080/DailyExerciseServer/upUserData.action";
    	HttpPost httppost=new HttpPost(url);
    	try {
    		uefe=new UrlEncodedFormEntity(upload, "UTF-8");
    		httppost.setEntity(uefe);
			HttpResponse response=httpclient.execute(httppost);
			if(response.getStatusLine().getStatusCode()==200)
			{
				result=EntityUtils.toString(response.getEntity()); 
				try {
					JSONObject a=new JSONObject(result);
					String dayplan=a.getString("dayplan");
					String trainplan=a.getString("trainplan");
					JSONArray dayjsonarray=new JSONArray(dayplan);
					JSONArray trainjsonarray=new JSONArray(trainplan);
					for(int i=0;i<trainjsonarray.length();i++)
					{
						JSONObject trainjson=trainjsonarray.getJSONObject(i);
						String planid=trainjson.getString("planid");
						String methodid=trainjson.getString("methodid");
						String completepercent=trainjson.getString("completepercent");
						String isoverdue=trainjson.getString("isoverdue");
						String begintime=trainjson.getString("begintime");
						String weeknumber=trainjson.getString("weeknumber");
						String weekday=trainjson.getString("weekday");
						String planname=trainjson.getString("planname");
						String myuserid=trainjson.getString("userid");
						Log.d("sdfasdfas", planname);
						EditPlan.downLoadTrainplan(planid, methodid, completepercent, isoverdue, begintime, weeknumber, weekday, planname, myuserid, sqlitedatabase);
					
					}
					
					for(int i=0;i<dayjsonarray.length();i++)
					{
						JSONObject dayjson=dayjsonarray.getJSONObject(i);
						String dayid=dayjson.getString("dayid");
						String date=dayjson.getString("date");
						String iscomplete=dayjson.getString("iscomplete");
						String heataccount=dayjson.getString("heataccount");
						String planid=dayjson.getString("planid");
						EditPlan.downLoadDayPlan(dayid, date, iscomplete, heataccount, planid, sqlitedatabase);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				isdownload=true;
				Log.d("jsonstring", result);
			}
			else
			{
				isdownload=false;
			}
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return isdownload;
    }
}
