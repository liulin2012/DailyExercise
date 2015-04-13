package com.whu.dailyexercise.exerciseshare;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuth;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.exception.WeiboException;
import com.whu.dailyexercise.R;
import com.whu.dailyexercise.activities.LoginActivity;

/**
 * 分享的初始化操作
 * @author Joshua
 * @since 2014-04-8
 */

public class Share{

	//变量定义
    private WeiboAuth mWeiboAuth;				//OAuth变量
    private Context login;				//loginActivity.this
    private Oauth2AccessToken mAccessToken;		//token变量
    
	public void getLoginActivity(Context a){
		login = a; 
	}
    
    public void sinaLoginInit(){
        //mSsoHandler = new SsoHandler(login, mWeiboAuth);
        //mSsoHandler.authorize(new AuthListener());
    	mWeiboAuth= new WeiboAuth(login,Constants.APP_KEY,Constants.REDIRECT_URL,Constants.SCOPE);
		mWeiboAuth.anthorize(new AuthListener());
	}
	
	public void txLoginInit(){

		
	}
	
    class AuthListener implements WeiboAuthListener {
        
        @Override
        public void onComplete(Bundle values) {

            mAccessToken = Oauth2AccessToken.parseAccessToken(values);
            if (mAccessToken.isSessionValid()) {
                AccessTokenKeeper.writeAccessToken(login, mAccessToken);
                Toast.makeText(login,R.string.weibo_success, Toast.LENGTH_SHORT).show();
            } else {
                
                String code = values.getString("code","");
                String message = login.getString(R.string.weibo_failed);
                if (!TextUtils.isEmpty(code)) {
                    message = message + "\nObtained the code: " + code;
                }
                Toast.makeText(login, message, Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onCancel() {
            Toast.makeText(login,R.string.weibo_canceled, Toast.LENGTH_LONG).show();
        }

        @Override
        public void onWeiboException(WeiboException e) {
            Toast.makeText(login,"Auth exception : " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
	
}