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
import org.json.JSONObject;

import com.whu.dailyexercise.exerciseplan.DayEntity;
import com.whu.dailyexercise.exerciseplan.EditPlan;
import com.whu.dailyexercise.exerciseplan.PlanEntity;
import com.whu.dailyexercise.util.CommonField;

import android.database.sqlite.SQLiteDatabase;
public class Upload {
	
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
    
    //上传所有计划
    public static boolean UploadPlan(String userid, SQLiteDatabase sqlitedatabase)
    {
    	boolean isupload=false;
    	//将planlist转化为json
    	List<PlanEntity> planlist=EditPlan.getAllPlanInfo(userid, sqlitedatabase);
    	JSONArray jsonarray=new JSONArray();
    	for(int i=0;i<planlist.size();i++)
    	{
    		JSONObject json=new JSONObject();
    		try{
    			json.put("userid", planlist.get(i).getUserid());
    			json.put("planid", planlist.get(i).getPlanid());
    			json.put("planname", planlist.get(i).getPlanname());
    			json.put("methodid",planlist.get(i).getMethodid());
    			json.put("completepercent", planlist.get(i).getCompletepercent());
    			json.put("isoverdue", planlist.get(i).getIsoverdue());
    			json.put("begindate", planlist.get(i).getBegindate());
    			json.put("weeknumber", planlist.get(i).getWeeknumber());
    			json.put("weekday",planlist.get(i).getWeekday());
    			jsonarray.put(json);//将一个json数据放入jsonArray 数组中去 
    		}catch(Exception e)
    		{
    			e.printStackTrace();
    		}
    	}
    	
    	//将daylist转化为json
    	List<DayEntity> daylist=EditPlan.getAllDayInfo(userid, sqlitedatabase);
    	JSONArray jsonarray1=new JSONArray();
    	for(int i=0;i<daylist.size();i++)
    	{
    		JSONObject json=new JSONObject();
    		try{
    			json.put("dayid",daylist.get(i).getDayid());
    			json.put("date", daylist.get(i).getDate());
    			json.put("iscomplete", daylist.get(i).getIscomplete());
    			json.put("heataccount", daylist.get(i).getHeataccount());
    			json.put("planid",daylist.get(i).getPlanid());
    			jsonarray1.put(json);
    		}catch(Exception e)
    		{
    			e.printStackTrace();
    		}
    	}
    	
    	//传递参数
    	List<NameValuePair> upload=new ArrayList<NameValuePair>();
    	upload.add(new BasicNameValuePair("myplanlist",jsonarray.toString()));
    	upload.add(new BasicNameValuePair("mydaylist",jsonarray1.toString()));
    	UrlEncodedFormEntity uefe;
		try {
			uefe=new UrlEncodedFormEntity(upload, "UTF-8");
			String url="http://"+CommonField.ServerIp+":8080/DailyExerciseServer/infoSyn.action";
			HttpPost httppost=new HttpPost(url);
			httppost.setEntity(uefe);
			HttpClient httpclient=getHttpClient();
			try{
				HttpResponse response=httpclient.execute(httppost);
				if(response.getStatusLine().getStatusCode()==200)
				{
					isupload=true;
					//获得响应信息
					result=EntityUtils.toString(response.getEntity());  
				}
				else
				{
					isupload=false;
				}
			}catch(ClientProtocolException e)
			{
				e.printStackTrace();
			}
		}catch(IOException e)
		{
			e.printStackTrace();
		}
    	return isupload;
    	
    }
    
    //修改个人信息
    public static boolean UploadUserInfo(List<UserEntity> userlist)
    {
    	boolean a=false;
    	String password=userlist.get(0).getPassword();
    	String username=userlist.get(0).getUsername();
    	String sex=userlist.get(0).getSex();
    	String userid=userlist.get(0).getUserid();
    	float height=userlist.get(0).getHeight();
    	float weight=userlist.get(0).getWeight();
    	int age=userlist.get(0).getAge();
    	List<NameValuePair> uploaduserinfo=new ArrayList<NameValuePair>();
    	uploaduserinfo.add(new BasicNameValuePair("password",password));
    	uploaduserinfo.add(new BasicNameValuePair("username",username));
    	uploaduserinfo.add(new BasicNameValuePair("userid",userid));
    	uploaduserinfo.add(new BasicNameValuePair("height",Float.toString(height)));
    	uploaduserinfo.add(new BasicNameValuePair("weight",Float.toString(weight)));
    	uploaduserinfo.add(new BasicNameValuePair("sex",sex));
    	uploaduserinfo.add(new BasicNameValuePair("age",Integer.toString(age)));
    	UrlEncodedFormEntity uefe;
		try {
			uefe=new UrlEncodedFormEntity(uploaduserinfo, "UTF-8");
			String url="http://"+CommonField.ServerIp+":8080/DailyExerciseServer/userInfo.action";
			HttpPost httppost=new HttpPost(url);
			httppost.setEntity(uefe);
			HttpClient httpclient=getHttpClient();
			try{
				HttpResponse response=httpclient.execute(httppost);
				if(response.getStatusLine().getStatusCode()==200)
				{
					a=true;
					//获得响应信息
					result=EntityUtils.toString(response.getEntity()); 
					System.out.println(result);
				}
				else
				{
					a=false;
				}
			}catch(ClientProtocolException e)
			{
				e.printStackTrace();
			}
		}catch(IOException e)
		{
			e.printStackTrace();
		}
    	return a;
    }
}
