package com.whu.dailyexercise.activities;

import com.tencent.weibo.sdk.android.component.PublishActivity;
import com.whu.dailyexercise.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;

public class ShareChooseActivity extends Activity {
	
	private RelativeLayout shareToSina;
	private RelativeLayout shareToTX;
	private Button btn_back;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_share_choose);
		shareToSina = (RelativeLayout)findViewById(R.id.shareToSina);
		shareToSina.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				Intent intent = new Intent();
				intent.setClass(ShareChooseActivity.this,SinaShareActivity.class);
				ShareChooseActivity.this.startActivity(intent);
			}
			
		});
		
		//ÌÚÑ¶·ÖÏí°´Å¥
		shareToTX = (RelativeLayout)findViewById(R.id.shareToTX);
		shareToTX.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				Intent i = new Intent(ShareChooseActivity.this,PublishNewActivity.class);
				startActivity(i);
			}
			
		});
		
		btn_back=(Button)findViewById(R.id.shareChoose_back);
		btn_back.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ShareChooseActivity.this.finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.share_choose, menu);
		return true;
	}

}
