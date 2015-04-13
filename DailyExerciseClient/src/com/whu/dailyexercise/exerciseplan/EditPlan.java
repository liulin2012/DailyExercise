package com.whu.dailyexercise.exerciseplan;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class EditPlan {
	//添加计划
	public static int addPlan(String userid, int[] methodid, String completepercent,
			String[] date, float heataccount, int isoverdue, String planname,
			int weeknumber, int[] weekday, SQLiteDatabase sqlitedatabase) {
		int isadd = 0;
		try {
			String mymethodid = "";
			for (int i = 0; i < methodid.length; i++) {
				mymethodid = mymethodid + "-" + methodid[i];
			}

			int[] iscomplete = new int[methodid.length];
			for (int i = 0; i < iscomplete.length; i++) {
				iscomplete[i] = 0;
			}
			String myiscomplete = "";
			for (int i = 0; i < iscomplete.length; i++) {
				myiscomplete = myiscomplete + "-" + iscomplete[i];
			}
			String myweekday = "";
			for (int i = 0; i < weekday.length; i++) {
				myweekday = myweekday + "-" + weekday[i];
			}

			String sql1 = "insert into trainplan(methodid,completepercent,userid,isoverdue,planname,begintime,weeknumber,weekday) values('"
					+ mymethodid
					+ "','"
					+ completepercent
					+ "','"
					+ userid
					+ "','"
					+ isoverdue
					+ "','"
					+ planname
					+ "','"
					+ date[0]
					+ "','" + weeknumber + "','" + myweekday + "')";
			sqlitedatabase.execSQL(sql1);
			String sql2 = "select planid from trainplan order by planid desc limit 1";
			Cursor cr = sqlitedatabase.rawQuery(sql2, null);
			int planid = -1;
			while (cr.moveToNext()) {
				planid = cr.getInt(cr.getColumnIndex("planid"));
				Log.d("bb", "success");
			}
			List<String> sqls = new ArrayList<String>();
			for (int i = 0; i < date.length; i++) {
				sqls.add("insert into dayplan(date,iscomplete,heataccount,planid) values ('"
						+ date[i]
						+ "','"
						+ myiscomplete
						+ "',"
						+ heataccount
						+ "," + planid + ")");
			}
			for (String sql : sqls) {
				sqlitedatabase.execSQL(sql);
			}
			isadd = 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isadd;
	}
	//更新计划名字
	public static int updateNamePlan(int planid, String planname,
			SQLiteDatabase sqlitedatabase) {
		int isupdate = 0;
		String sql = "update trainplan set planname='" + planname
				+ "' where planid=" + planid + "";
		try {
			sqlitedatabase.execSQL(sql);
			isupdate = 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isupdate;
	}
	//删除计划
	public static int deletePlan(int planid, SQLiteDatabase sqlitedatabase)
	{
		int isdelete=0;
		String sql="delete from trainplan where planid="+planid+"";
		try{
			sqlitedatabase.execSQL(sql);
			isdelete=1;
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return isdelete;

	}
	//得到用户所有计划
	public static List<PlanEntity> getAllPlanInfo(String userid, SQLiteDatabase sqlitedatabase)
	{
		List<PlanEntity> alist=new ArrayList<PlanEntity>();
		String sql="select * from trainplan where userid='"+userid+"'";
		Cursor cr=sqlitedatabase.rawQuery(sql,null);
		while(cr.moveToNext())
		{
			PlanEntity a=new PlanEntity();
			a.setPlanname(cr.getString(cr.getColumnIndex("planname")));
			a.setPlanid(cr.getInt(cr.getColumnIndex("planid")));
			a.setIsoverdue(cr.getInt(cr.getColumnIndex("isoverdue")));
			a.setMethodid(cr.getString(cr.getColumnIndex("methodid")));
			a.setBegindate(cr.getString(cr.getColumnIndex("begintime")));
			a.setWeeknumber(cr.getInt(cr.getColumnIndex("weeknumber")));
			a.setWeekday(cr.getString(cr.getColumnIndex("weekday")));
			a.setCompletepercent(cr.getString(cr.getColumnIndex("completepercent")));
			a.setUserid(cr.getString(cr.getColumnIndex("userid")));

			alist.add(a);
		}
		cr.close();
		return alist;
	}
	
	//根据日期得到当天计划
	public static List<PlanEntity> getPlanByDate(String userid, String date,SQLiteDatabase sqlitedatabase)
	{
		List<PlanEntity> alist=new ArrayList<PlanEntity>();
		String sql="select * from trainplan, dayplan where trainplan.userid='"+userid+"'and dayplan.date='"+date+"'and trainplan.planid=dayplan.planid";
		Cursor cr=sqlitedatabase.rawQuery(sql, null);
		while(cr.moveToNext())
		{
			PlanEntity a=new PlanEntity();
			
			a.setBegindate(cr.getString(cr.getColumnIndex("begintime")));
			a.setCompletepercent(cr.getString(cr.getColumnIndex("completepercent")));
			a.setMethodid(cr.getString(cr.getColumnIndex("methodid")));
			a.setPlanname(cr.getString(cr.getColumnIndex("planname")));
			a.setWeeknumber(cr.getInt(cr.getColumnIndex("weeknumber")));
			a.setWeekday(cr.getString(cr.getColumnIndex("weekday")));
			a.setPlanid(cr.getInt(cr.getColumnIndex("planid")));
			alist.add(a);
		}
		cr.close();
		return alist;
	}
	
	public static int[] getDayExerciseCompleteByDate(int planid, String date, SQLiteDatabase sqlitedatabase)
	{
		String sql="select * from dayplan where planid="+planid+" and date='"+date+"'";
		String iscomplete="";
		Cursor cr=sqlitedatabase.rawQuery(sql, null);
		while(cr.moveToNext())
		{
			iscomplete=cr.getString(cr.getColumnIndex("iscomplete"));
		}
		String[] myiscomplete=iscomplete.split("-");
		int [] daycomplete=new int[myiscomplete.length-1];
		for(int i=1;i<myiscomplete.length;i++)
		{
			daycomplete[i-1]=Integer.parseInt(myiscomplete[i]);
		}
		return daycomplete;
	}
	
	//训练完项目后更新完成项
		public static int updateComplete(int planid, int methodid, String date, SQLiteDatabase sqlitedatabase)
		{
			int isExercise=0;
			String sql1="select * from trainplan where planid="+planid+"";
			String sql2="select * from dayplan where planid="+planid+" and date='"+date+"'";
			
			Cursor cr1=sqlitedatabase.rawQuery(sql1, null);
			String oldmethodid="";
			String oldiscomplete="";
			while(cr1.moveToNext())
			{
				oldmethodid=cr1.getString(cr1.getColumnIndex("methodid"));
			}
			cr1.close();
			Cursor cr2=sqlitedatabase.rawQuery(sql2, null);
			while(cr2.moveToNext())
			{
				oldiscomplete=cr2.getString(cr2.getColumnIndex("iscomplete"));
			}
			cr2.close();
			String[] myoldmethodid=oldmethodid.split("-");
			int [] mymethodid=new int[myoldmethodid.length-1];
			for(int i=1;i<myoldmethodid.length;i++)
			{
				mymethodid[i-1]=Integer.parseInt(myoldmethodid[i]);
			}
			
			String[] myoldiscomplete=oldiscomplete.split("-");
			int [] myiscomplete=new int[myoldiscomplete.length-1];
			for(int i=1;i<myoldiscomplete.length;i++)
			{
				myiscomplete[i-1]=Integer.parseInt(myoldiscomplete[i]);
			}
			for(int i=0;i<mymethodid.length;i++)
			{
				if(mymethodid[i]==methodid)
				{
					myiscomplete[i]=1;
				}
			}
			String newmyiscomplete="";
			for(int i=0;i<myiscomplete.length;i++)
			{
				newmyiscomplete=newmyiscomplete+"-"+myiscomplete[i];
			}
			
			String sql3="update dayplan set iscomplete='"+newmyiscomplete+"' where date='"+date+"' and planid="+planid+"";
			
			//更新热量
			float heataccount=0;
			String sql4="select * from method where methodid="+methodid+"";
			Cursor cr4=sqlitedatabase.rawQuery(sql4, null);
			while(cr4.moveToNext())
			{
				heataccount=cr4.getFloat(cr4.getColumnIndex("unitheat"));
			}
			cr4.close();
			String sql5="update dayplan set heataccount=heataccount+"+heataccount+" where date='"+date+"' and planid="+planid+"";
			try{
				sqlitedatabase.execSQL(sql3);
				sqlitedatabase.execSQL(sql5);
				isExercise=1;
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			
			Log.d("fgh",Integer.toString(isExercise));
			return isExercise;
		}

	
	//根据用户得到所有天的计划
		public static List<DayEntity> getAllDayInfo(String userid, SQLiteDatabase sqlitedatabase)
		{
			List<DayEntity> daylist=new ArrayList<DayEntity>();
			String sql="select dayplan.date,dayplan.dayid,dayplan.iscomplete, dayplan.planid, dayplan.heataccount from trainplan,dayplan where trainplan.userid='"+userid+"' and trainplan.planid=dayplan.planid";
			Cursor cr=sqlitedatabase.rawQuery(sql, null);
			while(cr.moveToNext())
			{
				DayEntity a=new DayEntity();
				a.setDayid(cr.getInt(cr.getColumnIndex("dayid")));
				a.setDate(cr.getString(cr.getColumnIndex("date")));
				a.setHeataccount(cr.getFloat(cr.getColumnIndex("heataccount")));
				a.setIscomplete(cr.getString(cr.getColumnIndex("iscomplete")));
				a.setPlanid(cr.getInt(cr.getColumnIndex("planid")));
				daylist.add(a);
			}
			cr.close();
			return daylist;
		}
		
		//同步更新计划
		public static void downLoadTrainplan(String planid,String methodid, 
				String completepercent,String isoverdue, String begintime, 
				String weeknumber, String weekday, String planname, String userid,SQLiteDatabase sqlitedatabase)
		{
			int myplanid=Integer.parseInt(planid);
			int myisoverdue=Integer.parseInt(isoverdue);
			int myweeknumber=Integer.parseInt(weeknumber);
			String sql1="select * from trainplan where planid="+myplanid+"";
			Cursor cr1=sqlitedatabase.rawQuery(sql1, null);
			if(cr1.moveToNext())
			{
				String sql2="update trainplan set methodid='"+methodid+"',completepercent='"+completepercent+"', isoverdue="+myisoverdue+", begintime='"+begintime+"', weeknumber="+myweeknumber+", weekday='"+weekday+"',planname='"+planname+"' where planid="+myplanid+"";
				try{
					sqlitedatabase.execSQL(sql2);
				}catch(Exception e)
				{
					e.printStackTrace();
				}
			}
			else
			{
				String sql3="insert into trainplan(planid,methodid,completepercent,isoverdue,begintime,weeknumber,weekday,planname,userid) values("+myplanid+",'"+methodid+"','"+completepercent+"',"+isoverdue+",'"+begintime+"',"+myweeknumber+",'"+weekday+"','"+planname+"','"+userid+"')";
				try{
					sqlitedatabase.execSQL(sql3);
				}catch(Exception e)
				{
					e.printStackTrace();
				}
			}
			cr1.close();
		}
		
		//同步更新dayplan
		public static void downLoadDayPlan(String dayid,String date,String iscomplete,String heataccount,String planid,SQLiteDatabase sqlitedatabase)
		{
			int mydayid=Integer.parseInt(dayid);
			int myplanid=Integer.parseInt(planid);
			float myheataccount=Float.parseFloat(heataccount);
			String sql1="select * from dayplan where dayid="+mydayid+"";
			Cursor cr=sqlitedatabase.rawQuery(sql1, null);
			if(cr.moveToNext())
			{
				String sql2="update dayplan set date='"+date+"', iscomplete='"+iscomplete+"', heataccount="+heataccount+", planid="+planid+" where dayid="+mydayid+"";
				try{
					sqlitedatabase.execSQL(sql2);
				}catch(Exception e)
				{
					e.printStackTrace();
				}
			}
			else
			{
				String sql3="insert into dayplan(dayid,date,iscomplete,heataccount,planid) values("+mydayid+",'"+date+"','"+iscomplete+"',"+heataccount+","+planid+")";
				try{
					sqlitedatabase.execSQL(sql3);
				}catch(Exception e)
				{
					e.printStackTrace();
				}
			}
			cr.close();
		}


}
