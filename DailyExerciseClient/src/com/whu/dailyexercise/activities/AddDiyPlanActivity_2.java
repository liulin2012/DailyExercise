package com.whu.dailyexercise.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.whu.dailyexercise.R;
import com.whu.dailyexercise.activities.ExerciseChooseAdapter.ViewHolder;
import com.whu.dailyexercise.exercisemethod.MethodEntity;
import com.whu.dailyexercise.util.CommonField;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class AddDiyPlanActivity_2 extends Activity {

	//声明控件
	private Button btn_next_step;
	private ListView list;
	private List<Map<String, Object>> data; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_diy_plan_activity_2);
		//绑定控件
		btn_next_step = (Button)findViewById(R.id.add_diy_plan_2_btnNextStep);
		list = (ListView)findViewById(R.id.add_diy_plan_2_baseList);
		//点击事件监听器
		btn_next_step.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				CommonField.finallist = new ArrayList<MethodEntity>();
				int checknum = 0;
				 for(int i=0;i<list.getCount();i++)
				 {
					 if(ExerciseChooseAdapter.isSelected.get(i))
					 {
						 CommonField.finallist.add(CommonField.choosedList.get(i));
						 checknum++;
					 }
				 }
				 if(checknum!= 0)
				 {
					//跳转到添加计划界面3
					Intent intent = new Intent();
					intent.setClass(AddDiyPlanActivity_2.this,AddDiyPlanActivity_3.class);
					AddDiyPlanActivity_2.this.startActivity(intent);
				 }
				 else
				 {
					 Toast.makeText(AddDiyPlanActivity_2.this, "至少选择一项训练", Toast.LENGTH_SHORT).show();
				 }
			}
		});
		
		//获得ListView显示数据
		data = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < CommonField.choosedList.size(); i++) {    
            Map<String, Object> map = new HashMap<String, Object>();  
            Resources res = getResources();
            Object ob=res.getIdentifier(CommonField.choosedList.get(i).getPictureurl(),"drawable",getPackageName());
            map.put("img", ob);    
            map.put("title", CommonField.choosedList.get(i).getMethodname());    
            data.add(map);    
        } 
		//绑定适配器
		ExerciseChooseAdapter adapter=new ExerciseChooseAdapter(this,data);    
        list.setAdapter(adapter);    
        list.setItemsCanFocus(false);    
        list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);    
        //ListView点击事件监听器
        list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				ViewHolder vHollder = (ViewHolder) arg1.getTag();
				vHollder.cBox.toggle();
				ExerciseChooseAdapter.isSelected.put(arg2, vHollder.cBox.isChecked()); 
			}
        
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_diy_plan_activity_2, menu);
		return true;
	}

}
