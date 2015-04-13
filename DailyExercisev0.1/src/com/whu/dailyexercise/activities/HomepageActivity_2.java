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

	private LinearLayout btn_homepage;  //我的计划按钮
	private LinearLayout btn_adjustplan;  //计划调整按钮
	private LinearLayout btn_exercisedata;  //训练统计按钮
	private LinearLayout btn_usercenter;  //用户中心按钮
	private LinearLayout btn_visualimage;  //虚拟形象按钮
	
	private ViewPager viewPager;//主页的图片滑动控件
	private List<View> list;//表示滑动加载的view的list，其实是layout的tab页的list
	private ListView list_plan;
	
	private TextView page2_notice;
	private TextView pleaseLogin;
	private TextView UserName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_homepage_activity_2);
		
		//绑定控件
		UserName=(TextView)findViewById(R.id.tv_app_home_hello_user_2);
		pleaseLogin=(TextView)findViewById(R.id.tv_app_home_hello_pleaselogin_2);
		if(CommonField.loginOK)
		{
			pleaseLogin.setText("已登录");
			UserName.setText(CommonField.userId);
		}
		//绑定控件
		btn_homepage = (LinearLayout)findViewById(R.id.btnHomePageCenter);
		btn_usercenter = (LinearLayout)findViewById(R.id.llHomepageUserCenter);
		btn_adjustplan = (LinearLayout)findViewById(R.id.llHomepageAdjustPlan);
		btn_exercisedata = (LinearLayout)findViewById(R.id.llHomepageExerciseData);
		btn_visualimage = (LinearLayout)findViewById(R.id.llHomepageVisualImage);
		//绑定点击事件监听器
		btn_homepage.setOnClickListener(new BtnBackHomepageClickListener());
		btn_usercenter.setOnClickListener(new BtnUserCenterClickListener());
		btn_adjustplan.setOnClickListener(new BtnAdjustPlanClickListener());
		btn_exercisedata.setOnClickListener(new BtnExerciseDataClickListener());
		btn_visualimage.setOnClickListener(new BtnVisualImageClickListener());
		
		viewPager = (ViewPager)findViewById(R.id.viewpager_homepage);
		//动态加载布局
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
		//获取所有训练项目
		CommonField.todayplanlist = EditPlan.getPlanByDate(CommonField.userId,sToday,CommonField.sqlitedatabasa);
		//绑定Layout里面的ListView
		list_plan = (ListView)findViewById(R.id.today_plan_list);
			
		ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
		for(int i = 0;i < CommonField.todayplanlist.size();i++)
		{
			HashMap<String, Object> map = new HashMap<String,Object>();
			String temp = "开始时间："+ CommonField.todayplanlist.get(i).getBegindate()+" 持续周数："+CommonField.todayplanlist.get(i).getWeeknumber();
			map.put("lv_text", CommonField.todayplanlist.get(i).getPlanname());
			map.put("lv_desc",temp);
			listItem.add(map);
		}
		//生成适配器的Item和动态数组对应的元素  
        SimpleAdapter listItemAdapter = new SimpleAdapter(this,listItem,//数据源   
            R.layout.listview_item_allplan,//ListItem的XML实现  
            //动态数组与ImageItem对应的子项          
            new String[] {"lv_text","lv_desc"},   
            //ImageItem的XML文件里面的一个ImageView,一个TextView ID  
            new int[] {R.id.lv_text,R.id.lv_desc}  
        );  
         
        //添加并且显示  
        list_plan.setAdapter(listItemAdapter);  
        
        //listview点击事件
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
		String notice =sToday+"  "+"今天您有"+CommonField.todayplanlist.size()+"个训练计划";
		page2_notice.setText(notice);
	}

	 //viewpager的适配器的方法重写
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
	
	//首页按钮点击监听器
	class BtnBackHomepageClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			finish();
			overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);  
		}
	}
	
    //计划调整按钮
	class BtnAdjustPlanClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(HomepageActivity_2.this,AdjustPlanActivity.class);
			HomepageActivity_2.this.startActivity(intent);    
		}
	}
	//训练统计按钮
	class BtnExerciseDataClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(HomepageActivity_2.this,ExerciseDataActivity.class);
			HomepageActivity_2.this.startActivity(intent);   
		}
	}
	//用户中心按钮
	class BtnUserCenterClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(HomepageActivity_2.this,UserCenterActivity.class);
			HomepageActivity_2.this.startActivity(intent);  
		}
	}
	//虚拟形象按钮
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
