package com.whu.dailyexercise.activities;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;


import com.whu.dailyexercise.R;
import com.whu.dailyexercise.weatherinfo.GetAQI;
import com.whu.dailyexercise.weatherinfo.GetWeather;
import com.whu.dailyexercise.weatherinfo.ParseAQI;
import com.whu.dailyexercise.weatherinfo.ParseWeather;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class WeatherInfoActivity extends Activity {
	
	private TextView weatherCity;
	private TextView weatherTemperature;
	private TextView weatherSpecific;
	private TextView weatherWind;
	private TextView weatherIndex;
	private TextView airQuality;
	private TextView weatherNowTemperature;
	
	private RelativeLayout btn_back;
	
	private String city="武汉市";
	private LocationClient mLocationClient = null;
	private BDLocationListener myListener = new MyLocationListener();
	
	private ProgressDialog mDialog;
	private String isSuccess;
	private ParseWeather b;
	private ParseAQI d;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		
		setContentView(R.layout.activity_weather_info);
		
		
		btn_back=(RelativeLayout) findViewById(R.id.top_left_bg);
		
		weatherCity=(TextView)findViewById(R.id.weatherCity);
		weatherTemperature=(TextView)findViewById(R.id.weatherTemperature);
		weatherSpecific=(TextView)findViewById(R.id.weatherSpecific);
		weatherWind=(TextView)findViewById(R.id.weatherWind);
		weatherIndex=(TextView)findViewById(R.id.indexNumber);
		airQuality=(TextView)findViewById(R.id.airqualityNumber);
		weatherNowTemperature=(TextView)findViewById(R.id.weatherSSTemperature);
		
		mDialog = new ProgressDialog(WeatherInfoActivity.this);
		mDialog.setTitle("同步");
		mDialog.setMessage("正在请求服务器，请稍后...");
		mDialog.show();
		
		btn_back.setOnClickListener(new OnClickListener(){
			public void onClick(View v)
			{
				WeatherInfoActivity.this.finish();
			}
		});
		
		mLocationClient=new LocationClient(getApplicationContext());//声明LocationClient类
		mLocationClient.start();//开启定位
		mLocationClient.registerLocationListener(myListener);//注册监听器
		
		/**
		 * 定位方式的设置
		 */
		LocationClientOption option = new LocationClientOption();
		option.setCoorType("bd09ll");//设置坐标类型
		option.setOpenGps(false);
		option.setPriority(LocationClientOption.NetWorkFirst);
		option.setLocationMode(LocationMode.Battery_Saving);
		option.setIsNeedAddress(true);
		mLocationClient.setLocOption(option);
		
		if(mLocationClient!=null&&mLocationClient.isStarted())
		{
			mLocationClient.requestLocation();
			
		}
		else
		{
			Log.d("LocSDK4.1", "locClient is null or not started");
			city="武汉市";
		}
		
		Thread GetWeatherThread=new Thread(new GetWeatherThread());
		GetWeatherThread.start();
	}

	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.weather_info, menu);
		return true;
	}
	
	class MyLocationListener implements BDLocationListener
	{

		@Override
		public void onReceiveLocation(BDLocation location) {
			// TODO Auto-generated method stub
			if(location==null)
				return;
			if(location.getLocType()==BDLocation.TypeNetWorkLocation)
			{
				
				city=location.getCity();

				
			}
			else
			{
				city="武汉市";

			}
			
		}

		@Override
		public void onReceivePoi(BDLocation location) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	Handler handler = new Handler()  
	{
		public void handleMessage(Message msg)
		{
			switch(msg.what)  
			{
				case 0:mDialog.cancel();
					   weatherCity.setText(b.getCity());
				       weatherTemperature.setText(b.getNowDayTemperature());
				       weatherSpecific.setText(b.getNowDayWeather());
				       weatherWind.setText(b.getNowDayWind());
				       weatherIndex.setText(b.getUvIndex());
				       airQuality.setText("空气质量："+d.getQuality());
				       Log.d("adsfas", "failture");
				       weatherNowTemperature.setText(b.getNewTemperature());
					   break;
			}
		}
	};
	
	class GetWeatherThread implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			GetWeather a=new GetWeather();
			b=new ParseWeather(a.getRemoteInfo(city));
			GetAQI c=new GetAQI();
			d=new ParseAQI(c.getAQIDemo(city));
			isSuccess="true";
			Message msg = handler.obtainMessage();
			if(isSuccess.equals("true"))
			{
				msg.what=0;
				handler.sendMessage(msg);
			}
			
		}
		
	}
}
