package com.whu.dailyexercise.activities;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import com.whu.dailyexercise.exerciseplan.LogicEditPlan;
import com.whu.dailyexercise.util.CommonField;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.whu.dailyexercise.R;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.Toast;

public class AddDiyPlanActivity_3 extends Activity {
	
	private int iYear;
	private int iMonth;
	private int iDay;
	private int iWeekNum;
	private EditText planName;
	private DatePicker dp;
	private NumberPicker np_WeekNum;
	private CheckBox check_day1;
	private CheckBox check_day2;
	private CheckBox check_day3;
	private CheckBox check_day4;
	private CheckBox check_day5;
	private CheckBox check_day6;
	private CheckBox check_day7;
	private int[] aiWeek;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_diy_plan_activity_3);
		
		planName = (EditText)findViewById(R.id.editPlanname);
		aiWeek = new int[7];
		check_day1 = (CheckBox)findViewById(R.id.checkday1);
		check_day2 = (CheckBox)findViewById(R.id.checkday2);
		check_day3 = (CheckBox)findViewById(R.id.checkday3);
		check_day4 = (CheckBox)findViewById(R.id.checkday4);
		check_day5 = (CheckBox)findViewById(R.id.checkday5);
		check_day6 = (CheckBox)findViewById(R.id.checkday6);
		check_day7 = (CheckBox)findViewById(R.id.checkday7);
		
		check_day1.setOnCheckedChangeListener(new CheckBoxOnCheckedChangeListener());
		check_day2.setOnCheckedChangeListener(new CheckBoxOnCheckedChangeListener());
		check_day3.setOnCheckedChangeListener(new CheckBoxOnCheckedChangeListener());
		check_day4.setOnCheckedChangeListener(new CheckBoxOnCheckedChangeListener());
		check_day5.setOnCheckedChangeListener(new CheckBoxOnCheckedChangeListener());
		check_day6.setOnCheckedChangeListener(new CheckBoxOnCheckedChangeListener());
		check_day7.setOnCheckedChangeListener(new CheckBoxOnCheckedChangeListener());

		dp = (DatePicker)findViewById(R.id.datePicker1);
		resizeDatePicker(dp);
		Calendar c =Calendar.getInstance();  
        iYear = c.get(Calendar.YEAR);  
        iMonth=c.get(Calendar.MONTH);  
        iDay=c.get(Calendar.DAY_OF_MONTH);  

        dp.init(iYear, iMonth, iDay, new OnDateChangedListener() {  
            @Override  
            public void onDateChanged(DatePicker view, int year, int monthOfYear,int dayOfMonth) {  
		        iYear=year;  
		        iMonth=monthOfYear;  
		        iDay=dayOfMonth;  
            }  
        });  

		np_WeekNum = (NumberPicker) findViewById(R.id.weeknum);  
		np_WeekNum.setMaxValue(8);  
		np_WeekNum.setMinValue(1);  
		np_WeekNum.setValue(4);
		iWeekNum = np_WeekNum.getValue();
		np_WeekNum.setOnValueChangedListener(new OnValueChangeListener() {
			
			@Override
			public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
				// TODO Auto-generated method stub
				iWeekNum = newVal;
			}
		});
		
		Button btn_complete = (Button)findViewById(R.id.btnComplete);
		btn_complete.setOnClickListener(new OnClickListener() {
			
			@SuppressLint("SimpleDateFormat")
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//��Ӽƻ�
				@SuppressWarnings("deprecation")
				Date dStartDate= new Date(iYear-1900,iMonth,iDay);
				Date dNowDate = new Date();
				int week = iWeekNum;
				int[] ai = aiWeek;
				int inum = 0;
				for(int i = 0;i<7;i++)
				{
					if(ai[i] != 0)
					{
						inum++;
					}
				}
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
			    String s1 = sdf.format(dStartDate);  
			    String s2 = sdf.format(dNowDate); 
				if(dStartDate.after(dNowDate)||s1.equals(s2))
				{
					
					if(inum != 0){
						String planname_temp = planName.getText().toString().trim();
						if(planname_temp.equals(""))
						{
							Toast.makeText(AddDiyPlanActivity_3.this, "������ƻ�����", Toast.LENGTH_SHORT).show();
						}
						else
						{
							int iReturn;
							iReturn = LogicEditPlan.addPlan(CommonField.finallist, dStartDate, week, ai,planname_temp);
							if(iReturn == 1)
							{
								//��ӳɹ�����������
								Intent intent = new Intent(AddDiyPlanActivity_3.this, HomepageActivity_1.class);
						        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						        AddDiyPlanActivity_3.this.startActivity(intent);
						        Toast.makeText(AddDiyPlanActivity_3.this, "�ƻ���ӳɹ�", Toast.LENGTH_SHORT).show();
							}
				        }
					}
					else  //checkboxδѡ��
					{
						Toast.makeText(AddDiyPlanActivity_3.this, "ÿ���ظ�������ѡ��һ�� ", Toast.LENGTH_SHORT).show();
					}
				}
				else
				{
					Toast.makeText(AddDiyPlanActivity_3.this, "�޷��趨��ȥ��һ��Ϊ��ʼ����������", Toast.LENGTH_SHORT).show();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_diy_plan_activity_3, menu);
		return true;
	}
	
	 private List<NumberPicker> findNumberPicker(ViewGroup viewGroup)
	    {
	        List<NumberPicker> npList = new ArrayList<NumberPicker>();
	        View child = null;
	        
	        if (null != viewGroup)
	        {
	            for (int i = 0; i < viewGroup.getChildCount(); i++)
	            {
	                child = viewGroup.getChildAt(i);
	                if (child instanceof NumberPicker)
	                {
	                    npList.add((NumberPicker)child);
	                }
	                else if (child instanceof LinearLayout)
	                {
	                    List<NumberPicker> result = findNumberPicker((ViewGroup)child);
	                    if (result.size() > 0)
	                    {
	                        return result;
	                    }
	                }
	            }
	        }
	        
	        return npList;
	    }
	 
	private void resizeDatePicker(DatePicker dp)
    {
        List<NumberPicker> npList = findNumberPicker(dp);
        
        for (NumberPicker np : npList)
        {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(100, LayoutParams.WRAP_CONTENT);
            params.setMargins(50, 0, 50, 0);
            params.width = 200;
            np.setLayoutParams(params);

        }
    }
	
	class CheckBoxOnCheckedChangeListener implements OnCheckedChangeListener{

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			// TODO Auto-generated method stub
			switch(buttonView.getId())
			{
				case R.id.checkday1:
					if(isChecked)
					{
						aiWeek[0] = 1;
					}
					else
					{
						aiWeek[0] = 0;
					}
					break;
				case R.id.checkday2:
					if(isChecked)
					{
						aiWeek[1] = 1;
					}
					else
					{
						aiWeek[1] = 0;
					}
					break;
				case R.id.checkday3:
					if(isChecked)
					{
						aiWeek[2] = 1;
					}
					else
					{
						aiWeek[2] = 0;
					}
					break;
				case R.id.checkday4:
					if(isChecked)
					{
						aiWeek[3] = 1;
					}
					else
					{
						aiWeek[3] = 0;
					}
					break;
				case R.id.checkday5:
					if(isChecked)
					{
						aiWeek[4] = 1;
					}
					else
					{
						aiWeek[4] = 0;
					}
					break;
				case R.id.checkday6:
					if(isChecked)
					{
						aiWeek[5] = 1;
					}
					else
					{
						aiWeek[5] = 0;
					}
					break;
				case R.id.checkday7:
					if(isChecked)
					{
						aiWeek[6] = 1;
					}
					else
					{
						aiWeek[6] = 0;
					}
					break;
				default:
					break;
					
			}
		}
		
	}
}
