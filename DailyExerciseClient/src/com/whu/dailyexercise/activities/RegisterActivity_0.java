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
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class RegisterActivity_0 extends Activity {
	
	private Button btn_nextstep;
	private EditText edit_height;
	private EditText edit_weight;
	private EditText edit_age;
	private String sSex;
	private RadioGroup group;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_activity_0);
		btn_nextstep = (Button)findViewById(R.id.btnNextStep);
		btn_nextstep.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.putExtra("Height", edit_height.getText().toString());
				intent.putExtra("Weight", edit_weight.getText().toString());
				intent.putExtra("Age", edit_age.getText().toString());
				intent.putExtra("Sex", sSex);
				intent.setClass(RegisterActivity_0.this, RegisterActivity_1.class);
				startActivity(intent);
			}
		});
		
		edit_height = (EditText)findViewById(R.id.editHeight);
		edit_weight = (EditText)findViewById(R.id.editWeight);
		edit_age = (EditText)findViewById(R.id.editAge);
		group = (RadioGroup)this.findViewById(R.id.radioGroup_sex);
		 //绑定一个匿名监听器
		group.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		
				@Override
				public void onCheckedChanged(RadioGroup arg0, int arg1) {
				// TODO Auto-generated method stub
				//获取变更后的选中项的ID
				int radioButtonId = arg0.getCheckedRadioButtonId();
				//根据ID获取RadioButton的实例
				RadioButton rb = (RadioButton)findViewById(radioButtonId);
				//更新文本内容，以符合选中项
				sSex = rb.getText().toString();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register_activity_0, menu);
		return true;
	}

}
