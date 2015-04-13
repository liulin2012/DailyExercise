package com.whu.dailyexercise.activities;

import com.ant.liao.GifView;
import com.ant.liao.GifView.GifImageType;
import com.whu.dailyexercise.R;


import android.os.Bundle;
import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class VisualImageActivity extends Activity {

	private GifView gif;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_visual_image);
		gif = (GifView)findViewById(R.id.gif);
		gif.setGifImage(R.drawable.girl1);
		
		// 设置显示的大小，拉伸或者压缩
		gif.setShowDimension(1100, 1600);
		// 设置加载方式：先加载后显示、边加载边显示、只显示第一帧再显示
		gif.setGifImageType(GifImageType.COVER);
		
		//设置形象点击监听事件
				gif.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
					//	Toast toast = Toast.makeText(VisualImageActivity.this, "今天我又瘦了0.2kg哦，你真棒！！", Toast.LENGTH_LONG);
					//	toast.setGravity(Gravity.CENTER, 0, 0);
					//	toast.show();
						Toast toast = new Toast(VisualImageActivity.this);
						//加载自定义布局
						View view = LayoutInflater.from(VisualImageActivity.this).inflate(R.layout.toast, null);
//						ImageView imageview = (ImageView)view.findViewById(R.id.toast_image);
//						imageview.setImageResource(R.drawable.bg_toast);
						TextView textview = (TextView)view.findViewById(R.id.toast_text);
						textview.setText("今天我又瘦了\n0.2kg哦，真棒！！");
						toast.setDuration(Toast.LENGTH_LONG);
						toast.setGravity(Gravity.TOP|Gravity.RIGHT, 0, 300);
						//加载自定义的布局
						toast.setView(view);
						toast.show();
						
					}
				});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.visual_image, menu);
		return true;
	}

}
