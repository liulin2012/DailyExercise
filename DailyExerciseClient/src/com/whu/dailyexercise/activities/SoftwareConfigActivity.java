package com.whu.dailyexercise.activities;

import com.whu.dailyexercise.R;
import com.whu.dailyexercise.R.layout;
import com.whu.dailyexercise.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

public class SoftwareConfigActivity extends Activity {
	
	private LinearLayout btn_aboutus;
	private LinearLayout btn_help;
	private LinearLayout btn_exit;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_software_config);
		
		btn_exit = (LinearLayout)findViewById(R.id.esc);
		btn_exit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Intent.ACTION_MAIN);
				intent.addCategory(Intent.CATEGORY_HOME);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
			}
		});
		
		btn_aboutus=(LinearLayout)findViewById(R.id.aboutus);
		btn_aboutus.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(SoftwareConfigActivity.this,AboutUsActivity.class);
				SoftwareConfigActivity.this.startActivity(intent);
			}
		});
		
		
		btn_help = (LinearLayout)findViewById(R.id.help);
		btn_help.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(SoftwareConfigActivity.this,HelpUsActivity.class);
				SoftwareConfigActivity.this.startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.software_config, menu);
		return true;
	}

}
