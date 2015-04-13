package com.whu.dailyexercise.exerciseplan;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.whu.dailyexercise.exercisemethod.MethodEntity;

public class StaticPlanHelper {
	public static List<StaticPlanEntity> getStaticPlanListByType(int planType,SQLiteDatabase db)
	{
		List<StaticPlanEntity> planlist=new ArrayList<StaticPlanEntity>();
		String sql="";
		if(planType<0)
		{
			sql="select * from preparedplan";
		}
		else
		{
			sql="select * from preparedplan where plantype="+planType+"";
		}
		Cursor cr=db.rawQuery(sql,null);
		while(cr.moveToNext())
		{
			StaticPlanEntity a=new StaticPlanEntity();
			a.setMethodid(cr.getString(cr.getColumnIndex("methodid")));
			a.setPlanname(cr.getString(cr.getColumnIndex("planname")));
			a.setPlanintroduction(cr.getString(cr.getColumnIndex("planintroduction")));
			a.setPictureurl(cr.getString(cr.getColumnIndex("pictureurl")));
			a.setPlanflag(cr.getString(cr.getColumnIndex("planflag")));
			a.setPlantype(cr.getInt(cr.getColumnIndex("plantype")));
			a.setPrepareplanid(cr.getInt(cr.getColumnIndex("prepareplanid")));
			planlist.add(a);
		}
		
		cr.close();
		
		return planlist;
	}
	
	public static int getStaticPlanMaxFlagNumber(SQLiteDatabase db)
	{
		int maxFlag=0;
		String sql="select plantype from preparedplan order by plantype desc limit 1";	
		Cursor cr=db.rawQuery(sql, null);
		while(cr.moveToNext())
		{
			maxFlag=cr.getInt(cr.getColumnIndex("plantype"));
			Log.d("fff", Integer.toString(maxFlag));
		}
		return maxFlag;
	}
	
	public static List<ArrayList<StaticPlanEntity>> getStaticPlanCollection(SQLiteDatabase db)
	{
		List<ArrayList<StaticPlanEntity>> a=new ArrayList<ArrayList<StaticPlanEntity>>();
		
		for(int i=0;i<=getStaticPlanMaxFlagNumber(db);i++)
		{
			a.add((ArrayList<StaticPlanEntity>) getStaticPlanListByType(i,db));
		}
		return a;
	}

}
