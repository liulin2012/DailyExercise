package com.whu.dailyexercise.exercisedata;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ExerciseStatistics {
	//根据日期得到用户每天消耗的热量
	public static float getHeatAmountByDate(String userid,String date,SQLiteDatabase sqlitedatabase)
	{
		float dayHeatAmount=0;
		String sql="select sum(heataccount) as abc from trainplan,dayplan " +
				"where trainplan.userid='"+userid+"' and " +
				"dayplan.date='"+date+"' and trainplan.planid=dayplan.planid";
		Cursor cr=sqlitedatabase.rawQuery(sql, null);
		while(cr.moveToNext())
		{
			dayHeatAmount=cr.getFloat(cr.getColumnIndex("abc"));
		}
		cr.close();
		return dayHeatAmount;
	}
	
	//根据日期得到用户最近一周消耗的热量
	public static float getAllWeekHeatAmount(String userid,String nowdate,String olddate,SQLiteDatabase sqlitedatabase)
	{
		float weekHeatAmount=0;
		String sql="select sum(heataccount) as abc from trainplan,dayplan " +
				"where dayplan.date between '"+olddate+"' and '"+nowdate+"' " +
				"and dayplan.planid=trainplan.planid and trainplan.userid='"+userid+"'";
		Cursor cr=sqlitedatabase.rawQuery(sql, null);
		while(cr.moveToNext())
		{
			weekHeatAmount=cr.getFloat(cr.getColumnIndex("abc"));
		}
		cr.close();
		return weekHeatAmount;
	}
	
	//根据日期得到用户最近一月消耗的热量
	public static float getAllMonthHeatAmount(String userid,String nowdate, String olddate,SQLiteDatabase sqlitedatabase)
	{
		float monthHeatAmount=0;
		String sql="select sum(heataccount) as abc from trainplan,dayplan " +
				"where dayplan.date between '"+olddate+"' and '"+nowdate+"' " +
				"and dayplan.planid=trainplan.planid and trainplan.userid='"+userid+"'";
		Cursor cr=sqlitedatabase.rawQuery(sql, null);
		while(cr.moveToNext())
		{
			monthHeatAmount=cr.getFloat(cr.getColumnIndex("abc"));
		}
		cr.close();
		return monthHeatAmount;
	}
	
	//根据日期得到用户最近一周每天消耗的热量
		public static List<Float> getEveryDayHeatAmount(String userid, Date nowdate,SQLiteDatabase sqlitedatabase)
		{
			List<Float> alist=new ArrayList<Float>();
			float dayHeatAmount=0;
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			List<String> sqls=new ArrayList<String>();
			for(int i=0;i<7;i++)
			{
				Date eachday=new Date(nowdate.getTime()-(6-i)*24 * 60 * 60 * 1000);
				String day=simpleDateFormat.format(eachday);
				sqls.add("select sum(heataccount) as abc from trainplan,dayplan where dayplan.date='"+day+"' and trainplan.userid='"+userid+"' and trainplan.planid=dayplan.planid");
			}
			for(String sql:sqls)
			{
				Cursor cr=sqlitedatabase.rawQuery(sql, null);
				if(cr.moveToNext())
				{
					dayHeatAmount=cr.getFloat(cr.getColumnIndex("abc"));
					alist.add(dayHeatAmount);
				}
				else
				{
					dayHeatAmount=0;
					alist.add(dayHeatAmount);
				}
			}
			
			return alist;
		}

	
	//根据用户名得到用户累计消耗热量
	public static float getAllHeatAmount(String userid,SQLiteDatabase sqlitedatabase)
	{
		float allHeatAmount=0;
		String sql="select sum(dayplan.heataccount) as abc from trainplan, dayplan where trainplan.userid='"+userid+"' and trainplan.planid=dayplan.planid";
		Cursor cr=sqlitedatabase.rawQuery(sql, null);
		while(cr.moveToNext())
		{
			allHeatAmount=cr.getFloat(cr.getColumnIndex("abc"));
		}
		cr.close();
		return allHeatAmount;
	}

}
