package com.whu.dailyexercise.activities;

import java.util.List;

import com.whu.dailyexercise.R;
import com.whu.dailyexercise.exercisemethod.MethodDao;
import com.whu.dailyexercise.util.CommonField;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TodayExericseActivity extends Activity {
	
	private Button btn_back;
	private Button btn_start;
	private TextView exerciseName;
	private TextView exerciseDes;
	private int iMethodIndex;
	private int iPlanId;
	private int iPlanIndex;
	private ImageView showImage;
	private Button btnVideo;
	private RelativeLayout shareChoose;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_single_exerciase);
		iMethodIndex = getIntent().getIntExtra("MethodIndex", -1);
		iPlanId = getIntent().getIntExtra("PlanId", -1);
		iPlanIndex = getIntent().getIntExtra("PlanIndex", iPlanIndex);
		showImage = (ImageView)findViewById(R.id.showImage);
		List<String> picList = MethodDao.getMethodPictureById(CommonField.todayChoosedMethodlist.get(iMethodIndex).getMethodid(), CommonField.sqlitedatabasa);
		
		if(picList.size() !=0)
		{
			String sMethodPic = picList.get(0);
			Resources res = getResources();
			Object ob=res.getIdentifier(sMethodPic,"drawable",getPackageName());
			showImage.setImageResource((Integer) ob);
		}
		
		//返回按钮
		btn_back = (Button)findViewById(R.id.btn_single_exercise_back);
		//开始训练按钮
		btn_start = (Button)findViewById(R.id.btnStartExercise);
		//标题
		exerciseName = (TextView)findViewById(R.id.tvStepName);
		exerciseName.setText(CommonField.todayChoosedMethodlist.get(iMethodIndex).getMethodname());
		//介绍
		exerciseDes = (TextView)findViewById(R.id.exercise_des);
		exerciseDes.setText(CommonField.todayChoosedMethodlist.get(iMethodIndex).getIntroduction());
		
		//返回按钮点击监听器
		btn_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				TodayExericseActivity.this.finish();
			}
		});
		//开始训练按钮点击监听器
		btn_start.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.putExtra("MethodIndex", iMethodIndex);
				intent.putExtra("PlanId", iPlanId);
				intent.putExtra("PlanIndex",iPlanIndex);
				intent.setClass(TodayExericseActivity.this,TodayExerciseTimerActivity.class);
				TodayExericseActivity.this.startActivity(intent);
			}
		});
		
		//新浪分享按钮
		shareChoose = (RelativeLayout)findViewById(R.id.top_sec_right_bg);
		shareChoose.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				Intent intent = new Intent();
				
				intent.setClass(TodayExericseActivity.this,ShareChooseActivity.class);
				TodayExericseActivity.this.startActivity(intent);
			}
			
		});
		
		btnVideo = (Button)findViewById(R.id.btnVideo);
		btnVideo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.putExtra("videoName", CommonField.todayChoosedMethodlist.get(iMethodIndex).getVideourl());
				intent.setClass(TodayExericseActivity.this,VideoActivity.class);
				TodayExericseActivity.this.startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.today_exericse, menu);
		return true;
	}

}
