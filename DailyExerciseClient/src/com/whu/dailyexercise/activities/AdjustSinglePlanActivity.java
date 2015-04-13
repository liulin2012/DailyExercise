package com.whu.dailyexercise.activities;

import java.util.ArrayList;
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
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class AdjustSinglePlanActivity extends Activity {

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
	private Button btn_modify_name;
	private Button btn_del_plan;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_adjust_single_plan);
		
		check_day1 = (CheckBox)findViewById(R.id.checkday1);
		check_day2 = (CheckBox)findViewById(R.id.checkday2);
		check_day3 = (CheckBox)findViewById(R.id.checkday3);
		check_day4 = (CheckBox)findViewById(R.id.checkday4);
		check_day5 = (CheckBox)findViewById(R.id.checkday5);
		check_day6 = (CheckBox)findViewById(R.id.checkday6);
		check_day7 = (CheckBox)findViewById(R.id.checkday7);
		
		//绑定按钮
		btn_modify_name = (Button)findViewById(R.id.btnModifyName);
		btn_del_plan = (Button)findViewById(R.id.btnDelPlan);
		
		CheckBox[] checkArray = {check_day1,check_day2,check_day3,check_day4,check_day5,check_day6,check_day7};
		iPlanIndex = getIntent().getIntExtra("PlanIndex", -1);
		plan = CommonField.allPlanlist.get(iPlanIndex);
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
		String temp = "开始时间："+ plan.getBegindate()+" 持续周数："+plan.getWeeknumber();
		des.setText(temp);
		list = (ListView)findViewById(R.id.list_method);
		List<MethodEntity> methodlist = MethodDao.getMethodInfoById(plan.getMethodid(), CommonField.sqlitedatabasa);
		//生成数组，绑定数据
		ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
		for(int i = 0;i < methodlist.size();i++)
		{
			HashMap<String, Object> map = new HashMap<String,Object>();
			Resources res = getResources();
			Object ob=res.getIdentifier(methodlist.get(i).getPictureurl(),"drawable",getPackageName());
			map.put("image_method", ob);
			map.put("method_name",methodlist.get(i).getMethodname());
			map.put("method_des",methodlist.get(i).getIntroduction());
			map.put("tv_play_clock", methodlist.get(i).getPractime());
			listItem.add(map);
		}
		 //生成适配器的Item和动态数组对应的元素  
        SimpleAdapter listItemAdapter = new SimpleAdapter(this,listItem,//数据源   
            R.layout.listview_item_show_execise,//ListItem的XML实现  
            //动态数组与ImageItem对应的子项          
            new String[] {"image_method","method_name","method_des","tv_play_clock"},   
            //ImageItem的XML文件里面的一个ImageView,一个TextView ID  
            new int[] {R.id.image_method,R.id.method_name,R.id.method_des,R.id.tv_play_clock}  
        );  
         
        //添加并且显示  
        list.setAdapter(listItemAdapter);  
        
        btn_del_plan.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int iReturn;
				iReturn = EditPlan.deletePlan(plan.getPlanid(), CommonField.sqlitedatabasa);
				if(iReturn == 1)
				{
					Intent intent = new Intent(AdjustSinglePlanActivity.this, AdjustPlanActivity.class);
			        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			        AdjustSinglePlanActivity.this.startActivity(intent);
					Toast.makeText(AdjustSinglePlanActivity.this, "计划删除成功", Toast.LENGTH_SHORT).show();
				}
				else
				{
					Toast.makeText(AdjustSinglePlanActivity.this, "计划删除失败", Toast.LENGTH_SHORT).show();
				}
			}
		});
        
        btn_modify_name.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				EditNameDlg dialog = new EditNameDlg(AdjustSinglePlanActivity.this,new EditNameDlg.OnCustomDialogListener() {
	                    
	                    @Override
	                    public void back(String name) {
	                           int iRes;
	                           iRes = EditPlan.updateNamePlan(plan.getPlanid(),name,CommonField.sqlitedatabasa);
	                           if(iRes == 1)
		           				{
		           					Intent intent = new Intent(AdjustSinglePlanActivity.this, AdjustPlanActivity.class);
		           			        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		           			        AdjustSinglePlanActivity.this.startActivity(intent);
		           					Toast.makeText(AdjustSinglePlanActivity.this, "名称修改成功", Toast.LENGTH_SHORT).show();
		           				}
		           				else
		           				{
		           					Toast.makeText(AdjustSinglePlanActivity.this, "名称修改失败", Toast.LENGTH_SHORT).show();
		           				}
	                            
	                    }
	            },"修改计划名称");
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	            dialog.show();
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.adjust_single_plan, menu);
		return true;
	}

}
