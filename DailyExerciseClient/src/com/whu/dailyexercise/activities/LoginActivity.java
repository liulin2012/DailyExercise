package com.whu.dailyexercise.activities;


import org.json.JSONException;
import org.json.JSONObject;

import com.tencent.weibo.sdk.android.api.util.Util;
import com.tencent.weibo.sdk.android.component.Authorize;
import com.tencent.weibo.sdk.android.component.sso.AuthHelper;
import com.tencent.weibo.sdk.android.component.sso.OnAuthListener;
import com.tencent.weibo.sdk.android.component.sso.WeiboToken;
import com.whu.dailyexercise.R;
import com.whu.dailyexercise.util.CommonField;
import com.whu.dailyexercise.accountmanager.Login;
import com.whu.dailyexercise.accountmanager.UserInfo;
import com.whu.dailyexercise.exerciseshare.Share;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

/**
 * 登陆模块
 * @author darktemple9
 *
 */

@SuppressLint({ "HandlerLeak", "ShowToast" })
public class LoginActivity extends Activity {
	
	private Button loginbtn; 
	private Button registerbtn;
	private EditText inputuserid;
	private EditText inputpassword;
	private ProgressDialog mDialog;
	private static String responseMsg="";
	private String userid;
	private String password;
	private Button btn_register;
	private boolean isLogin=false;
	private Button btn_back;
	private LinearLayout sinaLogin;
	private LinearLayout txLogin;
	@SuppressLint("CutPasteId")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		this.getApplicationContext();
		btn_register=(Button)findViewById(R.id.btnRegister);
		btn_register.setOnClickListener(new BtnRegisterClickListener());
		
		loginbtn=(Button)findViewById(R.id.btnLogin);
		registerbtn=(Button)findViewById(R.id.btnRegister);
		inputuserid=(EditText)findViewById(R.id.editAcount);
		inputpassword=(EditText)findViewById(R.id.editPassword);
		btn_back=(Button)findViewById(R.id.btn_login_back);
		sinaLogin = (LinearLayout)findViewById(R.id.btnLoginFromSina);					//绑定新浪按钮
		txLogin = (LinearLayout)findViewById(R.id.btnLoginFromTx);						//绑定腾讯按钮
		
		sinaLogin.setOnClickListener(new OnClickListener(){						//点击新浪授权
			public void onClick(View V)
			{	
				Log.d("aaa","aaa");
				Share share=new Share();
				share.getLoginActivity(LoginActivity.this) ;
				share.sinaLoginInit();
			}
			
		});
		
		txLogin.setOnClickListener(new OnClickListener(){						//点击腾讯授权
			public void onClick(View V)
			{
		        long appid = Long.valueOf(Util.getConfig().getProperty("APP_KEY"));
		        String app_secket = Util.getConfig().getProperty("APP_KEY_SEC");
		        auth(appid, app_secket);
			}
			
		});
		
		loginbtn.setOnClickListener(new OnClickListener(){
			public void onClick(View v)
			{
				mDialog = new ProgressDialog(LoginActivity.this);
				mDialog.setTitle("登陆");
				mDialog.setMessage("正在登陆服务器，请稍后...");
				mDialog.show();
				Thread loginThread = new Thread(new LoginThread());
				loginThread.start();
				
			}
		});
		
		registerbtn.setOnClickListener(new OnClickListener(){
			public void onClick(View v)
			{
				Intent intent=new Intent();
				intent.setClass(LoginActivity.this, RegisterActivity_0.class);
				startActivity(intent);
				LoginActivity.this.finish();
			}
		});
		
		btn_back.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				LoginActivity.this.finish();
			}
		});
	}
	
	Handler handler = new Handler()  
    {  
        public void handleMessage(Message msg)  
        {  
            switch(msg.what)  
            {  
            case 0:  
                mDialog.cancel();  
  
                Toast.makeText(getApplicationContext(), userid+"登录成功！", Toast.LENGTH_SHORT).show();  
                isLogin=true;
                Intent intent=new Intent();
                //利用全局变量来实现传参数
                CommonField.loginOK=isLogin;
                CommonField.userId=userid;
                CommonField.userlist=UserInfo.getUserInfo(userid, CommonField.sqlitedatabasa);
                CommonField.homepage1_please_login.setText("已登录");
                CommonField.homepage1_username.setText(CommonField.userId);
                intent.setClass(LoginActivity.this,HomepageActivity_1.class);
				
				startActivity(intent);
				LoginActivity.this.finish();
                break;  
            case 1:  
                mDialog.cancel();  
                Toast.makeText(getApplicationContext(), "用户名或密码错误", Toast.LENGTH_SHORT).show();  
                break;  
            case 2:  
                mDialog.cancel();  
                Toast.makeText(getApplicationContext(), "URL验证失败,请检查网络连接", Toast.LENGTH_SHORT).show();  
                break;  
              
            }  
              
        }  
    };  
	
    class BtnRegisterClickListener implements android.view.View.OnClickListener
	{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent=new Intent();
			intent.setClass(LoginActivity.this, RegisterActivity_1.class);
			LoginActivity.this.startActivity(intent);
		}
	}
	
    class LoginThread implements Runnable
    {
		@Override
		public void run() {
			userid=inputuserid.getText().toString();
			password=inputpassword.getText().toString();
			Message msg = handler.obtainMessage();
			
			
			//URL合法，但是这一步并不验证密码是否正确
			Login a=new Login();
	    	boolean loginValidate=a.isLogin(userid, password);
	    	responseMsg=a.result;
	    	System.out.println("----------------------------bool is :"+loginValidate+"----------response:"+responseMsg);
	    
	    	if(loginValidate)
	    	{
	    		if(!responseMsg.equals("wrong password"))
	    		{
	    			try {
						JSONObject json=new JSONObject(responseMsg);
						String myuser=json.getString("LoginInfo");  
						JSONObject userjson=new JSONObject(myuser);
						String ssuserid=userjson.getString("userid");
						String password=userjson.getString("password");
						String username=userjson.getString("username");
						String sex=userjson.getString("sex");
						int age=Integer.parseInt(userjson.getString("age"));
						float height=Float.parseFloat(userjson.getString("height"));
						float weight=Float.parseFloat(userjson.getString("weight"));
						UserInfo.updateUserInfo(ssuserid, username, sex, password, height, weight, age, CommonField.sqlitedatabasa);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	    			msg.what = 0;
	    			handler.sendMessage(msg);
	    		}else
	    		{
	    			msg.what = 1;
		    		handler.sendMessage(msg);
	    		}
	    		
	    	}else
	    	{
	    		msg.what = 2;
	    		handler.sendMessage(msg);
	    	}
		}
    	
		
    	
    }
	
	@SuppressLint("ShowToast")
	private void auth(long appid, String app_secket) {
		final Context context = this.getApplicationContext();
		//注册当前应用的appid和appkeysec，并指定一个OnAuthListener
		//OnAuthListener在授权过程中实施监听
		AuthHelper.register(this, appid, app_secket, new OnAuthListener() {

			//如果当前设备没有安装腾讯微博客户端，走这里
			@Override
			public void onWeiBoNotInstalled() {
				Toast.makeText(LoginActivity.this, "onWeiBoNotInstalled", 1000)
						.show();
				AuthHelper.unregister(LoginActivity.this);
				Intent i = new Intent(LoginActivity.this,Authorize.class);
				startActivity(i);
			}

			//如果当前设备没安装指定版本的微博客户端，走这里
			@Override
			public void onWeiboVersionMisMatch() {
				Toast.makeText(LoginActivity.this, "onWeiboVersionMisMatch",
						1000).show();
				AuthHelper.unregister(LoginActivity.this);
				Intent i = new Intent(LoginActivity.this,Authorize.class);
				startActivity(i);
			}

			//如果授权失败，走这里
			@Override
			public void onAuthFail(int result, String err) {
				Toast.makeText(LoginActivity.this, "result : " + result, 1000)
						.show();
				AuthHelper.unregister(LoginActivity.this);
			}

			//授权成功，走这里
			//授权成功后，所有的授权信息是存放在WeiboToken对象里面的，可以根据具体的使用场景，将授权信息存放到自己期望的位置，
			//在这里，存放到了applicationcontext中
			@Override
			public void onAuthPassed(String name, WeiboToken token) {
				Toast.makeText(LoginActivity.this, "passed", 1000).show();
				//
				Util.saveSharePersistent(context, "ACCESS_TOKEN", token.accessToken);
				Util.saveSharePersistent(context, "EXPIRES_IN", String.valueOf(token.expiresIn));
				Util.saveSharePersistent(context, "OPEN_ID", token.openID);
//				Util.saveSharePersistent(context, "OPEN_KEY", token.omasKey);
				Util.saveSharePersistent(context, "REFRESH_TOKEN", "");
//				Util.saveSharePersistent(context, "NAME", name);
//				Util.saveSharePersistent(context, "NICK", name);
				Util.saveSharePersistent(context, "CLIENT_ID", Util.getConfig().getProperty("APP_KEY"));
				Util.saveSharePersistent(context, "AUTHORIZETIME",
						String.valueOf(System.currentTimeMillis() / 1000l));
				AuthHelper.unregister(LoginActivity.this);
			}
		});

		AuthHelper.auth(this, "");
	}

}
