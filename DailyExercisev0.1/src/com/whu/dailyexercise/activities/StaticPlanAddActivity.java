package com.whu.dailyexercise.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.whu.dailyexercise.R;

import com.whu.dailyexercise.exercisemethod.MethodDao;
import com.whu.dailyexercise.exercisemethod.MethodEntity;

import com.whu.dailyexercise.exerciseplan.StaticPlanEntity;
import com.whu.dailyexercise.util.CommonField;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class StaticPlanAddActivity extends Activity {
	
	private ListView list;
	private int iPlanIndex;
	private StaticPlanEntity plan;
	private ImageView img;
	private TextView title;
	private TextView flag;
	private TextView des;
	private Button btn_add_plan;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_static_plan_add);
		
		//�󶨰�ť
		btn_add_plan = (Button)findViewById(R.id.btnAddPlan);
		iPlanIndex = getIntent().getIntExtra("PlanIndex", -1);
		plan = CommonField.choosedPlanList.get(iPlanIndex);
		
		title = (TextView)findViewById(R.id.lv_text);
		title.setText(plan.getPlanname());
		flag = (TextView)findViewById(R.id.lv_desc);
		flag.setText(plan.getPlanflag());
		des = (TextView)findViewById(R.id.plan_des);
		des.setText(plan.getPlanintroduction());
		
		img = (ImageView)findViewById(R.id.img_plan);
		Resources res = getResources();
		Object ob=res.getIdentifier(plan.getPictureurl(),"drawable",getPackageName());
		img.setImageResource((Integer) ob);
		
		list = (ListView)findViewById(R.id.list_method);
		List<MethodEntity> methodlist = MethodDao.getMethodInfoById(plan.getMethodid(), CommonField.sqlitedatabasa);
		CommonField.finallist = methodlist;
		//�������飬������
		ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
		for(int i = 0;i < methodlist.size();i++)
		{
			HashMap<String, Object> map = new HashMap<String,Object>();
			Resources res1 = getResources();
			Object ob1=res1.getIdentifier(methodlist.get(i).getPictureurl(),"drawable",getPackageName());
			map.put("image_method", ob1);
			map.put("method_name",methodlist.get(i).getMethodname());
			map.put("method_des",methodlist.get(i).getIntroduction());
			map.put("tv_play_clock", methodlist.get(i).getPractime());
			listItem.add(map);
		}
		//������������Item�Ͷ�̬�����Ӧ��Ԫ��  
        SimpleAdapter listItemAdapter = new SimpleAdapter(this,listItem,//����Դ   
            R.layout.listview_item_show_execise,//ListItem��XMLʵ��  
            //��̬������ImageItem��Ӧ������          
            new String[] {"image_method","method_name","method_des","tv_play_clock"},   
            //ImageItem��XML�ļ������һ��ImageView,һ��TextView ID  
            new int[] {R.id.image_method,R.id.method_name,R.id.method_des,R.id.tv_play_clock}  
        );  
         
        //��Ӳ�����ʾ  
        list.setAdapter(listItemAdapter);
        
        btn_add_plan.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.putExtra("PlanName", plan.getPlanname());
				intent.setClass(StaticPlanAddActivity.this,StaticPlanAddStepActivity.class);
				StaticPlanAddActivity.this.startActivity(intent);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.static_plan_add, menu);
		return true;
	}

}
