package com.whu.dailyexercise.activities;

import java.util.ArrayList;
import java.util.List;

import com.tencent.weibo.sdk.android.api.util.Util;
import com.tencent.weibo.sdk.android.component.Authorize;
import com.tencent.weibo.sdk.android.component.sso.AuthHelper;
import com.tencent.weibo.sdk.android.component.sso.OnAuthListener;
import com.tencent.weibo.sdk.android.component.sso.WeiboToken;
import com.whu.dailyexercise.R;
import com.whu.dailyexercise.accountmanager.Upload;
import com.whu.dailyexercise.accountmanager.UserEntity;
import com.whu.dailyexercise.accountmanager.UserInfo;
import com.whu.dailyexercise.activities.ShareActivity.UploadThread;
import com.whu.dailyexercise.exerciseplan.EditPlan;
import com.whu.dailyexercise.exerciseshare.Share;
import com.whu.dailyexercise.util.CommonField;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class UserAccountActivity extends Activity {
	
	private TextView tx_UserId;
	private TextView tx_Username;
	private TextView tx_Userheight;
	private TextView tx_Userweight;
	private TextView tx_Userage;
	private TextView tx_Usersex;
	private Button btn_confirm;
	private Button btn_cancel;
	private ProgressDialog mDialog;
	private RelativeLayout btn_logout;  //ע��
	private LinearLayout btn_modify_name;
	private LinearLayout btn_modify_pwd;
	private LinearLayout btn_modify_height;
	private LinearLayout btn_modify_weight;
	private LinearLayout btn_modify_age;
	private LinearLayout btn_modify_sex;
	private LinearLayout btn_tx_weibo;
	private LinearLayout btn_sina_weibo;
	private List<UserEntity> userlist = new ArrayList<UserEntity>();
	private UserEntity user = new UserEntity();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_account);
		user = CommonField.userlist.get(0);
		
		tx_UserId = (TextView)findViewById(R.id.tvUserInfoAccount);
		tx_UserId.setText(CommonField.userlist.get(0).getUserid());
		
		tx_Username = (TextView)findViewById(R.id.etUserInfoName);
		tx_Username.setText(CommonField.userlist.get(0).getUsername());
		
		tx_Userheight = (TextView)findViewById(R.id.etUserInfoHeight);
		float temp_0 =CommonField.userlist.get(0).getHeight();
		String stemp_0 = ""+temp_0;
		tx_Userheight.setText(stemp_0);
		
		tx_Userweight = (TextView)findViewById(R.id.etUserInfoWeight);
		float temp_1 =CommonField.userlist.get(0).getWeight();
		String stemp_1 = ""+temp_1;
		tx_Userweight.setText(stemp_1);
		
		tx_Userage = (TextView)findViewById(R.id.etUserInfoAge);
		int temp_2 =CommonField.userlist.get(0).getAge();
		String stemp_2 = ""+temp_2;
		tx_Userage.setText(stemp_2);
		
		tx_Usersex = (TextView)findViewById(R.id.etUserInfoSex);
		tx_Usersex.setText(CommonField.userlist.get(0).getSex());
		
		btn_confirm = (Button)findViewById(R.id.btnConfim);
		btn_confirm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				mDialog = new ProgressDialog(UserAccountActivity.this);
				mDialog.setTitle("ͬ����...");
				mDialog.setMessage("����ͬ���������������Ժ�...");
				mDialog.show();
				Thread uploadThread=new Thread(new UploadThread());
				uploadThread.start();
			}
		});
		
		btn_cancel = (Button)findViewById(R.id.btnCancel);
		btn_cancel.setOnClickListener(new OnClickListener() {
					
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				CommonField.userlist = UserInfo.getUserInfo(CommonField.userId, CommonField.sqlitedatabasa);
				finish();
			}
		});
		
		btn_logout = (RelativeLayout)findViewById(R.id.rlUserInfoSettingLogout);
		btn_modify_name =(LinearLayout)findViewById(R.id.llUserInfoName);
		btn_modify_pwd = (LinearLayout)findViewById(R.id.llUserInfoPassWord);
		btn_modify_height = (LinearLayout)findViewById(R.id.llUserInfoHeight);
		btn_modify_weight = (LinearLayout)findViewById(R.id.llUserInfoWeight);
		btn_modify_age = (LinearLayout)findViewById(R.id.llUserInfoAge);
		btn_modify_sex = (LinearLayout)findViewById(R.id.llUserInfoSex);
		btn_tx_weibo  = (LinearLayout)findViewById(R.id.llUserInfoTx);
		btn_sina_weibo = (LinearLayout)findViewById(R.id.llUserInfoSina);
		
		btn_logout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CommonField.loginOK = false;
				CommonField.homepage1_please_login.setText("���¼");
		        CommonField.homepage1_username.setText("������");
		        Intent intent = new Intent(UserAccountActivity.this, HomepageActivity_1.class);
		        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		        UserAccountActivity.this.startActivity(intent);
		        Toast.makeText(UserAccountActivity.this, "ע��", Toast.LENGTH_SHORT).show();
			}
		});
		btn_modify_name.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
					
						EditNameDlg dialog = new EditNameDlg(UserAccountActivity.this,new EditNameDlg.OnCustomDialogListener() {
		                    
		                    @Override
		                    public void back(String name) {
	
		                    	user.setUsername(name);
		                    	tx_Username.setText(name);
		                    }
				            },"�޸��ǳ�");
						dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			            dialog.show();
						
					}
				});
		
		
		btn_modify_pwd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		btn_modify_height.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				EditNameDlg dialog = new EditNameDlg(UserAccountActivity.this,new EditNameDlg.OnCustomDialogListener() {
                    
                    @Override
                    public void back(String name) {

                    	user.setHeight(Float.valueOf(name).floatValue());
                    	tx_Userheight.setText(name);
                    }
		            },"�޸����");
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	            dialog.show();
				
			}
		});
		btn_modify_weight.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				EditNameDlg dialog = new EditNameDlg(UserAccountActivity.this,new EditNameDlg.OnCustomDialogListener() {
                    
                    @Override
                    public void back(String name) {

                    	user.setWeight(Float.valueOf(name).floatValue());
                    	tx_Userweight.setText(name);
                    }
		            },"�޸�����");
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	            dialog.show();
			}
		});
		btn_modify_age.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				EditNameDlg dialog = new EditNameDlg(UserAccountActivity.this,new EditNameDlg.OnCustomDialogListener() {
                    
                    @Override
                    public void back(String name) {

                    	user.setAge(Integer.valueOf(name).intValue());
                    	tx_Userage.setText(name);
                    }
		            },"�޸�����");
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	            dialog.show();
			}
		});
		btn_modify_sex.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				EditNameDlg dialog = new EditNameDlg(UserAccountActivity.this,new EditNameDlg.OnCustomDialogListener() {
                    
                    @Override
                    public void back(String name) {

                    	user.setSex(name);
                    	tx_Usersex.setText(name);
                    }
		            },"�޸��Ա�");
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	            dialog.show();
			}
		});
		btn_tx_weibo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				long appid = Long.valueOf(Util.getConfig().getProperty("APP_KEY"));
		        String app_secket = Util.getConfig().getProperty("APP_KEY_SEC");
		        auth(appid, app_secket);
			}
		});
		btn_sina_weibo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Share share=new Share();
				share.getLoginActivity(UserAccountActivity.this) ;
				share.sinaLoginInit();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_account, menu);
		return true;
	}
	
	Handler handler=new Handler()
	{
		 public void handleMessage(Message msg) 
		 {
			 switch(msg.what) 
			 {
			 case 0:
				 mDialog.cancel();
				 Toast.makeText(getApplicationContext(), "���³ɹ�", Toast.LENGTH_SHORT).show();
			        break;
			 case 1:
				 mDialog.cancel();
				 Toast.makeText(getApplicationContext(), "����ʧ��", Toast.LENGTH_SHORT).show();
			 		break;
			 }
		 }
	};
	
	class UploadThread implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			userlist.add(user);
			
			boolean isValidate=Upload.UploadUserInfo(userlist);;
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
	private void auth(long appid, String app_secket) {
		final Context context = this.getApplicationContext();
		//ע�ᵱǰӦ�õ�appid��appkeysec����ָ��һ��OnAuthListener
		//OnAuthListener����Ȩ������ʵʩ����
		AuthHelper.register(this, appid, app_secket, new OnAuthListener() {

			//�����ǰ�豸û�а�װ��Ѷ΢���ͻ��ˣ�������
			@Override
			public void onWeiBoNotInstalled() {
				Toast.makeText(UserAccountActivity.this, "onWeiBoNotInstalled", 1000)
						.show();
				AuthHelper.unregister(UserAccountActivity.this);
				Intent i = new Intent(UserAccountActivity.this,Authorize.class);
				startActivity(i);
			}

			//�����ǰ�豸û��װָ���汾��΢���ͻ��ˣ�������
			@Override
			public void onWeiboVersionMisMatch() {
				Toast.makeText(UserAccountActivity.this, "onWeiboVersionMisMatch",
						1000).show();
				AuthHelper.unregister(UserAccountActivity.this);
				Intent i = new Intent(UserAccountActivity.this,Authorize.class);
				startActivity(i);
			}

			//�����Ȩʧ�ܣ�������
			@Override
			public void onAuthFail(int result, String err) {
				Toast.makeText(UserAccountActivity.this, "result : " + result, 1000)
						.show();
				AuthHelper.unregister(UserAccountActivity.this);
			}

			//��Ȩ�ɹ���������
			//��Ȩ�ɹ������е���Ȩ��Ϣ�Ǵ����WeiboToken��������ģ����Ը��ݾ����ʹ�ó���������Ȩ��Ϣ��ŵ��Լ�������λ�ã�
			//�������ŵ���applicationcontext��
			@Override
			public void onAuthPassed(String name, WeiboToken token) {
				Toast.makeText(UserAccountActivity.this, "passed", 1000).show();
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
				AuthHelper.unregister(UserAccountActivity.this);
			}
		});

		AuthHelper.auth(this, "");
	}


}
