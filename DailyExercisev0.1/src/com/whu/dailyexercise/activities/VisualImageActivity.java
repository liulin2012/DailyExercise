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
		
		// ������ʾ�Ĵ�С���������ѹ��
		gif.setShowDimension(1100, 1600);
		// ���ü��ط�ʽ���ȼ��غ���ʾ���߼��ر���ʾ��ֻ��ʾ��һ֡����ʾ
		gif.setGifImageType(GifImageType.COVER);
		
		//���������������¼�
				gif.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
					//	Toast toast = Toast.makeText(VisualImageActivity.this, "������������0.2kgŶ�����������", Toast.LENGTH_LONG);
					//	toast.setGravity(Gravity.CENTER, 0, 0);
					//	toast.show();
						Toast toast = new Toast(VisualImageActivity.this);
						//�����Զ��岼��
						View view = LayoutInflater.from(VisualImageActivity.this).inflate(R.layout.toast, null);
//						ImageView imageview = (ImageView)view.findViewById(R.id.toast_image);
//						imageview.setImageResource(R.drawable.bg_toast);
						TextView textview = (TextView)view.findViewById(R.id.toast_text);
						textview.setText("������������\n0.2kgŶ���������");
						toast.setDuration(Toast.LENGTH_LONG);
						toast.setGravity(Gravity.TOP|Gravity.RIGHT, 0, 300);
						//�����Զ���Ĳ���
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
