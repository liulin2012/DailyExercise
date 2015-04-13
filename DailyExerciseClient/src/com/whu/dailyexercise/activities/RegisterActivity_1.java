package com.whu.dailyexercise.activities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.whu.dailyexercise.R;
import com.whu.dailyexercise.accountmanager.Register;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity_1 extends Activity {
	
	private Button registerbtn; 
	private EditText inputuserid;
	private EditText inputpassword;
	private EditText inputusername;
	private EditText inputconfirmpsd;
	private String userid=null;
	private String password=null;
	private String username=null;
	private float height =0;
	private float weight =0;
	private int age = 18;
	private String sex ="男";
	private String confirmpsd;
	private Button btn_back;
	private ProgressDialog mDialog;
	private static String responseMsg="";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		btn_back=(Button)findViewById(R.id.btnPrev);
		btn_back.setOnClickListener(new BtnBackClickListener());
		
		inputuserid=(EditText)findViewById(R.id.editAcount);
		inputpassword=(EditText)findViewById(R.id.editPassword);
		inputusername=(EditText)findViewById(R.id.editName);
		inputconfirmpsd=(EditText)findViewById(R.id.editPassword2);

		height = Float.valueOf(getIntent().getStringExtra("Height")).floatValue();
		weight = Float.valueOf(getIntent().getStringExtra("Weight")).floatValue();
		age =  Integer.valueOf(getIntent().getStringExtra("Age")).intValue();
		sex = getIntent().getStringExtra("Sex");
		
		registerbtn=(Button)findViewById(R.id.btnRegister);
		registerbtn.setOnClickListener(new OnClickListener(){
			public void onClick(View v)
			{
				mDialog = new ProgressDialog(RegisterActivity_1.this);
				mDialog.setTitle("注册");
				mDialog.setMessage("正在注册到服务器，请稍后...");
				mDialog.show();
				Thread registerThread=new Thread(new RegisterThread());
				registerThread.start();
			}
		});
		
	}
	
	@SuppressLint("HandlerLeak")
	Handler handler = new Handler()  
    {  
        public void handleMessage(Message msg)  
        {  
            switch(msg.what)  
            {  
            case 0:  
                mDialog.cancel();  
                Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_SHORT).show(); 
                Intent intent=new Intent();
                intent.setClass(RegisterActivity_1.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                RegisterActivity_1.this.startActivity(intent);
                break;  
            case 1:  
                mDialog.cancel();  
                Toast.makeText(getApplicationContext(), "用户名已经被注册，注册失败", Toast.LENGTH_SHORT).show();  
                break;  
            case 2:  
                mDialog.cancel();  
                Toast.makeText(getApplicationContext(), "URL验证失败,请检查网络连接", Toast.LENGTH_SHORT).show();  
                break;  
            case 3:
            	mDialog.cancel();
            	Toast.makeText(getApplicationContext(), "两次密码不一致，请重新输出", Toast.LENGTH_SHORT).show();  
            	break;
            case 4:
            	mDialog.cancel();
            	Toast.makeText(getApplicationContext(), "用户名和密码不能为空", Toast.LENGTH_SHORT).show();  
            case 5:
            	mDialog.cancel();
            	Toast.makeText(getApplicationContext(), "邮箱格式错误", Toast.LENGTH_SHORT).show();  
            	break;
            
            }  
              
        }  
    };  
	
	
	class RegisterThread implements Runnable
	{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			boolean isValidate=false;
			userid=inputuserid.getText().toString().trim();
			password=inputpassword.getText().toString().trim();
			username=inputusername.getText().toString().trim();
			confirmpsd=inputconfirmpsd.getText().toString().trim();
			Message msg=handler.obtainMessage();
			String strPattern = "^[a-zA-Z][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";
			Pattern p = Pattern.compile(strPattern);
			Matcher m = p.matcher(userid);
			if(!m.matches())
			{
				msg.what=5;
				handler.sendMessage(msg);
			}
			else{
			if(userid.equals("")||password.equals(""))
			{
				msg.what=4;
				handler.sendMessage(msg);
			}
			else {
				if (password.equals(confirmpsd)) {
					Register a = new Register();
					isValidate = a.isRegister(userid,password,username,height,weight,sex,age);
					responseMsg = a.result;
					if (isValidate) {
						if (responseMsg.equals("success")) {
							msg.what = 0;
							handler.sendMessage(msg);
						} else {
							msg.what = 1;
							handler.sendMessage(msg);
						}
					} else {
						msg.what = 2;
						handler.sendMessage(msg);
					}
				} else {
					msg.what = 3;
					System.out.println("aa");
					handler.sendMessage(msg);
				}
			}
			}
		}
		
	}
	
	class BtnBackClickListener implements android.view.View.OnClickListener
	{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			RegisterActivity_1.this.finish();
		}
	}



}
