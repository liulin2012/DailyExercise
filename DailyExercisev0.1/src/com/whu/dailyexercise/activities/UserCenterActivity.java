package com.whu.dailyexercise.activities;

import com.whu.dailyexercise.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

public class UserCenterActivity extends Activity {
	
	private LinearLayout cloudshare;
	private LinearLayout selfinfo;
	private LinearLayout config;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_center);
		cloudshare=(LinearLayout)findViewById(R.id.llPersonalCenterCloud);
		cloudshare.setOnClickListener(new OnClickListener(){
			public void onClick(View v)
			{
				Intent intent=new Intent();
				intent.setClass(UserCenterActivity.this, ShareActivity.class);
				UserCenterActivity.this.startActivity(intent);
			}
		});
		selfinfo=(LinearLayout)findViewById(R.id.llPersonalCenterInfo);
		selfinfo.setOnClickListener(new OnClickListener(){
			public void onClick(View v)
			{
				Intent intent=new Intent();
				intent.setClass(UserCenterActivity.this, UserAccountActivity.class);
				UserCenterActivity.this.startActivity(intent);
			}
		});
		config=(LinearLayout)findViewById(R.id.llPersonalCenterSetting);
		config.setOnClickListener(new OnClickListener(){
			public void onClick(View v)
			{
				Intent intent=new Intent();
				intent.setClass(UserCenterActivity.this, SoftwareConfigActivity.class);
				UserCenterActivity.this.startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_center, menu);
		return true;
	}

}
