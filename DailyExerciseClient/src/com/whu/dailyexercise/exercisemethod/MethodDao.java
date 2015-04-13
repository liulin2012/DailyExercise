package com.whu.dailyexercise.exercisemethod;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class MethodDao {
	public static List<MethodEntity> getAllMethodInfo(int methodflag,SQLiteDatabase db)
	{
		List<MethodEntity> methodlist=new ArrayList<MethodEntity>();
		Log.d("dd", "zhazha");
		String sql="";
		if(methodflag<0)
		{
			sql="select * from method";
		}
		else
		{
			sql="select * from method where methodflag="+methodflag+"";
		}
		Cursor cr=db.rawQuery(sql,null);
		while(cr.moveToNext())
		{
			MethodEntity a=new MethodEntity();
			a.setMethodid(cr.getInt(cr.getColumnIndex("methodid")));
			a.setMethodname(cr.getString(cr.getColumnIndex("methodname")));
			a.setIntroduction(cr.getString(cr.getColumnIndex("introduction")));
			a.setPictureurl(cr.getString(cr.getColumnIndex("pictureurl")));
			a.setVideourl(cr.getString(cr.getColumnIndex("videourl")));
			a.setUnitheat(cr.getFloat(cr.getColumnIndex("unitheat")));
			a.setPractime(cr.getInt(cr.getColumnIndex("practicetime")));
			methodlist.add(a);
		}
		
		cr.close();
		
		return methodlist;
	}
	
	public static int getMaxFlagNumber(SQLiteDatabase db)
	{
		int maxFlag=0;
		String sql="select methodflag from method order by methodflag desc limit 1";	
		Cursor cr=db.rawQuery(sql, null);
		while(cr.moveToNext())
		{
			maxFlag=cr.getInt(cr.getColumnIndex("methodflag"));
			Log.d("fff", Integer.toString(maxFlag));
		}
		return maxFlag;
	}
	
	public static List<ArrayList<MethodEntity>> getMethodCollection(SQLiteDatabase db)
	{
		List<ArrayList<MethodEntity>> a=new ArrayList<ArrayList<MethodEntity>>();
		
		for(int i=1;i<=getMaxFlagNumber(db);i++)
		{
			a.add((ArrayList<MethodEntity>) getAllMethodInfo(i,db));
		}
		return a;
	}
	
	public static List<MethodEntity> getMethodInfoById(String methodid, SQLiteDatabase sqlitedatabase)
	{
		List<MethodEntity> alist=new ArrayList<MethodEntity>();
		List<String> sqls=new ArrayList<String>();
		String[] planmethodid=methodid.split("-");
		for(int i=1;i<planmethodid.length;i++){
			sqls.add("select * from method where methodid="+Integer.parseInt(planmethodid[i])+"");
		}
		Cursor cr=null;
		for(String sql:sqls){
			cr=sqlitedatabase.rawQuery(sql, null);
			MethodEntity a=new MethodEntity();
			while(cr.moveToNext())
			{
				a.setMethodname(cr.getString(cr.getColumnIndex("methodname")));
				a.setIntroduction(cr.getString(cr.getColumnIndex("introduction")));
				a.setPictureurl(cr.getString(cr.getColumnIndex("pictureurl")));
				a.setVideourl(cr.getString(cr.getColumnIndex("videourl")));
				a.setUnitheat(cr.getFloat(cr.getColumnIndex("unitheat")));
				a.setPractime(cr.getInt(cr.getColumnIndex("practicetime")));
				a.setMethodid(cr.getInt(cr.getColumnIndex("methodid")));
				alist.add(a);
			}
		}
		cr.close();
		return alist;
	}
	
	public static List<String> getMethodPictureById(int methodid, SQLiteDatabase sqlitedatabase)
	{
		List<String> alist=new ArrayList<String>();
		String sql="select pictureurl from methodpicture where methodid="+methodid+"";
		Cursor cr=sqlitedatabase.rawQuery(sql, null);
		while(cr.moveToNext())
		{
			String a=cr.getString(cr.getColumnIndex("pictureurl"));
			alist.add(a);
		}
		cr.close();
		return alist;
	}
}
