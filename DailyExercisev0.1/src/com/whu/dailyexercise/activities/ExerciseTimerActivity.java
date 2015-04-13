package com.whu.dailyexercise.activities;

import java.util.List;
import java.util.Timer;
import com.whu.dailyexercise.R;
import com.whu.dailyexercise.R.drawable;
import com.whu.dailyexercise.exercisemethod.MethodDao;
import com.whu.dailyexercise.exercisemethod.MethodEntity;
import com.whu.dailyexercise.util.CommonField;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Resources;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


@SuppressLint("HandlerLeak")
public class ExerciseTimerActivity extends Activity {

	Timer timer = new Timer(); 
	private MediaPlayer mp;//媒体文件
	private RelativeLayout play;
	private RelativeLayout sound;
	private boolean isPause=true;//设置是否暂停
	private boolean soundEnabled = true;//设置是否有声音
	private AudioManager audioManager;
	private TextView showTime;
	private MyCount mc;//计时器
	private long millisInFuture=30000;
	private RelativeLayout imgComplete;
	private int iExerciseType;
	private int iExerciseItem;
	private MethodEntity localMethod;
	private ImageView stepimage;
	private int[] stepImageId;
	private int iStep = 0;
	private TextView stepText;
	private TextView img_Pause;
	private ImageView img_sound;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exercise_timer);
		
		iExerciseType = getIntent().getIntExtra("ExerciseType", -1);
		iExerciseItem = getIntent().getIntExtra("ExerciseItem", -1);
		
		img_Pause = (TextView)findViewById(R.id.tvVoicePlayPause);
		
		localMethod = CommonField.structEntityList.get(iExerciseType).get(iExerciseItem);

		millisInFuture = localMethod.getPractime()*60*1000;
		play=(RelativeLayout)findViewById(R.id.rlVoicePlayPause);
		sound=(RelativeLayout)findViewById(R.id.rlVoicePlaySetting);
		img_sound = (ImageView)findViewById(R.id.ivVoicePlaySound);
		showTime=(TextView)findViewById(R.id.btnVoicePlaySkipDesign);
		showTime.setText("" + formatTime(millisInFuture));
		
		imgComplete=(RelativeLayout)findViewById(R.id.rlVoicePlayList);
		
		stepimage = (ImageView)findViewById(R.id.stepImage);
		List<String> picList = MethodDao.getMethodPictureById(localMethod.getMethodid(), CommonField.sqlitedatabasa);
		stepImageId = new int[picList.size()];
		for(int i = 0;i<picList.size();i++)
		{
			Resources res = getResources();
			Object ob=res.getIdentifier(picList.get(i),"drawable",getPackageName());
			stepImageId[i] = (Integer) ob;
		}
		stepimage.setImageResource(stepImageId[0]);
		
		stepText = (TextView)findViewById(R.id.tvVoicePlayExerDetail);
		String stepStr = localMethod.getMethodname()+"("+1+"/"+picList.size()+")";
		stepText.setText(stepStr);
		
		//加载相应资源
		mp=MediaPlayer.create(this,R.raw.apologize);
		audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
		mc=new MyCount(millisInFuture,1000);
		
		play.setOnClickListener(new OnClickListener(){
			public void onClick(View v)
			{
				controlExercise();
			}
		});
		
		sound.setOnClickListener(new OnClickListener(){
			public void onClick(View v)
			{
				controlSound();
				
			}
		});
		
		imgComplete.setOnClickListener(new OnClickListener(){
			public void onClick(View v)
			{
				mc.onFinish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.exercise_timer, menu);
		return true;
	}
	
	
	//是否播放音乐和时间
	public void controlExercise()
	{
		int currentPosition=0;
		if(isPause)
		{
			try {
				mc.start();
				mp.start();
				mp.setLooping(true);
				
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			Toast.makeText(ExerciseTimerActivity.this, "开启成功",  
                    Toast.LENGTH_SHORT).show();  
			isPause=false;
			img_Pause.setBackgroundResource(drawable.voiceplay_pause);
		}
		else
		{
			if(mp.isPlaying())
			{
				mp.pause();
				mc.cancel();
				/**
				 * activity里面onCreate()函数是初始化函数，我在这里第一次实例化一个mc对象
				 * 当调用mc.cancel()的时候会结束这个计时器，我再new 一个新的mc对象，即mc=new MyCount(mc.remainmills,1000)
				 * CountDownTimer里面的onTick()实际上是每间隔countDownInterval就会被回调一次，里面的millisUntilFinished
				 * 就是剩余倒计时时间。onFinish()函数是倒计时结束后自动调用的，在这面释放音乐资源比较好
				 */
				mc=new MyCount(mc.remainmills,1000);
				currentPosition=mp.getCurrentPosition();//记录当前位置
				Log.d("asfg",Integer.toString(currentPosition));
				mp.seekTo(currentPosition);//设置音乐开始的位置
				img_Pause.setBackgroundResource(drawable.voiceplay_play);
			}
				
			Toast.makeText(ExerciseTimerActivity.this, "暂停成功",  
                    Toast.LENGTH_SHORT).show();  
			isPause=true;
		}
	}
	
	//是否静音
	public void controlSound()
	{
		 if (soundEnabled) {
             audioManager.setStreamMute(AudioManager.STREAM_MUSIC , true);
             img_sound.setImageResource(drawable.icon_sound_off);
         } else {
             audioManager.setStreamMute(AudioManager.STREAM_MUSIC , false);
             img_sound.setImageResource(drawable.icon_sound_on);
         }
         soundEnabled = !soundEnabled;
	}
	
	public String formatTime(long millis) {  
	    String output = "00:00";  
	    long seconds = millis / 1000;  
	    long minutes = seconds / 60;  

	    seconds = seconds % 60;  
	    minutes = minutes % 60;  

	    String sec = String.valueOf(seconds);  
	    String min = String.valueOf(minutes);  

	    if (seconds < 10)  
	        sec = "0" + seconds;  
	    if (minutes < 10)  
	        min= "0" + minutes;  

	    output = min + " : " + sec;  
	    return output;
	}

    class MyCount extends CountDownTimer {  
		long remainmills=0;
	    public MyCount(long millisInFuture, long countDownInterval) {  
	        super(millisInFuture, countDownInterval); 
	        this.remainmills=millisInFuture;
	        
	    }//MyCount  
	    
	    public void onTick(long millisUntilFinished) {
	    	 this.remainmills=millisUntilFinished;
	    	 showTime.setText("" + formatTime(millisUntilFinished));     
	    	 stepimage.setImageResource(stepImageId[iStep]);
	    	 int iTemp = iStep+1;
	    	 String stepStr = localMethod.getMethodname()+"("+iTemp+"/"+stepImageId.length+")";
	 		 stepText.setText(stepStr);
	    	 if(iStep==stepImageId.length-1){iStep = 0;}
	    	 else{iStep++;};      
	    }//on tick  

	    @Override  
	    public void onFinish() {  
	    	audioManager.setStreamMute(AudioManager.STREAM_MUSIC , false);
	        onStop(); 
	        showTime.setText("" + formatTime(0));
	        mp.stop();
	        mp.release();
	        Toast.makeText(ExerciseTimerActivity.this, "倒计时结束",  
                    Toast.LENGTH_SHORT).show(); 
	        finish();
	    } 
	}
}

