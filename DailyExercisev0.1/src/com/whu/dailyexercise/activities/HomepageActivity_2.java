package com.whu.dailyexercise.activities;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.whu.dailyexercise.R;
import com.whu.dailyexercise.exerciseplan.EditPlan;
import com.whu.dailyexercise.util.CommonField;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

@SuppressLint("SimpleDateFormat")
public class HomepageActivity_2 extends Activity {

	private LinearLayout btn_homepage;  //�ҵļƻ���ť
	private LinearLayout btn_adjustplan;  //�ƻ�������ť
	private LinearLayout btn_exercisedata;  //ѵ��ͳ�ư�ť
	private LinearLayout btn_usercenter;  //�û����İ�ť
	private LinearLayout btn_visualimage;  //��������ť
	
	private ViewPager viewPager;//��ҳ��ͼƬ�����ؼ�
	private List<View> list;//��ʾ�������ص�view��list����ʵ��layout��tabҳ��list
	private ListView list_plan;
	
	private TextView page2_notice;
	private TextView pleaseLogin;
	private TextView UserName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_homepage_activity_2);
		
		//�󶨿ؼ�
		UserName=(TextView)findViewById(R.id.tv_app_home_hello_user_2);
		pleaseLogin=(TextView)findViewById(R.id.tv_app_home_hello_pleaselogin_2);
		if(CommonField.loginOK)
		{
			pleaseLogin.setText("�ѵ�¼");
			UserName.setText(CommonField.userId);
		}
		//�󶨿ؼ�
		btn_homepage = (LinearLayout)findViewById(R.id.btnHomePageCenter);
		btn_usercenter = (LinearLayout)findViewById(R.id.llHomepageUserCenter);
		btn_adjustplan = (LinearLayout)findViewById(R.id.llHomepageAdjustPlan);
		btn_exercisedata = (LinearLayout)findViewById(R.id.llHomepageExerciseData);
		btn_visualimage = (LinearLayout)findViewById(R.id.llHomepageVisualImage);
		//�󶨵���¼�������
		btn_homepage.setOnClickListener(new BtnBackHomepageClickListener());
		btn_usercenter.setOnClickListener(new BtnUserCenterClickListener());
		btn_adjustplan.setOnClickListener(new BtnAdjustPlanClickListener());
		btn_exercisedata.setOnClickListener(new BtnExerciseDataClickListener());
		btn_visualimage.setOnClickListener(new BtnVisualImageClickListener());
		
		viewPager = (ViewPager)findViewById(R.id.viewpager_homepage);
		//��̬���ز���
		View view1 = LayoutInflater.from(HomepageActivity_2.this).inflate(R.layout.viewpager_homepage_tab1,null);
		View view2 = LayoutInflater.from(HomepageActivity_2.this).inflate(R.layout.viewpager_homepage_tab2,null);
		View view3 = LayoutInflater.from(HomepageActivity_2.this).inflate(R.layout.viewpager_homepage_tab3,null);
		
		list = new ArrayList<View>();
		list.add(view1);
		list.add(view2);
		list.add(view3);
		
		viewPager.setAdapter(new MyAdapter());
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date today = new Date();
		String sToday = simpleDateFormat.format(today);
		//��ȡ����ѵ����Ŀ
		CommonField.todayplanlist = EditPlan.getPlanByDate(CommonField.userId,sToday,CommonField.sqlitedatabasa);
		//��Layout�����ListView
		list_plan = (ListView)findViewById(R.id.today_plan_list);
			
		ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
		for(int i = 0;i < CommonField.todayplanlist.size();i++)
		{
			HashMap<String, Object> map = new HashMap<String,Object>();
			String temp = "��ʼʱ�䣺"+ CommonField.todayplanlist.get(i).getBegindate()+" ����������"+CommonField.todayplanlist.get(i).getWeeknumber();
			map.put("lv_text", CommonField.todayplanlist.get(i).getPlanname());
			map.put("lv_desc",temp);
			listItem.add(map);
		}
		//������������Item�Ͷ�̬�����Ӧ��Ԫ��  
        SimpleAdapter listItemAdapter = new SimpleAdapter(this,listItem,//����Դ   
            R.layout.listview_item_allplan,//ListItem��XMLʵ��  
            //��̬������ImageItem��Ӧ������          
            new String[] {"lv_text","lv_desc"},   
            //ImageItem��XML�ļ������һ��ImageView,һ��TextView ID  
            new int[] {R.id.lv_text,R.id.lv_desc}  
        );  
         
        //��Ӳ�����ʾ  
        list_plan.setAdapter(listItemAdapter);  
        
        //listview����¼�
        list_plan.setOnItemClickListener(new AdapterView.OnItemClickListener(){
  			@Override
  			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
  					long arg3) {
  				// TODO Auto-generated method stub
  				Intent intent = new Intent();
  				intent.putExtra("PlanIndex",arg2);
  				intent.setClass(HomepageActivity_2.this,TodayPlanActivity.class);
  				HomepageActivity_2.this.startActivity(intent);
  			}
  		});
        
        page2_notice = (TextView)findViewById(R.id.homepage2_notice);
		String notice =sToday+"  "+"��������"+CommonField.todayplanlist.size()+"��ѵ���ƻ�";
		page2_notice.setText(notice);
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
			
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.homepage_activity_2, menu);
		return true;
	}
	
	//��ҳ��ť���������
	class BtnBackHomepageClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			finish();
			overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);  
		}
	}
	
    //�ƻ�������ť
	class BtnAdjustPlanClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(HomepageActivity_2.this,AdjustPlanActivity.class);
			HomepageActivity_2.this.startActivity(intent);    
		}
	}
	//ѵ��ͳ�ư�ť
	class BtnExerciseDataClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(HomepageActivity_2.this,ExerciseDataActivity.class);
			HomepageActivity_2.this.startActivity(intent);   
		}
	}
	//�û����İ�ť
	class BtnUserCenterClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(HomepageActivity_2.this,UserCenterActivity.class);
			HomepageActivity_2.this.startActivity(intent);  
		}
	}
	//��������ť
	class BtnVisualImageClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(HomepageActivity_2.this,VisualImageActivity.class);
			HomepageActivity_2.this.startActivity(intent);    
		}
	}

}
