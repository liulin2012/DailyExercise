package com.whu.dailyexercise.activities;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;
import com.whu.dailyexercise.R;
import com.whu.dailyexercise.exercisedata.ExerciseStatistics;
import com.whu.dailyexercise.util.CommonField;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.view.Menu;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

@SuppressLint("SimpleDateFormat")
public class ExerciseDataActivity extends Activity {

	public WebView wv;
	private TextView today_heat;
	private TextView total_heat;
	private TextView week_heat;
	private TextView month_heat;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exercise_data);
		
//		wv = (WebView) findViewById(R.id.wv);
//		wv.getSettings().setJavaScriptEnabled(true);  
//		wv.getSettings().setUseWideViewPort(true);
//		//wv.getSettings().setSupportZoom(true);
//		//wv.getSettings().setBuiltInZoomControls(true);
//		wv.getSettings().setLoadWithOverviewMode(true);
//		wv.requestFocus();
//		wv.loadUrl("file:///android_asset/mianji_chart.html");
//		ExerciseDataLogic.updateData(wv);
		
		today_heat = (TextView)findViewById(R.id.today_heat);
		float fTodayHeat = ExerciseStatistics.getHeatAmountByDate(CommonField.userId,CommonField.sToday,CommonField.sqlitedatabasa);
		String sTodayHeat = ""+fTodayHeat;
		today_heat.setText(sTodayHeat);
		
		total_heat = (TextView)findViewById(R.id.total_heat);
		float fTotalHeat = ExerciseStatistics.getAllHeatAmount(CommonField.userId,CommonField.sqlitedatabasa);
		String sTotalHeat = ""+fTotalHeat;
		total_heat.setText(sTotalHeat);
		
		week_heat = (TextView)findViewById(R.id.week_heat);
		Date firstday = new Date(CommonField.dToday.getTime() - 6 * 24 * 60 * 60 * 1000);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String sFirstDay = simpleDateFormat.format(firstday);
		float fWeekHeat = ExerciseStatistics.getAllWeekHeatAmount(CommonField.userId,CommonField.sToday,sFirstDay,CommonField.sqlitedatabasa);
		String sWeekHeat = ""+fWeekHeat;
		week_heat.setText(sWeekHeat);
		
		month_heat = (TextView)findViewById(R.id.month_heat);
		Date monthfirstday = new Date(CommonField.dToday.getTime() - 9 * 24 * 60 * 60 * 1000-7 * 24 * 60 * 60 * 1000-7 * 24 * 60 * 60 * 1000-7 * 24 * 60 * 60 * 1000);
		String sMonthFirstDay = simpleDateFormat.format(monthfirstday);
		float fMonthHeat = ExerciseStatistics.getAllMonthHeatAmount(CommonField.userId,CommonField.sToday,sMonthFirstDay,CommonField.sqlitedatabasa);
		String sMonthHeat = ""+fMonthHeat;
		month_heat.setText(sMonthHeat);
		
		List<Float> datalist = ExerciseStatistics.getEveryDayHeatAmount(CommonField.userId,CommonField.dToday,CommonField.sqlitedatabasa);
		int num = datalist.size();
		GraphViewData[] data = new GraphViewData[num];
		for (int i=0; i<num; i++) {
		  data[i] = new GraphViewData(i, datalist.get(i));
		}
		GraphView graphView = new LineGraphView(
		    this
		    , "近一周每日消耗（Cal）"
		);

		// add data
		graphView.addSeries(new GraphViewSeries(data));
		graphView.setHorizontalLabels(new String[] {"6天前", "5天前", "4天前", "3天前","前天","昨天","今天"});
		graphView.setViewPort(0, 6);
		graphView.getGraphViewStyle().setHorizontalLabelsColor(Color.BLACK);
		graphView.getGraphViewStyle().setVerticalLabelsColor(Color.BLACK);
		graphView.getGraphViewStyle().setTextSize(32);
		LinearLayout layout = (LinearLayout) findViewById(R.id.graph_view);
		layout.addView(graphView);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.exercise_data, menu);
		return true;
	}

}
