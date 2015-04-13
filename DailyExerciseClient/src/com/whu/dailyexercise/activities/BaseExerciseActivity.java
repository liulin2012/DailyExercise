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

	//��ϰ����ListView
	ListView list;
	//��ϰ���ఴť
	Button btn_exercisekinds;
	//������ϰ��ť
	Button btn_allexercises;
	Button btn_back;
	//��������
	ImageView line_1;
	ImageView line_2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_base_exercises_type);
		//������list
		CommonField.kindEntityList = MethodDao.getAllMethodInfo(0, CommonField.sqlitedatabasa);
		//��Layout�����ListView
		list = (ListView)findViewById(R.id.baseList);
		btn_back = (Button)findViewById(R.id.btnCancel_exerchoose);
		
		//�������飬������
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
		 //������������Item�Ͷ�̬�����Ӧ��Ԫ��  
        SimpleAdapter listItemAdapter = new SimpleAdapter(this,listItem,//����Դ   
            R.layout.listview_item_base_exercise,//ListItem��XMLʵ��  
            //��̬������ImageItem��Ӧ������          
            new String[] {"icon","tvExerChooseListItem"},   
            //ImageItem��XML�ļ������һ��ImageView,һ��TextView ID  
            new int[] {R.id.icon,R.id.tvExerChooseListItem}  
        );  
         
        //��Ӳ�����ʾ  
        list.setAdapter(listItemAdapter);  
        
		btn_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				BaseExerciseActivity.this.finish();
			}
		});
		
		//listview����¼�
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
