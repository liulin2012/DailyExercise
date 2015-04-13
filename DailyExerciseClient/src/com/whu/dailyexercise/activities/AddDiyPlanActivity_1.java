package com.whu.dailyexercise.activities;
import java.util.ArrayList;
import com.whu.dailyexercise.R;
import com.whu.dailyexercise.activities.ExerciseDiyAdapter.ChildViewHolder;
import com.whu.dailyexercise.exercisemethod.GetAllMethodList;
import com.whu.dailyexercise.exercisemethod.MethodEntity;
import com.whu.dailyexercise.util.CommonField;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Toast;
import android.widget.ExpandableListView.OnChildClickListener;

public class AddDiyPlanActivity_1 extends Activity {

	//声明控件
	private Button btn_next_step;
	private ExpandableListView exList_Exercise;
	private ExerciseDiyAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_diy_plan_activity_1);
		//绑定控件
		btn_next_step = (Button)findViewById(R.id.add_diy_plan_1_btnNextStep);
		exList_Exercise =(ExpandableListView)findViewById(R.id.add_diy_plan_1_baseList);
	    GetAllMethodList methodlist = new GetAllMethodList(getResources(), getPackageName());
		adapter=new ExerciseDiyAdapter(this,methodlist);
		exList_Exercise.setAdapter(adapter);
		//点击事件监听器
		btn_next_step.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 更新选择的训练方法list
				int checknum = 0;
				CommonField.choosedList = new ArrayList<MethodEntity>();
				for(int i = 0;i<ExerciseDiyAdapter.ChildIsSelected.size();i++)
				{
					for(int j = 0;j<ExerciseDiyAdapter.ChildIsSelected.get(i).size();j++)
					{
						if(ExerciseDiyAdapter.ChildIsSelected.get(i).get(j))
						{
							CommonField.choosedList.add(CommonField.structEntityList.get(i).get(j));
							checknum++;
						}
					}
				}
				if(checknum!=0)
				{
					//跳转到添加计划界面2
					Intent intent = new Intent();
					intent.setClass(AddDiyPlanActivity_1.this,AddDiyPlanActivity_2.class);
					AddDiyPlanActivity_1.this.startActivity(intent);
				}
				else
				{
					Toast.makeText(AddDiyPlanActivity_1.this, "至少选择一项训练", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		exList_Exercise.setOnChildClickListener(new OnChildClickListener() {
			
			@Override
			public boolean onChildClick(ExpandableListView arg0, View arg1, int arg2,
					int arg3, long arg4) {
				// TODO Auto-generated method stub
				ChildViewHolder vHollder = (ChildViewHolder) arg1.getTag();
				vHollder.cBox.toggle();
				ExerciseDiyAdapter.ChildIsSelected.get(arg2).put(arg3, vHollder.cBox.isChecked());
				return false;
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_diy_plan_activity_1, menu);
		return true;
	}

}
