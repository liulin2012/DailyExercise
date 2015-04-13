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

	//�ƻ�ListView
	private ListView list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_adjust_plan);
		
		//��ȡ����ѵ����Ŀ
		CommonField.allPlanlist = EditPlan.getAllPlanInfo(CommonField.userId, CommonField.sqlitedatabasa);
		//��Layout�����ListView
		list = (ListView)findViewById(R.id.list_allplan);
			
		ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
		for(int i = 0;i < CommonField.allPlanlist.size();i++)
		{
			HashMap<String, Object> map = new HashMap<String,Object>();
			String temp = "��ʼʱ�䣺"+ CommonField.allPlanlist.get(i).getBegindate()+" ����������"+CommonField.allPlanlist.get(i).getWeeknumber();
			map.put("lv_text", CommonField.allPlanlist.get(i).getPlanname());
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
        list.setAdapter(listItemAdapter);  
        
        //listview����¼�
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
