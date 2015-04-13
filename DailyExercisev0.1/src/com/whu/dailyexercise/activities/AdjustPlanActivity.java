package com.whu.dailyexercise.activities;

import java.util.ArrayList;
import java.util.HashMap;

import com.whu.dailyexercise.R;
import com.whu.dailyexercise.exerciseplan.EditPlan;
import com.whu.dailyexercise.util.CommonField;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class AdjustPlanActivity extends Activity {

	//计划ListView
	private ListView list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_adjust_plan);
		
		//获取所有训练项目
		CommonField.allPlanlist = EditPlan.getAllPlanInfo(CommonField.userId, CommonField.sqlitedatabasa);
		//绑定Layout里面的ListView
		list = (ListView)findViewById(R.id.list_allplan);
			
		ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
		for(int i = 0;i < CommonField.allPlanlist.size();i++)
		{
			HashMap<String, Object> map = new HashMap<String,Object>();
			String temp = "开始时间："+ CommonField.allPlanlist.get(i).getBegindate()+" 持续周数："+CommonField.allPlanlist.get(i).getWeeknumber();
			map.put("lv_text", CommonField.allPlanlist.get(i).getPlanname());
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
        list.setAdapter(listItemAdapter);  
        
        //listview点击事件
  		list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
  			@Override
  			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
  					long arg3) {
  				// TODO Auto-generated method stub
  				Intent intent = new Intent();
  				intent.putExtra("PlanIndex",arg2);
  				intent.setClass(AdjustPlanActivity.this,AdjustSinglePlanActivity.class);
  				AdjustPlanActivity.this.startActivity(intent);
  			}
  		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.adjust_plan, menu);
		return true;
	}

}
