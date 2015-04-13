package com.whu.dailyexercise.activities;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.whu.dailyexercise.R;
import com.whu.dailyexercise.exercisemethod.MethodDao;
import com.whu.dailyexercise.exercisemethod.MethodEntity;
import com.whu.dailyexercise.exerciseplan.EditPlan;
import com.whu.dailyexercise.exerciseplan.PlanEntity;
import com.whu.dailyexercise.util.CommonField;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class TodayPlanActivity extends Activity {
	
	private ListView list;
	private int iPlanIndex;
	private PlanEntity plan;
	private TextView title;
	private TextView des;
	private CheckBox check_day1;
	private CheckBox check_day2;
	private CheckBox check_day3;
	private CheckBox check_day4;
	private CheckBox check_day5;
	private CheckBox check_day6;
	private CheckBox check_day7;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_today_plan);
		
		check_day1 = (CheckBox)findViewById(R.id.checkday1);
		check_day2 = (CheckBox)findViewById(R.id.checkday2);
		check_day3 = (CheckBox)findViewById(R.id.checkday3);
		check_day4 = (CheckBox)findViewById(R.id.checkday4);
		check_day5 = (CheckBox)findViewById(R.id.checkday5);
		check_day6 = (CheckBox)findViewById(R.id.checkday6);
		check_day7 = (CheckBox)findViewById(R.id.checkday7);
		
		CheckBox[] checkArray = {check_day1,check_day2,check_day3,check_day4,check_day5,check_day6,check_day7};
		iPlanIndex = getIntent().getIntExtra("PlanIndex", -1);
		plan = CommonField.todayplanlist.get(iPlanIndex);
		String weekday = plan.getWeekday();
		String[] asWeek = weekday.split("-");
		for(int i = 1;i<asWeek.length;i++)
		{
			if(asWeek[i].equals("1"))
			{
				checkArray[i-1].setChecked(true);
				
			}
			else
			{
				checkArray[i-1].setChecked(false);
			}
		}
		title = (TextView)findViewById(R.id.lv_text);
		title.setText(plan.getPlanname());
		des = (TextView)findViewById(R.id.lv_desc);
		String temp = "��ʼʱ�䣺"+ plan.getBegindate()+" ����������"+plan.getWeeknumber();
		des.setText(temp);
		
		list = (ListView)findViewById(R.id.list_method);
		CommonField.todayChoosedMethodlist = MethodDao.getMethodInfoById(plan.getMethodid(), CommonField.sqlitedatabasa);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date today = new Date();
		String sToday = simpleDateFormat.format(today);

		int[] iMethodIsComplete = EditPlan.getDayExerciseCompleteByDate(plan.getPlanid(),sToday,CommonField.sqlitedatabasa);
		//�������飬������
		ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
		for(int i = 0;i < CommonField.todayChoosedMethodlist.size();i++)
		{
			HashMap<String, Object> map = new HashMap<String,Object>();
			Resources res = getResources();
			Object ob=res.getIdentifier(CommonField.todayChoosedMethodlist.get(i).getPictureurl(),"drawable",getPackageName());
			map.put("image_method", ob);
			map.put("method_name",CommonField.todayChoosedMethodlist.get(i).getMethodname());
			map.put("method_des",CommonField.todayChoosedMethodlist.get(i).getIntroduction());
			map.put("tv_play_clock", CommonField.todayChoosedMethodlist.get(i).getPractime());
			if(iMethodIsComplete[i]==1)
			{
				map.put("today_method_isComplete", "�����");
			}
			else
			{
				map.put("today_method_isComplete", "δ���");
			}
			listItem.add(map);
		}
		 //������������Item�Ͷ�̬�����Ӧ��Ԫ��  
        SimpleAdapter listItemAdapter = new SimpleAdapter(this,listItem,//����Դ   
            R.layout.listview_item_play_exercise,//ListItem��XMLʵ��  
            //��̬������ImageItem��Ӧ������          
            new String[] {"image_method","method_name","method_des","tv_play_clock","today_method_isComplete"},   
            //ImageItem��XML�ļ������һ��ImageView,һ��TextView ID  
            new int[] {R.id.image_method,R.id.method_name,R.id.method_des,R.id.tv_play_clock,R.id.today_method_isComplete}  
        );  
         
        //��Ӳ�����ʾ  
        list.setAdapter(listItemAdapter);
        
        list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
  				intent.putExtra("MethodIndex",arg2);
  				intent.putExtra("PlanId", plan.getPlanid());
  				intent.putExtra("PlanIndex",iPlanIndex);
  				intent.setClass(TodayPlanActivity.this,TodayExericseActivity.class);
  				TodayPlanActivity.this.startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.today_plan, menu);
		return true;
	}

}
