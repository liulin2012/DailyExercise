package com.whu.dailyexercise.activities;

import java.util.ArrayList;
import java.util.HashMap;

import com.whu.dailyexercise.R;
import com.whu.dailyexercise.exerciseplan.StaticPlanHelper;
import com.whu.dailyexercise.util.CommonField;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class StaticPlanListActivity extends Activity {

	//计划ListView
	private ListView list;
	private int iPlanType;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_static_plan_list);
		iPlanType = getIntent().getIntExtra("PlanType", -1);
		CommonField.choosedPlanList = StaticPlanHelper.getStaticPlanListByType(iPlanType, CommonField.sqlitedatabasa);
		
		list = (ListView)findViewById(R.id.list_static_plan);
		
		ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
		for(int i = 0;i < CommonField.choosedPlanList.size();i++)
		{
			HashMap<String, Object> map = new HashMap<String,Object>();
			Resources res = getResources();
  			Object ob=res.getIdentifier(CommonField.choosedPlanList.get(i).getPictureurl(),"drawable",getPackageName());
  			map.put("plan_img",ob);
			map.put("lv_text", CommonField.choosedPlanList.get(i).getPlanname());
			map.put("lv_desc",CommonField.choosedPlanList.get(i).getPlanflag());
			listItem.add(map);
		}
		//生成适配器的Item和动态数组对应的元素  
        SimpleAdapter listItemAdapter = new SimpleAdapter(this,listItem,//数据源   
            R.layout.listview_item_allplan,//ListItem的XML实现  
            //动态数组与ImageItem对应的子项          
            new String[] {"plan_img","lv_text","lv_desc"},   
            //ImageItem的XML文件里面的一个ImageView,一个TextView ID  
            new int[] {R.id.plan_img,R.id.lv_text,R.id.lv_desc}  
        );  
         
        //添加并且显示  
        list.setAdapter(listItemAdapter);  
        
        //listview点击事件
  		list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
  			@Override
  			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
  					long arg3) {
  				// TODO Auto-generated method stub
  				Intent intent = new Intent();
  				intent.putExtra("PlanIndex",arg2);
  				intent.setClass(StaticPlanListActivity.this,StaticPlanAddActivity.class);
  				StaticPlanListActivity.this.startActivity(intent);
  			}
  		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.static_plan_list, menu);
		return true;
	}

}
