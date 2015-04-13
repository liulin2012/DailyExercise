package com.whu.dailyexercise.activities;

import com.whu.dailyexercise.R;
import com.whu.dailyexercise.R.layout;
import com.whu.dailyexercise.R.menu;
import com.whu.dailyexercise.accountmanager.Download;
import com.whu.dailyexercise.accountmanager.Upload;
import com.whu.dailyexercise.util.CommonField;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.ProgressDialog;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class ShareActivity extends Activity {
	
	private RelativeLayout uploadbtn;
	private RelativeLayout downloadbtn;
	private ProgressDialog mDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_share);
		uploadbtn=(RelativeLayout)findViewById(R.id.cloudupload);
		downloadbtn=(RelativeLayout)findViewById(R.id.clouddownload);
		uploadbtn.setOnClickListener(new OnClickListener(){
			public void onClick(View v)
			{
				mDialog = new ProgressDialog(ShareActivity.this);
				mDialog.setTitle("同步中...");
				mDialog.setMessage("正在同步到服务器，请稍后...");
				mDialog.show();
				Thread uploadThread=new Thread(new UploadThread());
				uploadThread.start();
			}
		});
		
		downloadbtn.setOnClickListener(new OnClickListener(){
			public void onClick(View v)
			{
				mDialog = new ProgressDialog(ShareActivity.this);
				mDialog.setTitle("同步中...");
				mDialog.setMessage("正在同步到客户端，请稍后...");
				mDialog.show();
				Thread downloadThread=new Thread(new DownloadThread());
				downloadThread.start();
			}
		});
	}

	
	
	Handler handler=new Handler()
	{
		 public void handleMessage(Message msg) 
		 {
			 switch(msg.what) 
			 {
			 case 0:
				 mDialog.cancel();
				 Toast.makeText(getApplicationContext(), "更新成功", Toast.LENGTH_SHORT).show();
			        break;
			 case 1:
				 mDialog.cancel();
				 Toast.makeText(getApplicationContext(), "更新失败", Toast.LENGTH_SHORT).show();
			 		break;
			 }
		 }
	};
	
	class UploadThread implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			
			boolean isValidate=Upload.UploadPlan(CommonField.userlist.get(0).getUserid(), CommonField.sqlitedatabasa);
			Message msg = handler.obtainMessage();
	    	if(isValidate)
	    	{
	    		msg.what = 0;
	    		handler.sendMessage(msg);
	    	}
	    	else
	    	{
	    		msg.what = 1;
	    		handler.sendMessage(msg);
	    	}
		
		}
		
	}
	
	class DownloadThread implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			boolean isValidate=Download.DownloadPlan(CommonField.userlist.get(0).getUserid(),CommonField.sqlitedatabasa);
			Message msg = handler.obtainMessage();
			if(isValidate)
			{
				msg.what = 0;
	    		handler.sendMessage(msg);
			}
	    	else
	    	{
	    		msg.what = 1;
	    		handler.sendMessage(msg);
	    	}
		
		}
		
	}
}
