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
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class BaseExerciseListActivity extends Activity {
	Button btn_back;
	ListView list;
	TextView text_title;
	int iExerciseType;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_base_exercise_list);
		CommonField.structEntityList = MethodDao.getMethodCollection(CommonField.sqlitedatabasa);
		//��Layout�����ListView
		list = (ListView)findViewById(R.id.exercise_list_baseList);
		btn_back = (Button)findViewById(R.id.exercise_list_back);
		text_title = (TextView)findViewById(R.id.exercise_list_txtBase);
	    iExerciseType = getIntent().getIntExtra("ExerciseType", -1);
	    //�������飬������
  		ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
  		for(int i = 0;i < CommonField.structEntityList.get(iExerciseType).size();i++)
  		{
  			HashMap<String, Object> map = new HashMap<String,Object>();
  			map.put("tvExerChooseListItem", CommonField.structEntityList.get(iExerciseType).get(i).getMethodname());
  			Resources res = getResources();
  			Object ob=res.getIdentifier(CommonField.structEntityList.get(iExerciseType).get(i).getPictureurl(),"drawable",getPackageName());
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
	   
	    //listview����¼�
		list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
						Intent intent = new Intent();
						intent.putExtra("ExerciseType",iExerciseType);
						intent.putExtra("ExerciseItem",arg2);
						intent.setClass(BaseExerciseListActivity.this,SingleExerciaseActivity.class);
						BaseExerciseListActivity.this.startActivity(intent);
			}
		});
	    
		btn_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				BaseExerciseListActivity.this.finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.base_exercise_list, menu);
		return true;
	}

}
