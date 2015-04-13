package com.whu.dailyexercise.activities;

import java.util.ArrayList;
import java.util.HashMap;

import com.whu.dailyexercise.R;
import com.whu.dailyexercise.exercisemethod.MethodDao;
import com.whu.dailyexercise.util.CommonField;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class BaseExerciseActivity extends Activity {

	//练习分类ListView
	ListView list;
	//练习分类按钮
	Button btn_exercisekinds;
	//所有练习按钮
	Button btn_allexercises;
	Button btn_back;
	//两条横线
	ImageView line_1;
	ImageView line_2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_base_exercises_type);
		//获得类别list
		CommonField.kindEntityList = MethodDao.getAllMethodInfo(0, CommonField.sqlitedatabasa);
		//绑定Layout里面的ListView
		list = (ListView)findViewById(R.id.baseList);
		btn_back = (Button)findViewById(R.id.btnCancel_exerchoose);
		
		//生成数组，绑定数据
		ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
		for(int i = 0;i < CommonField.kindEntityList.size();i++)
		{
			HashMap<String, Object> map = new HashMap<String,Object>();
			map.put("tvExerChooseListItem", CommonField.kindEntityList.get(i).getMethodname());
			Resources res = getResources();
			Object ob=res.getIdentifier(CommonField.kindEntityList.get(i).getPictureurl(),"drawable",getPackageName());
			map.put("icon",ob);
			listItem.add(map);
		}
		 //生成适配器的Item和动态数组对应的元素  
        SimpleAdapter listItemAdapter = new SimpleAdapter(this,listItem,//数据源   
            R.layout.listview_item_base_exercise,//ListItem的XML实现  
            //动态数组与ImageItem对应的子项          
            new String[] {"icon","tvExerChooseListItem"},   
            //ImageItem的XML文件里面的一个ImageView,一个TextView ID  
            new int[] {R.id.icon,R.id.tvExerChooseListItem}  
        );  
         
        //添加并且显示  
        list.setAdapter(listItemAdapter);  
        
		btn_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				BaseExerciseActivity.this.finish();
			}
		});
		
		//listview点击事件
		list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.putExtra("ExerciseType",arg2);
				intent.setClass(BaseExerciseActivity.this,BaseExerciseListActivity.class);
				BaseExerciseActivity.this.startActivity(intent);
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.base_exercise, menu);
		return true;
	}

}
