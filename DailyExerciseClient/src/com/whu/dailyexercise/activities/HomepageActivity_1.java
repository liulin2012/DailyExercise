package com.whu.dailyexercise.activities;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.whu.dailyexercise.R;
import com.whu.dailyexercise.accountmanager.UserInfo;
import com.whu.dailyexercise.database.DBHelper;
import com.whu.dailyexercise.exerciseplan.EditPlan;
import com.whu.dailyexercise.exerciseplan.StaticPlanHelper;
import com.whu.dailyexercise.util.CommonField;

import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("SimpleDateFormat")
public class HomepageActivity_1 extends Activity {

	private LinearLayout btn_useraccount;  //�û���¼��ť
	private LinearLayout btn_baseexercise;  //������ϰ�ⰴť
	private LinearLayout btn_userdiy;  //���Լƻ���ť
	private LinearLayout btn_heartbeat;  //�û����İ�ť
	private LinearLayout btn_weatherinfo;  //������Ϣ��ť
	private LinearLayout btn_myplan;  //�ҵļƻ���ť
	private LinearLayout btn_homepage_1;  //�����水ť_1
	private LinearLayout btn_homepage_2;  //�����水ť_2
	private LinearLayout btn_homepage_3;  //�����水ť_3
	private LinearLayout btn_homepage_4;  //�����水ť_4
	
	private ViewPager viewPager;//��ҳ��ͼƬ�����ؼ�
	private List<View> list;//��ʾ�������ص�view��list����ʵ��layout��tabҳ��list
	
	private TextView page1_notice;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_homepage);
		CommonField.helper = new DBHelper(HomepageActivity_1.this,"dailyexercise",null,1);
		CommonField.sqlitedatabasa = CommonField.helper.getWritableDatabase();
		//�󶨿ؼ�
		CommonField.homepage1_username=(TextView)findViewById(R.id.tv_app_home_hello_user);
		CommonField.homepage1_please_login=(TextView)findViewById(R.id.tv_app_home_hello_pleaselogin);
		btn_useraccount = (LinearLayout)findViewById(R.id.llHomepageUser);
		btn_baseexercise = (LinearLayout)findViewById(R.id.llHomepageExers);
		btn_userdiy = (LinearLayout)findViewById(R.id.llHomepageDiy);
		btn_heartbeat = (LinearLayout)findViewById(R.id.llHomepageHeartBeat);
		btn_weatherinfo = (LinearLayout)findViewById(R.id.llHomepageWeatherInfo);
		btn_myplan = (LinearLayout)findViewById(R.id.btnHomePageCenter);
		btn_homepage_1 = (LinearLayout)findViewById(R.id.llHomepagePart_1);
		btn_homepage_2 = (LinearLayout)findViewById(R.id.llHomepagePart_2);
		btn_homepage_3 = (LinearLayout)findViewById(R.id.llHomepagePart_3);
		btn_homepage_4 = (LinearLayout)findViewById(R.id.llHomepagePart_4);
		//�󶨵��������
		btn_useraccount.setOnClickListener(new BtnUserAccountClickListener());
		btn_baseexercise.setOnClickListener(new BtnBaseExerciseClickListener());
		btn_userdiy.setOnClickListener(new BtnUserDiyClickListener());
		btn_heartbeat.setOnClickListener(new BtnHeartBeatClickListener());
		btn_weatherinfo.setOnClickListener(new BtnWeatherInfoClickListener());
		btn_myplan.setOnClickListener(new BtnMyPlanClickListener());
		btn_homepage_1.setOnClickListener(new BtnHomepagePart1ClickListener());
		btn_homepage_2.setOnClickListener(new BtnHomepagePart2ClickListener());
		btn_homepage_3.setOnClickListener(new BtnHomepagePart3ClickListener());
		btn_homepage_4.setOnClickListener(new BtnHomepagePart4ClickListener());
		
		viewPager = (ViewPager)findViewById(R.id.viewpager_homepage);
		//��̬���ز���
		View view1 = LayoutInflater.from(HomepageActivity_1.this).inflate(R.layout.viewpager_homepage_tab1,null);
		View view2 = LayoutInflater.from(HomepageActivity_1.this).inflate(R.layout.viewpager_homepage_tab2,null);
		View view3 = LayoutInflater.from(HomepageActivity_1.this).inflate(R.layout.viewpager_homepage_tab3,null);
		
		list = new ArrayList<View>();
		list.add(view1);
		list.add(view2);
		list.add(view3);
		
		viewPager.setAdapter(new MyAdapter());
		
		page1_notice = (TextView)findViewById(R.id.homepage1_notice);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date today = new Date();
		CommonField.dToday = today;
		String sToday = simpleDateFormat.format(today);
		CommonField.sToday = simpleDateFormat.format(today);
		//��ȡ����ѵ����Ŀ
		CommonField.todayplanlist = EditPlan.getPlanByDate(CommonField.userId,sToday,CommonField.sqlitedatabasa);
		
		String notice =sToday+"  "+"��������"+CommonField.todayplanlist.size()+"��ѵ���ƻ�";
		page1_notice.setText(notice);
		
		//Ԥ��ѵ���ƻ�
		CommonField.staticEntityList = StaticPlanHelper.getStaticPlanCollection(CommonField.sqlitedatabasa);
	
	    //��½�ɹ�
		CommonField.userlist=UserInfo.getUserInfo(CommonField.userId, CommonField.sqlitedatabasa);
		CommonField.loginOK=true;
		CommonField.homepage1_please_login.setText("�ѵ�¼");
        CommonField.homepage1_username.setText(CommonField.userId);
	}
	
     //viewpager���������ķ�����д
	class MyAdapter extends PagerAdapter{
		public int getCount() {
			return list.size();
		};
		
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
		//	super.destroyItem(container, position, object);
			((ViewPager)container).removeView(list.get(position));
		}
		
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// TODO Auto-generated method stub
			((ViewPager)container).addView(list.get(position));
			return list.get(position);
		}
		
		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}
		
	}
	
	//���Ͻǰ�ť�û���¼���������
	class BtnUserAccountClickListener implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			if(CommonField.loginOK)
			{
				Intent intent = new Intent();
				intent.setClass(HomepageActivity_1.this,UserAccountActivity.class);
				HomepageActivity_1.this.startActivity(intent);
			}
			else
			{
				Intent intent = new Intent();
				intent.setClass(HomepageActivity_1.this,LoginActivity.class);
				HomepageActivity_1.this.startActivity(intent);
			}
		}
		
	}
	
	//������ϰ�ⰴť���������
	class BtnBaseExerciseClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(HomepageActivity_1.this,BaseExerciseActivity.class);
			HomepageActivity_1.this.startActivity(intent);
		}
	}
	
	//���Լƻ���ť���������
	class BtnUserDiyClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			//��ת����Ӽƻ�����1
			if(!CommonField.loginOK)
			{
				Toast.makeText(HomepageActivity_1.this, "����δ��½���޷�ʹ�øù���", Toast.LENGTH_SHORT).show();
				return;
			}
			Intent intent = new Intent();
			intent.setClass(HomepageActivity_1.this,AddDiyPlanActivity_1.class);
			HomepageActivity_1.this.startActivity(intent);
		}
	}
	
	//�������԰�ť���������
	class BtnHeartBeatClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(HomepageActivity_1.this,HeartBeatActivity.class);
			HomepageActivity_1.this.startActivity(intent);
		}
	}
	
	//������Ϣ��ť���������
		class BtnWeatherInfoClickListener implements OnClickListener {
			
			public boolean checkWifi(Activity activitiy) {
				WifiManager mWifiManager = (WifiManager) activitiy
						.getSystemService(Context.WIFI_SERVICE);
				WifiInfo wifiInfo = mWifiManager.getConnectionInfo();
				int ipAddress = wifiInfo == null ? 0 : wifiInfo.getIpAddress();
				if (mWifiManager.isWifiEnabled() && ipAddress != 0) {
			            System.out.println("**** WIFI is on");
			            return true;
				} else {
		                    System.out.println("**** WIFI is off");
		                    return false;    
		               }
		}
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(checkWifi(HomepageActivity_1.this))
				{
				
					Intent intent = new Intent();
					intent.setClass(HomepageActivity_1.this,WeatherInfoActivity.class);
					HomepageActivity_1.this.startActivity(intent);
				}
				else
				{
					 Toast.makeText(getApplicationContext(), "��ȷ���Ƿ���wifi����", Toast.LENGTH_SHORT).show();  
				}
			}
		}

	
	//�ҵļƻ���ť���������
	class BtnMyPlanClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(!CommonField.loginOK)
			{
				Toast.makeText(HomepageActivity_1.this, "����δ��½���޷�ʹ�øù���", Toast.LENGTH_SHORT).show();
				return;
			}
			Intent intent = new Intent();
			intent.setClass(HomepageActivity_1.this,HomepageActivity_2.class);
			HomepageActivity_1.this.startActivity(intent);
			overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);  
		}
	}
	
	//�в���ť1��ť���������
	class BtnHomepagePart1ClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(!CommonField.loginOK)
			{
				Toast.makeText(HomepageActivity_1.this, "����δ��½���޷�ʹ�øù���", Toast.LENGTH_SHORT).show();
				return;
			}
			Intent intent = new Intent();
			intent.putExtra("PlanType", 0);
			intent.setClass(HomepageActivity_1.this,StaticPlanListActivity.class);
			HomepageActivity_1.this.startActivity(intent);

		}
	}
	//�в���ť2��ť���������
	class BtnHomepagePart2ClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(!CommonField.loginOK)
			{
				Toast.makeText(HomepageActivity_1.this, "����δ��½���޷�ʹ�øù���", Toast.LENGTH_SHORT).show();
				return;
			}
			Intent intent = new Intent();
			intent.putExtra("PlanType", 1);
			intent.setClass(HomepageActivity_1.this,StaticPlanListActivity.class);
			HomepageActivity_1.this.startActivity(intent);

		}
	}
	//�в���ť3��ť���������
	class BtnHomepagePart3ClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(!CommonField.loginOK)
			{
				Toast.makeText(HomepageActivity_1.this, "����δ��½���޷�ʹ�øù���", Toast.LENGTH_SHORT).show();
				return;
			}
			Intent intent = new Intent();
			intent.putExtra("PlanType", 2);
			intent.setClass(HomepageActivity_1.this,StaticPlanListActivity.class);
			HomepageActivity_1.this.startActivity(intent);

		}
	}
	//�в���ť4��ť���������
	class BtnHomepagePart4ClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(!CommonField.loginOK)
			{
				Toast.makeText(HomepageActivity_1.this, "����δ��½���޷�ʹ�øù���", Toast.LENGTH_SHORT).show();
				return;
			}
			Intent intent = new Intent();
			intent.putExtra("PlanType", -1);
			intent.setClass(HomepageActivity_1.this,StaticPlanListActivity.class);
			HomepageActivity_1.this.startActivity(intent);

		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
