package com.whu.dailyexercise.activities;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import com.tencent.weibo.sdk.android.api.WeiboAPI;
import com.tencent.weibo.sdk.android.api.util.BackGroudSeletor;
import com.tencent.weibo.sdk.android.api.util.Util;
import com.tencent.weibo.sdk.android.model.AccountModel;
import com.tencent.weibo.sdk.android.model.BaseVO;
import com.tencent.weibo.sdk.android.model.ModelResult;
import com.tencent.weibo.sdk.android.network.HttpCallback;
import com.tencent.weibo.sdk.android.component.FriendActivity;
import com.tencent.weibo.sdk.android.component.ConversationActivity;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;

import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.text.Editable;

import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import android.view.Window;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 微博发表组件
 * 
 * 
 * */
public class PublishNewActivity extends Activity implements OnClickListener,
		HttpCallback {
	private Button button_esc; // 取消按钮5001
	private Button button_send; // 发�?按钮5002

	private EditText editText_text;// 编辑文本内容5003

	private ImageView imageView_icon;// 图片小图�?004
	private ImageView imageView_bound;// 小图标边�?005
	private ImageView imageView_big; // 图片大图
	private ImageView imageView_delete;// 大图删除按钮5013

	private ImageButton button_friend; // 查看收听的人列表功能按钮5006
	private ImageButton button_conversation;// 查看话题功能按钮5007
	private ImageButton button_camera;// 启动照相机按�?008
	private ImageButton button_location;// 获取地理位置按钮5009

	private TextView textView_num;// 显示文本剩余字数

	private LinearLayout layout_imagebound;
	private LinearLayout layout_set;
	private LinearLayout viewroot;
	private LinearLayout layout_big_delete;

	private FrameLayout frameLayout_icon;
	private FrameLayout frameLayout_big;

	private PopupWindow popupWindow;
	// private byte[] pics;
	private int lyout[] = new int[2];
	private String edstring = "���׵��˲��ں����ں����˲����ס���֪���Լ�������ô";
	private Map<String, String> location;
	private Location mLocation;
	private ProgressDialog dialog;
	private String accessToken;// 用户访问令牌
	private Bitmap mBitmap = null;
	private WeiboAPI weiboAPI;// 微博相关API
	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		accessToken = Util.getSharePersistent(getApplicationContext(),
				"ACCESS_TOKEN");
		if (accessToken == null || "".equals(accessToken)) {
			Toast.makeText(PublishNewActivity.this, "������Ȩ", Toast.LENGTH_SHORT)
					.show();
			this.finish();
			return ;
		}
		context = getApplicationContext();
		AccountModel account = new AccountModel(accessToken);
		weiboAPI = new WeiboAPI(account);
		lyout[0] = (BackGroudSeletor.getdrawble("test2x", PublishNewActivity.this)).getMinimumWidth();
		lyout[1] = BackGroudSeletor.getdrawble("test2x", PublishNewActivity.this)
				.getMinimumHeight();
		LinearLayout layout = (LinearLayout) initview();
		dialog = new ProgressDialog(PublishNewActivity.this);
		dialog.setMessage("���ڷ����С�����");
		setContentView(layout);
		setonclick();
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				InputMethodManager imm = (InputMethodManager) PublishNewActivity.this
						.getSystemService(INPUT_METHOD_SERVICE);
				// imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
				imm.showSoftInput(editText_text, InputMethodManager.SHOW_FORCED);

			}

		}, 400);
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onResume() {
		super.onResume();
		final InputMethodManager imm = (InputMethodManager) PublishNewActivity.this
				.getSystemService(INPUT_METHOD_SERVICE);
		// frameLayout_icon.setVisibility(View.INVISIBLE);
		// frameLayout_big.setVisibility(View.GONE);
		if (popupWindow != null && popupWindow.isShowing()) {

			Log.d("mkl", imm.isActive() + "");
			// imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
			// imm.showSoftInput(editText_text,InputMethodManager.SHOW_FORCED);
			imm.hideSoftInputFromWindow(editText_text.getWindowToken(), 0);

		} else {
			Timer timer = new Timer();
			timer.schedule(new TimerTask() {
				@Override
				public void run() {

					imm.showSoftInput(editText_text,
							InputMethodManager.SHOW_FORCED);

				}

			}, 400);
		}
		if (location != null) {
			button_location.setBackgroundDrawable(BackGroudSeletor.getdrawble(
					"dingwei_icon_hover2x", PublishNewActivity.this));
		} else {
			button_location.setBackgroundDrawable(BackGroudSeletor.getdrawble(
					"dingwei_icon2x", PublishNewActivity.this));
		}
	}
/**
 * 初始化界面使用控件，并设置相应属性，
 * 
 * */
	@SuppressWarnings("deprecation")
	private View initview() {

		viewroot = new LinearLayout(PublishNewActivity.this);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.FILL_PARENT);
		FrameLayout.LayoutParams layoutParams_frame = new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.WRAP_CONTENT,
				FrameLayout.LayoutParams.WRAP_CONTENT);
		RelativeLayout.LayoutParams wrapParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		viewroot.setLayoutParams(params);
		viewroot.setOrientation(LinearLayout.VERTICAL);
		// viewroot.setBackgroundColor(Color.parseColor("#888888"));
		LinearLayout.LayoutParams params_layout = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);

		// LinearLayout layout_title = new LinearLayout(PublishActivity.this);
		// layout_title.setLayoutParams(params_layout);
		// layout_title.setOrientation(LinearLayout.HORIZONTAL);
		// layout_title.setGravity(Gravity.CENTER_VERTICAL);
		// layout_title.setBackgroundDrawable(BackGroudSeletor.getdrawble(
		// "up_bg2x", PublishActivity.this));
		// layout_title.setPadding(10, 10, 10, 10);
		RelativeLayout.LayoutParams fillWrapParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.FILL_PARENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		RelativeLayout layout_title = new RelativeLayout(this);
		layout_title.setLayoutParams(fillWrapParams);
		layout_title.setBackgroundDrawable(BackGroudSeletor.getdrawble(
				"up_bg2x", getApplication()));
		layout_title.setGravity(LinearLayout.HORIZONTAL);
		button_esc = new Button(PublishNewActivity.this);// 取消按钮
		wrapParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
		wrapParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
		wrapParams.topMargin=10;
		wrapParams.leftMargin=10;
		wrapParams.bottomMargin=10;
		button_esc.setLayoutParams(wrapParams);
		button_esc.setText("ȡ��");
		button_esc.setClickable(true);
		button_esc.setId(5001);
		String string_esc[] = { "quxiao_btn2x", "quxiao_btn_hover" };
		button_esc.setBackgroundDrawable(BackGroudSeletor.createBgByImageIds(
				string_esc, PublishNewActivity.this));
		
		button_send = new Button(PublishNewActivity.this);// 发�?按钮
		RelativeLayout.LayoutParams wrapParamsRight = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		wrapParamsRight.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
		wrapParamsRight.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
		wrapParamsRight.topMargin=10;
		wrapParamsRight.rightMargin=10;
		wrapParamsRight.bottomMargin=10;
		button_send.setLayoutParams(wrapParamsRight);
		LinearLayout layout_space = new LinearLayout(PublishNewActivity.this);
		LinearLayout.LayoutParams params_space = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT, 1);
		layout_space.setLayoutParams(params_space);
		button_send.setText("����");
		button_send.setClickable(true);
		button_send.setId(5002);
		String string_send[] = { "sent_btn_22x", "sent_btn_hover" };
		button_send.setBackgroundDrawable(BackGroudSeletor.createBgByImageIds(
				string_send, PublishNewActivity.this));
		layout_title.addView(button_esc);
		layout_title.addView(button_send);

		LinearLayout layout_content = new LinearLayout(PublishNewActivity.this);
		layout_content.setLayoutParams(params_layout);
		layout_content.setLayoutParams(params_layout);
		layout_content.setOrientation(LinearLayout.VERTICAL);
		layout_content.setBackgroundColor(Color.WHITE);
		layout_content.requestFocus();
		editText_text = new EditText(PublishNewActivity.this);
		editText_text.setBackgroundColor(Color.WHITE);
		editText_text.setMaxLines(4);
		editText_text.setMinLines(4);
		editText_text.setMinEms(4);
		editText_text.setMaxEms(4);
		editText_text.setFocusable(true);
		editText_text.requestFocus();
		editText_text.setText(edstring);
		editText_text.setSelection(edstring.length());
		editText_text.setScrollbarFadingEnabled(true);
		editText_text.setGravity(Gravity.TOP);
		editText_text.setMovementMethod(ScrollingMovementMethod.getInstance());
		editText_text.setId(5003);

		frameLayout_icon = new FrameLayout(PublishNewActivity.this);

		frameLayout_icon.setLayoutParams(layoutParams_frame);
		LinearLayout layout_icon = new LinearLayout(PublishNewActivity.this);
		layout_icon.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);

		layout_icon.setLayoutParams(new LinearLayout.LayoutParams(54, 45));
		layout_icon.setPadding(0, 0, 2, 0);
		imageView_icon = new ImageView(PublishNewActivity.this);
		imageView_icon.setId(5004);
		imageView_bound = new ImageView(PublishNewActivity.this);
		imageView_bound.setId(5005);
		imageView_bound.setLayoutParams(new LinearLayout.LayoutParams(54, 45));
		// imageView_icon.setImageDrawable(BackGroudSeletor.getdrawble(
		// "test2x", PublishActivity.this));
		imageView_icon.setLayoutParams(new LinearLayout.LayoutParams(33, 33));
		imageView_bound.setImageDrawable(BackGroudSeletor.getdrawble(
				"composeimageframe", PublishNewActivity.this));
		frameLayout_icon.setVisibility(View.GONE);
		// imageView_bound.setLayoutParams(layoutParams_frame);
		layout_icon.addView(imageView_icon);
		frameLayout_icon.addView(layout_icon);
		frameLayout_icon.addView(imageView_bound);
		layout_content.addView(editText_text);
		layout_content.addView(frameLayout_icon);

		layout_set = new LinearLayout(PublishNewActivity.this);
		layout_set.setLayoutParams(params_layout);
		layout_set.setBackgroundDrawable(BackGroudSeletor.getdrawble(
				"icon_bg2x", PublishNewActivity.this));
		layout_set.setOrientation(LinearLayout.HORIZONTAL);
		layout_set.setGravity(Gravity.CENTER_VERTICAL);
		layout_set.setPadding(10, 0, 30, 0);
		LinearLayout layout_function = new LinearLayout(PublishNewActivity.this);
		layout_function.setOrientation(LinearLayout.HORIZONTAL);
		layout_function.setLayoutParams(params_space);
		LinearLayout layout_friend = new LinearLayout(PublishNewActivity.this);
		layout_friend.setGravity(Gravity.CENTER_HORIZONTAL);
		layout_friend.setLayoutParams(params_space);
		LinearLayout layout_conversation = new LinearLayout(
				PublishNewActivity.this);
		layout_conversation.setGravity(Gravity.CENTER_HORIZONTAL);
		layout_conversation.setLayoutParams(params_space);
		LinearLayout layout_camera = new LinearLayout(PublishNewActivity.this);
		layout_camera.setGravity(Gravity.CENTER_HORIZONTAL);
		layout_camera.setLayoutParams(params_space);
		LinearLayout layout_location = new LinearLayout(PublishNewActivity.this);
		layout_location.setGravity(Gravity.CENTER_HORIZONTAL);
		layout_location.setLayoutParams(params_space);

		button_friend = new ImageButton(PublishNewActivity.this);
		button_friend.setLayoutParams(layoutParams_frame);
		button_friend.setId(5006);
		button_conversation = new ImageButton(PublishNewActivity.this);
		button_conversation.setLayoutParams(layoutParams_frame);
		button_conversation.setId(5007);
		button_camera = new ImageButton(PublishNewActivity.this);
		button_camera.setLayoutParams(layoutParams_frame);
		button_camera.setId(5008);
		button_location = new ImageButton(PublishNewActivity.this);
		button_location.setLayoutParams(layoutParams_frame);
		button_location.setId(5009);
		// String string_friend[] = { "haoyou_icon2x", "haoyou_icon_hover2x" };
		button_friend.setBackgroundDrawable(BackGroudSeletor.getdrawble(
				"haoyou_icon2x", PublishNewActivity.this));
		String string_conversation[] = { "huati_icon2x", "huati_icon_hover2x" };
		button_conversation.setBackgroundDrawable(BackGroudSeletor
				.createBgByImageIds(string_conversation, PublishNewActivity.this));
		String string_camera[] = { "pic_icon2x", "pic_icon_hover2x" };
		button_camera.setBackgroundDrawable(BackGroudSeletor
				.createBgByImageIds(string_camera, PublishNewActivity.this));
		String string_location[] = { "dingwei_icon2x", "dingwei_icon_hover2x" };
		button_location.setBackgroundDrawable(BackGroudSeletor
				.createBgByImageIds(string_location, PublishNewActivity.this));
		layout_friend.addView(button_friend);
		layout_function.addView(layout_friend);
		layout_conversation.addView(button_conversation);
		layout_function.addView(layout_conversation);
		layout_camera.addView(button_camera);
		layout_function.addView(layout_camera);
		layout_location.addView(button_location);
		layout_function.addView(layout_location);

		textView_num = new TextView(PublishNewActivity.this);
		textView_num.setText("140");
		textView_num.setTextColor(Color.parseColor("#999999"));
		textView_num.setGravity(Gravity.RIGHT);
		textView_num.setLayoutParams(params_space);
		textView_num.setId(5010);
		textView_num.setWidth(40);
		LinearLayout layout_textnum = new LinearLayout(PublishNewActivity.this);
		layout_textnum.setLayoutParams(params_space);

		layout_textnum.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
		layout_textnum.addView(textView_num);
		layout_set.addView(layout_function);
		layout_set.addView(layout_textnum);

		LinearLayout layout_image = new LinearLayout(PublishNewActivity.this);
		LinearLayout.LayoutParams params_image = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT, 1);
		layout_image.setLayoutParams(params_image);
		layout_image.setGravity(Gravity.CENTER);
		layout_image.setBackgroundDrawable(BackGroudSeletor.getdrawble("bg",
				PublishNewActivity.this));
		frameLayout_big = new FrameLayout(PublishNewActivity.this);
		FrameLayout.LayoutParams framelayout_Params = new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.WRAP_CONTENT,
				FrameLayout.LayoutParams.WRAP_CONTENT);
		frameLayout_big.setLayoutParams(framelayout_Params);
		frameLayout_big.setPadding(10, 10, 0, 0);
		layout_imagebound = new LinearLayout(PublishNewActivity.this);
		layout_imagebound.setPadding(2, 2, 2, 2);
		layout_imagebound.setBackgroundDrawable(BackGroudSeletor.getdrawble(
				"pic_biankuang2x", PublishNewActivity.this));

		layout_big_delete = new LinearLayout(PublishNewActivity.this);
		LinearLayout.LayoutParams image_layout_params = new LinearLayout.LayoutParams(
				getarea(lyout)[0] + 10, getarea(lyout)[1] + 10);
		layout_big_delete.setLayoutParams(image_layout_params);

		layout_imagebound.setGravity(Gravity.CENTER);
		layout_imagebound.setId(5011);
		layout_imagebound.setLayoutParams(new LayoutParams(getarea(lyout)[0],
				getarea(lyout)[1]));
		imageView_big = new ImageView(PublishNewActivity.this);
		imageView_big.setId(5012);
		// imageView_big.setImageDrawable(BackGroudSeletor.getdrawble(
		// "test2x", PublishActivity.this));
		layout_imagebound.addView(imageView_big);
		// layout_imagebound.setVisibility(View.GONE);
		imageView_delete = new ImageView(PublishNewActivity.this);
		imageView_delete.setId(5013);
		imageView_delete.setLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));
		imageView_delete.setImageDrawable(BackGroudSeletor.getdrawble("close",
				PublishNewActivity.this));
		layout_big_delete.addView(imageView_delete);
		frameLayout_big.addView(layout_imagebound);
		frameLayout_big.addView(layout_big_delete);
		frameLayout_big.setVisibility(View.GONE);
		layout_image.addView(frameLayout_big);

		viewroot.addView(layout_title);
		viewroot.addView(layout_content);
		viewroot.addView(layout_set);
		viewroot.addView(layout_image);
		return viewroot;
	}
/**
 * 对个空间设置监听
 * */
	private void setonclick() {
		button_esc.setOnClickListener(PublishNewActivity.this);
		button_send.setOnClickListener(PublishNewActivity.this);

		editText_text.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				try {

					Log.d("contentafter", s.toString().getBytes("gbk").length
							+ "");

				} catch (UnsupportedEncodingException e) {

					e.printStackTrace();
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				try {
					edstring = s.toString();
					String num = (140 - s.toString().getBytes("gbk").length / 2)
							+ "";
					Log.d("contentafter", num);
					textView_num.setText(num);

				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		imageView_bound.setOnClickListener(PublishNewActivity.this);

		imageView_delete.setOnClickListener(PublishNewActivity.this);

		button_friend.setOnClickListener(PublishNewActivity.this);
		button_conversation.setOnClickListener(PublishNewActivity.this);
		button_camera.setOnClickListener(PublishNewActivity.this);
		button_location.setOnClickListener(PublishNewActivity.this);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onClick(View v) {
		final InputMethodManager imm = (InputMethodManager) PublishNewActivity.this
				.getSystemService(INPUT_METHOD_SERVICE);
		switch (v.getId()) {
		case 5001:

			imm.hideSoftInputFromWindow(editText_text.getWindowToken(), 0);
			finish();
			break;
		case 5002:
			String content = editText_text.getText().toString();
			if ("".equals(content)
					&& frameLayout_icon.getVisibility() == View.GONE) {
				Toast.makeText(PublishNewActivity.this, "�����ݷ���",
						Toast.LENGTH_SHORT).show();
				break;
			}
			if (dialog != null && !dialog.isShowing()) {
				dialog.show();
			}
			if (Integer.parseInt(textView_num.getText().toString()) < 0) {
				Toast.makeText(PublishNewActivity.this, "��������������140���ֵ�����",
						Toast.LENGTH_SHORT).show();
			} else {
				double longitude = 0d;
				double latitude = 0d;
				if (mLocation != null) {
					longitude = mLocation.getLongitude();
					latitude = mLocation.getLatitude();
				}
				if (!frameLayout_icon.isShown()) {
					weiboAPI.addWeibo(context, content, "json", longitude,
							latitude, 0, 0, this, null, BaseVO.TYPE_JSON);
				} else if (frameLayout_icon.getVisibility() == View.VISIBLE) {
					weiboAPI.addPic(context, content, "json", longitude,
							latitude, mBitmap, 0, 0, this, null,
							BaseVO.TYPE_JSON);
				}

			}

			break;
		case 5005:
			// Toast.makeText(PublishActivity.this, "5005", 1000).show();
			imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
			// imm.showSoftInput(editText_text,InputMethodManager.SHOW_FORCED);
			break;
		case 5006:
			imm.hideSoftInputFromWindow(editText_text.getWindowToken(), 0);
			Intent intent_friend = new Intent();
			intent_friend.setClass(PublishNewActivity.this, FriendActivity.class);
			startActivityForResult(intent_friend, 5006);
			break;
		case 5007:
			imm.hideSoftInputFromWindow(editText_text.getWindowToken(), 0);
			Intent intent_conversation = new Intent();
			intent_conversation.setClass(PublishNewActivity.this,
					ConversationActivity.class);
			startActivityForResult(intent_conversation, 5007);
			break;
		case 5008:
			if (popupWindow != null && popupWindow.isShowing()) {
				popupWindow.dismiss();
				if (imm.isActive()) {
					imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
					// m.showSoftInput(editText_text,InputMethodManager.SHOW_FORCED);
				}

			} else {
				popupWindow = new PopupWindow(showView(),
						LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);

				// popupWindow.setFocusable(true);
				popupWindow.setTouchable(true);
				Timer timer = new Timer();
				timer.schedule(new TimerTask() {
					@Override
					public void run() {
						Message msg = handler.obtainMessage();
						msg.what = 0;
						handler.sendMessage(msg);
					}

				}, 500);

			}

			break;
		case 5009:
			new Thread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					Looper.prepare();
					Message msg = handler.obtainMessage();
					// if (location == null) {
					// LocationAction la = new LocationAction(context);
					// location = la.getLocation();
					// if (location != null) {
					// msg.what = 10;
					// handler.sendMessage(msg);
					// } else {
					// msg.what = 15;
					// handler.sendMessage(msg);
					// }
					// } else {
					// msg.what = 15;
					// }
					msg.what = 15;
					if (mLocation == null) {
						mLocation = Util.getLocation(context);
						if (mLocation != null) {
							msg.what = 10;
						}
					}
					handler.sendMessage(msg);
					Looper.loop();
				}

			}).start();

			break;
		case 5010:

			break;
		case 5013:
			frameLayout_icon.setVisibility(View.INVISIBLE);
			frameLayout_big.setVisibility(View.GONE);
			break;
		case 5014:
			edstring = editText_text.getText().toString();
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(intent, 2000);
			break;
		case 5015:
			edstring = editText_text.getText().toString();
			Intent i = new Intent(
					Intent.ACTION_PICK,
					android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			startActivityForResult(i, 1000);
			break;
		case 5016:

			if (popupWindow != null && popupWindow.isShowing()) {
				popupWindow.dismiss();
				editText_text.requestFocus();

				Timer timer = new Timer();
				timer.schedule(new TimerTask() {
					@Override
					public void run() {

						if (imm.isActive()) {
							imm.toggleSoftInput(0,
									InputMethodManager.HIDE_NOT_ALWAYS);
							// m.showSoftInput(editText_text,InputMethodManager.SHOW_FORCED);
						}

					}

				}, 100);

			}
			break;

		}

	}

	private Handler handler = new Handler() {

		@SuppressLint("HandlerLeak")
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			int flag = msg.what;
			if (flag == 5) {
				frameLayout_big.setVisibility(View.VISIBLE);
				frameLayout_icon.setVisibility(View.VISIBLE);
				return;
			}
			if (flag == 0) {
				popupWindow.showAsDropDown(layout_set);
				InputMethodManager imm = (InputMethodManager) PublishNewActivity.this
						.getSystemService(INPUT_METHOD_SERVICE);
				Log.d("alive", imm.isActive() + "");
				if (imm.isActive()) {
					imm.hideSoftInputFromWindow(editText_text.getWindowToken(),
							0);
					Log.d("alive", imm.isActive() + "");
				}
			}
			if (flag == 10) {
				BackGroudSeletor
						.getdrawble("dingwei_icon_hover2x",
								PublishNewActivity.this);
			}
			if (flag == 15) {
				Toast.makeText(PublishNewActivity.this, "��λʧ��", Toast.LENGTH_SHORT)
						.show();
				BackGroudSeletor
						.getdrawble("dingwei_icon2x", PublishNewActivity.this);
			}
		}

	};
   /**
    * 点击相机按钮弹出选择框，来�?择本地图片来源，可以是系统图库，也可以是拍照
    * */
	@SuppressWarnings("deprecation")
	private View showView() {
		LinearLayout camera = new LinearLayout(PublishNewActivity.this);
		camera.setLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));
		camera.setBackgroundDrawable(BackGroudSeletor.getdrawble("bg",
				PublishNewActivity.this));
		camera.setOrientation(LinearLayout.VERTICAL);
		camera.setPadding(50, 50, 50, 50);
		camera.setGravity(Gravity.CENTER);
		camera.requestFocus();
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		LinearLayout camera_layout = new LinearLayout(PublishNewActivity.this);
		camera_layout.setLayoutParams(params);
		camera_layout.setPadding(0, 0, 0, 0);
		LinearLayout pic_layout = new LinearLayout(PublishNewActivity.this);
		pic_layout.setLayoutParams(params);
		pic_layout.setPadding(0, 10, 0, 30);
		@SuppressWarnings("unused")
		LinearLayout esc_layout = new LinearLayout(PublishNewActivity.this);
		LinearLayout.LayoutParams button_Params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		Button camera_button = new Button(PublishNewActivity.this);
		camera_button.setId(5014);
		camera_button.setOnClickListener(PublishNewActivity.this);
		camera_button.setLayoutParams(button_Params);
		camera_button.setText("����");
		String camera_string[] = { "btn1_", "btn1_hover_" };
		camera_button.setBackgroundDrawable(BackGroudSeletor
				.createBgByImageIds(camera_string, PublishNewActivity.this));
		Button pic_button = new Button(PublishNewActivity.this);
		pic_button.setId(5015);
		pic_button.setOnClickListener(PublishNewActivity.this);
		pic_button.setLayoutParams(button_Params);
		pic_button.setText("���");
		pic_button.setBackgroundDrawable(BackGroudSeletor.createBgByImageIds(
				camera_string, PublishNewActivity.this));
		Button esc_bButton = new Button(PublishNewActivity.this);
		esc_bButton.setId(5016);
		esc_bButton.setOnClickListener(PublishNewActivity.this);
		esc_bButton.setLayoutParams(button_Params);
		esc_bButton.setText("ȡ��");
		String esc_string[] = { "btn2_", "btn1_hover_" };
		esc_bButton.setBackgroundDrawable(BackGroudSeletor.createBgByImageIds(
				esc_string, PublishNewActivity.this));
		pic_layout.addView(pic_button);
		camera.addView(camera_button);
		camera.addView(pic_layout);
		camera.addView(esc_bButton);
		return camera;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1000 && resultCode == RESULT_OK && null != data) {
			Uri selectedImage = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };
			Cursor cursor = getContentResolver().query(selectedImage,
					filePathColumn, null, null, null);
			cursor.moveToFirst();
			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String picturePath = cursor.getString(columnIndex);
			Log.d("path", picturePath + "");
			final int[] in = new int[2];
			try {
				FileInputStream fileInputStream = new FileInputStream(
						picturePath);
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inJustDecodeBounds = false;
				options.inSampleSize = 6;// 宽度和高度设置为原来�?/10
				Bitmap bitmap = BitmapFactory.decodeStream(fileInputStream,
						null, options);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
				mBitmap = bitmap;
				lyout[0] = bitmap.getWidth();
				lyout[1] = bitmap.getHeight();
				setContentView(initview());
				setonclick();
				imageView_icon.setImageDrawable(new BitmapDrawable(bitmap));
				imageView_big.setImageDrawable(new BitmapDrawable(bitmap));
				frameLayout_icon.setVisibility(View.VISIBLE);
				frameLayout_big.setVisibility(View.VISIBLE);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			cursor.close();

			if (popupWindow != null && popupWindow.isShowing()) {
				popupWindow.dismiss();

				Timer timer = new Timer();
				timer.schedule(new TimerTask() {
					@Override
					public void run() {

						InputMethodManager i = (InputMethodManager) PublishNewActivity.this
								.getSystemService(INPUT_METHOD_SERVICE);
						Log.d("mks", i.isActive() + "");

						i.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
						Message msg = handler.obtainMessage();
						msg.what = 5;
						handler.sendMessage(msg);

						// i.hideSoftInputFromWindow(PublishActivity.this.getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);

					}

				}, 100);
			}

		} else if (requestCode == 2000 && resultCode == RESULT_OK
				&& null != data) {
			if (popupWindow != null && popupWindow.isShowing()) {
				popupWindow.dismiss();
			}
			Bundle bundle = data.getExtras();
			Bitmap bitmap = (Bitmap) bundle.get("data");
			// MediaStore.Images.Media.insertImage(getContentResolver(), bitmap,
			// "myPhoto", "");
			sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
					Uri.parse("file://"
							+ Environment.getExternalStorageDirectory())));
			//
			// frameLayout_big.setVisibility(View.VISIBLE);
			// frameLayout_icon.setVisibility(View.VISIBLE);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
			mBitmap = bitmap;
			lyout[0] = bitmap.getWidth();
			lyout[1] = bitmap.getHeight();
			setContentView(initview());
			setonclick();
			imageView_icon.setImageDrawable(new BitmapDrawable(bitmap));
			imageView_big.setImageDrawable(new BitmapDrawable(bitmap));
			frameLayout_icon.setVisibility(View.VISIBLE);
			frameLayout_big.setVisibility(View.VISIBLE);
		} else if (requestCode == 5007 && resultCode == RESULT_OK
				&& null != data) {
			edstring = edstring + data.getStringExtra("conversation");
			editText_text.setText(edstring);
			editText_text.setSelection(edstring.length());
		} else if (requestCode == 5006 && resultCode == RESULT_OK
				&& null != data) {
			edstring = edstring + "@" + data.getStringExtra("firend");
			editText_text.setText(edstring);
			editText_text.setSelection(edstring.length());
		}
	}

	@Override
	public void onResult(Object object) {
		{
			if (dialog != null && dialog.isShowing()) {
				dialog.dismiss();
			}

			if (object != null) {
				ModelResult result = (ModelResult) object;
				if (result.isExpires()) {
					Toast.makeText(PublishNewActivity.this,
							result.getError_message(), Toast.LENGTH_SHORT)
							.show();
				} else {
					if (result.isSuccess()) {
						Toast.makeText(PublishNewActivity.this, "���ͳɹ�", 4000)
								.show();
						Log.d("���ͳɹ�", object.toString());
						finish();
					} else {
						Toast.makeText(PublishNewActivity.this,
								((ModelResult) object).getError_message(), 4000)
								.show();
					}
				}

			}

		}

	}
  /**
   * 计算图片大图显示时的伸缩后的宽高
   * */
	private int[] getarea(int area[]) {
		int myarea[] = new int[2];
		float temp = 0.0f;
		if (area != null) {
			if (area[0] > area[1] && area[0] >= 300) {
				myarea[0] = 300;
				temp = (float) area[1] / area[0];
				myarea[1] = (int) (temp * 300);
			} else if (area[0] > area[1] && area[0] < 300) {
				myarea[0] = area[0];
				myarea[1] = area[1];
			} else if (area[0] < area[1] && area[1] >= 300) {
				temp = (float) area[0] / area[1];
				myarea[0] = (int) (temp * 300);
				myarea[1] = 300;
			} else if (area[0] < area[1] && area[0] < 300) {
				myarea[0] = area[0];
				myarea[1] = area[1];
			} else if (area[0] == area[1] && area[0] >= 300) {
				myarea[0] = 300;
				myarea[1] = 300;
			} else if (area[0] == area[1] && area[0] < 300) {
				myarea[0] = area[0];
				myarea[1] = area[1];
			}
		}
		Log.d("myarea", myarea[0] + "....." + myarea[1]);
		return myarea;
	}

	/***
	 * 图片的缩放方�?
	 * 
	 * @param bgimage
	 *            ：源图片资源
	 * @param newWidth
	 *            ：缩放后宽度
	 * @param newHeight
	 *            ：缩放后高度
	 * @return  可用的图�?bitmap对象
	 */
	public Bitmap zoomImage(Bitmap bm, double newWidth, double newHeight) {

		// 获取这个图片的宽和高
		float width = bm.getWidth();
		float height = bm.getHeight();
		// 创建操作图片用的matrix对象
		Matrix matrix = new Matrix();
		// 计算宽高缩放�?
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		// 缩放图片动作
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap bitmap = Bitmap.createBitmap(bm, 0, 0, (int) width,
				(int) height, matrix, true);
		// Options op = new Options();
		// op.inSampleSize=(int)maxSize;
		// Bitmap bitmap = BitmapFactory.decodeFile(op);
		//
		// try{
		// ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		// InputStream temp = new
		// ByteArrayInputStream(baos.toByteArray());//this.getAssets().open(path);
		//
		// BitmapFactory.Options options = new BitmapFactory.Options();
		// // 这个参数代表，不为bitmap分配内存空间，只记录�?��该图片的信息（例如图片大小），说白了就是为了内存优化
		// options.inJustDecodeBounds = true;
		// // 通过创建图片的方式，取得options的内容（这里就是利用了java的地�?��递来赋�?�?
		// BitmapFactory.decodeStream(temp, null, options);
		// // 关闭�?
		// temp.close();
		//
		// // 生成压缩的图�?
		// int i = 0;
		// Bitmap bitmap = null;
		// while (true) {
		// // 这一步是根据要设置的大小，使宽和高都能满�?
		// if ((options.outWidth >> i <= maxSize)
		// && (options.outHeight >> i <= maxSize)) {
		// // 重新取得流，注意：这里一定要再次加载，不能二次使用之前的流！
		// temp = new
		// ByteArrayInputStream(baos.toByteArray());//this.getAssets().open(path);
		// // 这个参数表示 新生成的图片为原始图片的几分之一�?
		// options.inSampleSize = (int)maxSize;//(int) Math.pow(2.0D, i);
		// // 这里之前设置为了true，所以要改为false，否则就创建不出图片
		// options.inJustDecodeBounds = false;
		//
		// bitmap = BitmapFactory.decodeStream(temp, null, options);
		// break;
		// }
		// i += 1;
		// }
		// }catch(Exception e){
		// e.printStackTrace();
		// }
		return bitmap;
	}
}
